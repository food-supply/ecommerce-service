use ecommerce_service;

-- lấy thông tin sản phẩm người dùng bán
CREATE PROCEDURE  get_product_seller(IN p_user_id BINARY(16))

BEGIN
  RETURN 
    SELECT 
      p.product_code, p.product_name, p.brand, p.unit, p.description, p.product_id, 
      pv.product_id as product_id_variant, pv.sku_code, pv.base_cost, pv.retail_price, 
      pv.wholesale_price, pv.default_discount, c.category_name
    FROM seller s
    INNER JOIN product_offering po ON po.seller_id = s.seller_id 
    INNER JOIN product p ON p.product_id = po.product_id
    INNER JOIN product_variant pv ON pv.product_id = p.product_id
    INNER JOIN category c ON p.category_id = c.category_id
    WHERE s.user_id = p_user_id 
    AND s.status = 'ACTIVE' 
    AND p.status = 'ACTIVE' 
    AND pv.status = 'ACTIVE'
    ORDER BY c.category_name DESC;
END;


CREATE PROCEDURE p_generate_sku_code (
  IN v_variant_id BINARY(16), 
  IN v_product_id BINARY(16), 
  IN v_product_variant_name VARCHAR(200),
  IN v_base_cost DECIMAL(10,2), 
  IN v_retail_price DECIMAL(10,2), 
  IN v_wholesale_price DECIMAL(10,2), 
  IN v_default_discount DECIMAL(5,2),
  IN v_min_qty INT,
  OUT result_code INT
)

BEGIN
  DECLARE exit handler FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        SET result_code = 0;
    END;

    START TRANSACTION;
  
  DECLARE attr_values TEXT;

  INSERT INTO product_variant (variant_id, product_id, product_variant_name, sku_code, base_cost, retail_price, wholesale_price, default_discount, min_qty) VALUES (v_variant_id, v_product_id, v_product_variant_name, null, v_base_cost, v_retail_price, v_wholesale_price, v_default_discount, v_min_qty);

  SELECT GROUP_CONCAT(av.value ORDER BY av.value SEPARATOR '-') INTO attr_values
  FROM product_attribute_value pav
  JOIN attribute_value av ON pav.value_id = av.value_id
  WHERE pav.variant_id = NEW.variant_id;

  DECLARE pcode VARCHAR(50);
  SELECT p.product_code INTO pcode
  FROM product_variant pv
  JOIN product p ON pv.product_id = p.product_id
  WHERE pv.variant_id = NEW.variant_id;

  UPDATE product_variant
  SET sku_code = CONCAT(pcode, '-', attr_values)
  WHERE variant_id = NEW.variant_id;


COMMIT; 
  SET result_code = 1;
END;