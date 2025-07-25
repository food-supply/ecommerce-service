package com.se.ecommerce_service.repository;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.se.ecommerce_service.dto.ProductImage;
import com.se.ecommerce_service.dto.ProductRequestDTO;
import com.se.ecommerce_service.dto.ProductVariantRequestDTO;
import com.se.ecommerce_service.helper.CodeGenerator;
import com.se.ecommerce_service.helper.DbCallHelper;
import com.se.ecommerce_service.helper.UUIDUtil;
import com.se.ecommerce_service.mapper.GenericRowMapper;
import com.se.ecommerce_service.model.Product;
import com.se.ecommerce_service.model.ProductVariant;
import com.se.ecommerce_service.service.MinioService;

@Repository
public class ProductRepository {
	private final JdbcTemplate jdbcTemplate;
	private final MinioService minioService;
	private final DbCallHelper dbCallHelper;

	public ProductRepository(JdbcTemplate jdbcTemplate, MinioService minioService, DbCallHelper dbCallHelper) {
		this.jdbcTemplate = jdbcTemplate;
		this.minioService = minioService;
		this.dbCallHelper = dbCallHelper;
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
		LocalDate date = LocalDate.now();
        String productCode = CodeGenerator.generateProductCode(date);

		Object[] params = {
				UUIDUtil.uuidToBytes(uuid),
				productCode,
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
			int minQty = (variant.getMinQty() != null) ? variant.getMinQty() : 1;

			Map<String, Object> paramsG = Map.of(
							"v_variant_id", UUIDUtil.uuidToBytes(variantId),
							"v_product_id", UUIDUtil.uuidToBytes(uuid),
							"v_product_variant_name", variant.getProductVariantName(),
							"v_base_cost", variant.getBaseCost(), 
							"v_retail_price", variant.getRetailPrice(),
							"v_wholesale_price", variant.getWholeSalePrice(), 
							"v_default_discount", variant.getDefaultDiscount(),
							"min_qty", minQty
					);
			Map<String, SqlParameter> outParams = Map.of(
					"result_code", new SqlOutParameter("result_code", Types.INTEGER)
			);

			Map<String, Object> result = dbCallHelper.callProcedure(
					"p_generate_sku_code",
					paramsG,
					outParams
			);

			rowAttr = (int) result.get("result_code");

			if (!variant.getAttributes().isEmpty() && rowAttr > 0) {
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
			int minQty = (variant.getMinQty() != null) ? variant.getMinQty() : 1;

			Object[] paramsProductVariant = {
					UUIDUtil.uuidToBytes(
							variant.getVariantId()),
					// variant.getSkuCode(),
					variant.getBaseCost(), variant.getRetailPrice(),
					variant.getWholeSalePrice(), variant.getDefaultDiscount(),
					minQty,
					UUIDUtil.uuidToBytes(product.getProduct_id())
			};
			rowAttr = jdbcTemplate.update(
					"update product_variant set variant_id = ?, base_cost = ?, retail_price = ?, wholesale_price = ?, default_discount  = ?, min_qty = ? where product_id = ?",
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

	public List<Product> getAllProductByIsActive(boolean isActive) {
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

	public BigDecimal getPriceAtAddTime (UUID variantId, BigDecimal quantity){
	
		String sql = """
				select 
					case ?  >= min_qty when wholesale_price - wholesale_price * default_discount/100
						else retail_price AS current_price - current_price.default_discount/100
				from product_variant
				where variant_id = ?
				""";
		Object [] params = {
			quantity,
			variantId
		};
		BigDecimal productVariants = jdbcTemplate.queryForObject(sql, params, BigDecimal.class);
		return productVariants;
	}

	public boolean getProductByIsActive(UUID variantId) {
		BigDecimal products = jdbcTemplate.queryForObject("select IFNULL(variant_id, 0) from product_variant where status = 'ACTIVE' and variant_id = ?",
				new Object[]{UUIDUtil.uuidToBytes(variantId)}, BigDecimal.class);
		return products.compareTo(BigDecimal.ZERO) > 0;
	}
}
