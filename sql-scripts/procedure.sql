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