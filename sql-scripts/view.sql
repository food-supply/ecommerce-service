use ecommerce_service;

-- view tồn kho 

CREATE VIEW v_inventory AS
SELECT 
    sid.variant_id,
    pb.warehouse_id,

    -- Tổng số lượng đã nhập
    SUM(sid.quantity) AS total_in,

    -- Số lượng hàng trả về đã nhập lại
    COALESCE((
        SELECT SUM(r.quantity)
        FROM return_in r
        WHERE r.variant_id = sid.variant_id
          AND r.warehouse_id = pb.warehouse_id
    ), 0) AS total_return,

    -- Tổng số lượng đã xuất
    COALESCE((
        SELECT SUM(sod.quantity)
        FROM stock_out_detail sod
        JOIN product_batch pb2 ON sod.batch_id = pb2.batch_id
        WHERE sod.variant_id = sid.variant_id
          AND pb2.warehouse_id = pb.warehouse_id
    ), 0) AS total_out,

    -- Tồn kho thực tế = nhập + trả hàng - xuất
    SUM(sid.quantity)
    + COALESCE((
        SELECT SUM(r.quantity)
        FROM return_in r
        WHERE r.variant_id = sid.variant_id
          AND r.warehouse_id = pb.warehouse_id
    ), 0)
    - COALESCE((
        SELECT SUM(sod.quantity)
        FROM stock_out_detail sod
        JOIN product_batch pb2 ON sod.batch_id = pb2.batch_id
        WHERE sod.variant_id = sid.variant_id
          AND pb2.warehouse_id = pb.warehouse_id
    ), 0) AS quantity_in_stock,

    -- Số lượng đang được đặt (chưa giao)
    COALESCE((
        SELECT SUM(od.quantity)
        FROM order_detail od
        JOIN `order` o ON od.order_id = o.order_id
        WHERE od.variant_id = sid.variant_id
          AND od.warehouse_id = pb.warehouse_id
          AND o.status IN ('PENDING', 'CONFIRMED')
    ), 0) AS reserved_stock,

    -- Tồn khả dụng = tồn thực tế - đã đặt
    SUM(sid.quantity)
    + COALESCE((
        SELECT SUM(r.quantity)
        FROM return_in r
        WHERE r.variant_id = sid.variant_id
          AND r.warehouse_id = pb.warehouse_id
    ), 0)
    - COALESCE((
        SELECT SUM(sod.quantity)
        FROM stock_out_detail sod
        JOIN product_batch pb2 ON sod.batch_id = pb2.batch_id
        WHERE sod.variant_id = sid.variant_id
          AND pb2.warehouse_id = pb.warehouse_id
    ), 0)
    - COALESCE((
        SELECT SUM(od.quantity)
        FROM order_detail od
        JOIN `order` o ON od.order_id = o.order_id
        WHERE od.variant_id = sid.variant_id
          AND od.warehouse_id = pb.warehouse_id
          AND o.status IN ('PENDING', 'CONFIRMED')
    ), 0) AS available_stock

FROM stock_in_detail sid
JOIN stock_in si ON sid.stock_in_id = si.stock_in_id
JOIN product_batch pb ON sid.batch_id = pb.batch_id
GROUP BY sid.variant_id, pb.warehouse_id;

-- view thông tin sản phẩm 

CREATE VIEW v_product_full_info AS
SELECT
    p.product_id,
    p.product_code,
    p.product_name,
    p.description,
    p.brand,
    p.unit,
    p.status AS product_status,
    p.created_at AS product_created_at,

    c.category_id,
    c.category_name,

    v.variant_id,
    v.sku_code,
    v.base_cost,
    v.retail_price,
    v.wholesale_price,
    v.default_discount,
    v.status AS variant_status,
    v.created_at AS variant_created_at,

    -- Thuộc tính sản phẩm
    (
        SELECT JSON_OBJECTAGG(a.attribute_name, av.value)
        FROM product_attribute_value pav
        JOIN attribute a ON pav.attribute_id = a.attribute_id
        join attribute_value av on a.attribute_id = av.attribute_id
        WHERE pav.variant_id = v.variant_id
    ) AS attributes_json,

    -- Danh sách hình ảnh
    (
        SELECT JSON_ARRAYAGG(image_url)
        FROM product_image
        WHERE variant_id = v.variant_id
    ) AS images,

    -- Tổng tồn kho (tính trên tất cả kho)
    (
        SELECT SUM(quantity_in_stock)
        FROM v_inventory i
        WHERE i.variant_id = v.variant_id
    ) AS total_stock,

    -- Tổng tồn khả dụng
    (
        SELECT SUM(available_stock)
        FROM v_inventory i
        WHERE i.variant_id = v.variant_id
    ) AS total_available_stock

FROM product p
JOIN category c ON p.category_id = c.category_id
JOIN product_variant v ON p.product_id = v.product_id;

