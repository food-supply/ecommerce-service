package com.se.ecommerce_service.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.se.ecommerce_service.config.MinioProperties;
import com.se.ecommerce_service.helper.ProjectUtils;
import com.se.ecommerce_service.helper.UUIDUtil;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;

@Service
public class MinioService {
    private final MinioClient minioClient;
    private final MinioProperties minioProps;

    public MinioService(MinioClient minioClient, MinioProperties props) {
        this.minioClient = minioClient;
        this.minioProps = props;
    }

    public String uploadFile(MultipartFile file) {

        Tika tika = new Tika();
        String mimeType;
        try {
            mimeType = tika.detect(file.getInputStream());
            List<String> allowedMimeTypes = List.of(
                    "image/jpeg", "image/png", "application/pdf",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

            if (!allowedMimeTypes.contains(mimeType)) {
                throw new IllegalArgumentException("Unsupported file type: " + mimeType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileName = UUIDUtil.generateUuidV7() + "-" + file.getOriginalFilename();
        String bucket = detectBucket(file, ProjectUtils.getProjectName());
        try {
            InputStream inputStream = file.getInputStream();

            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();

            minioClient.putObject(args);
            return minioProps.getUrl() + "/" + bucket + "/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Upload failed", e);
        }
    }

    public Pair<String, String> extractBucketAndObject(String imageUrl) {
        try {
            URI uri = URI.create(imageUrl);
            String path = uri.getPath(); // /bucketName/objectName.ext
            String[] parts = path.split("/", 3);

            if (parts.length < 3) {
                throw new IllegalArgumentException("Invalid image URL format: " + imageUrl);
            }

            String bucketName = parts[1];
            String objectName = parts[2];

            return Pair.of(bucketName, objectName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract bucket and object from URL", e);
        }
    }

    public void deleteFileByUrl(String imageUrl) {
        try {
            Pair<String, String> bucketAndObject = extractBucketAndObject(imageUrl);
            String bucketName = bucketAndObject.getLeft();
            String objectName = bucketAndObject.getRight();

            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file from MinIO: " + imageUrl, e);
        }
    }

    private String detectBucket(MultipartFile file, String service) {
        String contentType = file.getContentType();
        if (contentType == null)
            return service + "-default-bucket";

        if (contentType.startsWith("image/"))
            return service + "-image-bucket";
        if (contentType.contains("word"))
            return service + "-docx-bucket";
        if (contentType.contains("excel") || contentType.contains("spreadsheet"))
            return service + "-excel-bucket";
        if (contentType.equals("application/pdf"))
            return service + "-pdf-bucket";

        return service + "-misc-bucket";
    }

}
