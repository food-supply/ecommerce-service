package com.se.ecommerce_service.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.se.ecommerce_service.dto.ProductImage;
import com.se.ecommerce_service.dto.ProductRequestDTO;
import com.se.ecommerce_service.dto.ProductVariantRequestDTO;
import com.se.ecommerce_service.helper.UUIDUtil;
import com.se.ecommerce_service.mapper.GenericRowMapper;
import com.se.ecommerce_service.model.Product;
import com.se.ecommerce_service.service.MinioService;

@Repository
public class ProductRepository {
	private final JdbcTemplate jdbcTemplate;
	private final MinioService minioService;

	public ProductRepository(JdbcTemplate jdbcTemplate, MinioService minioService) {
		this.jdbcTemplate = jdbcTemplate;
		this.minioService = minioService;
	}

	public List<Product> findAll() {
		return jdbcTemplate.query("SELECT * FROM product", new GenericRowMapper<>(Product.class));
	}

	public Optional<Product> findById(UUID id) {
		List<Product> list = jdbcTemplate.query("SELECT * FROM v_product_full_info WHERE product_id = ?",
				ps -> ps.setBytes(1, UUIDUtil.uuidToBytes(id)),
				new GenericRowMapper<>(Product.class));
		return list.stream().findFirst();
	}

	public boolean save(ProductRequestDTO product) {
		UUID uuid = UUIDUtil.generateUuidV7();
		Object[] params = {
				UUIDUtil.uuidToBytes(uuid),
				product.getProductCode(),
				product.getProductName(),
				UUIDUtil.uuidToBytes(product.getCategoryId()),
				product.getBrand(),
				product.getUnit(),
				product.getDescription()
		};
		int row = jdbcTemplate.update(
				"INSERT INTO product (product_id, product_code, product_name, category_id, brand, unit, description) VALUES (?, ?, ?, ?, ?, ?, ?)",
				params);

		int rowVal = 0;
		int rowAttr = 0;
		for (ProductVariantRequestDTO variant : product.getProductVariant()) {
			UUID variantId = UUIDUtil.generateUuidV7();

			Object[] paramsPV = {
					UUIDUtil.uuidToBytes(variantId),
					UUIDUtil.uuidToBytes(uuid),
					variant.getSkuCode(),
					variant.getBaseCost(), variant.getRetailPrice(),
					variant.getWholeSalePrice(), variant.getDefaultDiscount()

			};

			rowAttr = jdbcTemplate.update(
					" INSERT INTO product_variant (variant_id, product_id, sku_code, base_cost, retail_price, wholesale_price, default_discount) VALUES (?, ?, ?, ?, ?, ?, ?)",
					paramsPV);

			if (!variant.getAttributes().isEmpty()) {
				for (Map<UUID, UUID> attrMap : variant.getAttributes()) {
					for (Map.Entry<UUID, UUID> attr : attrMap.entrySet()) {
						UUID id = UUIDUtil.generateUuidV7();
						Object[] pProductAttributeValue = {
								UUIDUtil.uuidToBytes(id),
								UUIDUtil.uuidToBytes(variantId),
								UUIDUtil.uuidToBytes(attr.getKey()),
								UUIDUtil.uuidToBytes(attr.getValue())
						};
						jdbcTemplate.update(
								"INSERT INTO product_attribute_value (id, variant_id, attribute_id, value_id) VALUES (?, ?, ?, ?)",
								pProductAttributeValue);
					}
				}
			}

			for (ProductImage image : product.getProductImages()) {
				// String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
				if (image.getFile() != null) {
					String url = minioService.uploadFile(image.getFile());
					UUID imgId = UUIDUtil.generateUuidV7();
					Object[] pProductImage = {
							UUIDUtil.uuidToBytes(imgId),
							UUIDUtil.uuidToBytes(uuid),
							image.getVariantId(),
							url,
							image.getIsPrimary(),
							image.getDescription()
					};
					jdbcTemplate.update(
							"INSERT INTO product_image (image_id, product_id, variant_id, image_url, is_primary, description) VALUES (?, ?, ?, ?, ?, ?)",
							pProductImage);

				}
			}
		}

		if (!product.getTags().isEmpty()) {
			int rowTags = jdbcTemplate.update("Insert into product_tag (product_id, tag_id) values (?, ?)",
					new Object[] { UUIDUtil.uuidToBytes(uuid), product.getTags() });
			return row > 0 && rowTags > 0 && rowVal > 0 && rowAttr > 0;
		}

		return row > 0 && rowVal > 0 && rowAttr > 0;
	}

	public boolean update(ProductRequestDTO product) {
		// UUID uuid = UUIDUtil.generateUuidV7();
		Object[] paramsProduct = {
				product.getProductCode(), product.getProductName(), UUIDUtil.uuidToBytes(
						product.getCategoryId()),
				product.getBrand(),
				product.getUnit(), product.getDescription(),
				UUIDUtil.uuidToBytes(product.getProduct_id())
		};
		int row = jdbcTemplate.update(
				"update product set product_code = ?, product_name = ?, category_id = ?, brand = ?, unit = ?, description = ? where product_id = ?",
				paramsProduct);

		int rowVal = 0;
		int rowAttr = 0;
		for (ProductVariantRequestDTO variant : product.getProductVariant()) {
			// UUID variantId = UUIDUtil.generateUuidV7();
			Object[] paramsProductVariant = {
					UUIDUtil.uuidToBytes(
							variant.getVariantId()),
					variant.getSkuCode(),
					variant.getBaseCost(), variant.getRetailPrice(),
					variant.getWholeSalePrice(), variant.getDefaultDiscount(),
					UUIDUtil.uuidToBytes(product.getProduct_id())
			};
			rowAttr = jdbcTemplate.update(
					"update product_variant set variant_id = ?, sku_code = ?, base_cost = ?, retail_price = ?, wholesale_price = ?, default_discount  = ? where product_id = ?",
					paramsProductVariant);

			if (!variant.getAttributes().isEmpty()) {
				for (Map<UUID, UUID> attrMap : variant.getAttributes()) {
					for (Map.Entry<UUID, UUID> attr : attrMap.entrySet()) {
						// UUID id = UUIDUtil.generateUuidV7();
						Object[] paramsProductAttrValue = {
								UUIDUtil.uuidToBytes(
										attr.getKey()),
								attr.getValue(),
								UUIDUtil.uuidToBytes(variant
										.getVariantId())
						};
						rowVal = jdbcTemplate.update(
								"update product_attribute_value set attribute_id = ?, value_id = ? where variant_id = ?",
								paramsProductAttrValue);
					}
				}
			}

			for (ProductImage image : product.getProductImages()) {
				// String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
				String url = minioService.uploadFile(image.getFile());
				// UUID imgId = UUIDUtil.generateUuidV7();

				String oldImageUrl = image.getImageUrl();
				if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
					minioService.deleteFileByUrl(oldImageUrl);
				}
				Object[] paramsProductImg = {
						url,
						image.getIsPrimary(),
						image.getDescription(),
						UUIDUtil.uuidToBytes(image
								.getVariantId()),
						UUIDUtil.uuidToBytes(image.getImageId()),
						UUIDUtil.uuidToBytes(product.getProduct_id())
				};
				jdbcTemplate.update(
						"update product_image set image_url = ?, is_primary = ?, description = ? , variant_id = ?, image_id = ? where product_id = ?",
						paramsProductImg);
			}
		}

		if (!product.getTags().isEmpty()) {
			int rowTags = jdbcTemplate.update("update product_tag set tag_id = ? where product_id = ?",
					product.getTags(),
					UUIDUtil.uuidToBytes(product.getProduct_id()));
			return row > 0 && rowTags > 0 && rowVal > 0 && rowAttr > 0;
		}

		return row > 0 && rowVal > 0 && rowAttr > 0;
	}

	public boolean delete(UUID id) {
		int row = jdbcTemplate.update("DELETE FROM product WHERE product_id = ?",
				new Object[] { UUIDUtil.uuidToBytes(id) });
		return row > 0;
	}

	public List<Product> getProductByCategory(UUID category_id) {
		List<Product> products = jdbcTemplate.query("select * from product where category_id = ?",
				ps -> ps.setBytes(1, UUIDUtil.uuidToBytes(category_id)),
				new GenericRowMapper<>(Product.class));

		return products;
	}

	public List<Product> getProductByIsActive(boolean isActive) {
		List<Product> products = jdbcTemplate.query("select * from product where status = ?",
				new GenericRowMapper<>(Product.class), isActive);
		return products;
	}

	public List<Product> getProductByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
		RowMapper<Product> rowMapper = new GenericRowMapper<>(Product.class);
		List<Product> products = jdbcTemplate.query("select * from product where price > ? and price < ?",
				rowMapper, minPrice, maxPrice);
		return products;
	}

	public List<Product> getProductByName(String name) {
		RowMapper<Product> rowMapper = new GenericRowMapper<>(Product.class);
		List<Product> products = jdbcTemplate.query("select * from product where product_name = ?",
				rowMapper, name);
		return products;
	}

	public List<Product> getProductByTag(UUID id) {
		RowMapper<Product> rowMapper = new GenericRowMapper<>(Product.class);
		List<Product> products = jdbcTemplate.query(
				"select p.* from product p inner join product_tag pt on p.product_id = pt.product_tag having pt.tag_id = ? ",
				rowMapper, new Object[] { UUIDUtil.uuidToBytes(id) });
		return products;
	}
}
