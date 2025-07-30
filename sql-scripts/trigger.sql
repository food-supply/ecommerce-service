use ecommerce_service;

DELIMITER //

CREATE TRIGGER check_valid_attribute
BEFORE INSERT ON product_attribute_value
FOR EACH ROW
BEGIN
    DECLARE v_category_id BINARY(16);

  
    SELECT p.category_id INTO v_category_id
    FROM product_variant pv
    JOIN product p ON pv.product_id = p.product_id
    WHERE pv.variant_id = NEW.variant_id;

    
    IF NOT EXISTS (
        SELECT 1 FROM category_attribute
        WHERE category_id = v_category_id
          AND attribute_id = NEW.attribute_id
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Invalid attribute for this product''s category';
    END IF;
END //

DELIMITER ;