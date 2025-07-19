use ecommerce_service;

-- lấy điểm trung bình và số lượt đánh giá
CREATE FUNCTION get_product_rating_summary(p_product_id BINARY(16))
RETURNS TABLE (
    avg_rating DECIMAL(3,2),
    review_count INT
)
BEGIN
  RETURN 
    SELECT 
      AVG(rating), COUNT(*)
    FROM product_review
    WHERE product_id = p_product_id AND is_approved = TRUE;
END;

-- SELECT * FROM get_product_rating_summary(:product_id);
