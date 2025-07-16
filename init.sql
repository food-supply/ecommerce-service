-- create database product_service;
use product_service;

create table category 
(
    category_id BINARY(16) PRIMARY KEY,
    category_name VARCHAR(200) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tag (
  tag_id BINARY(16) PRIMARY KEY AUTO_INCREMENT,
  tag_name VARCHAR(50) NOT NULL UNIQUE  -- new, hot, discount, vegan, limited
);

CREATE TABLE attribute (
    attribute_id BINARY(16) PRIMARY KEY,
    attribute_name VARCHAR(100) NOT NULL,
  --  data_type VARCHAR(20) NOT NULL                 
);

create table attribute_value (
  value_id BINARY(16) PRIMARY KEY,
  attribute_id BINARY(16),
  value VARCHAR(20),
  FOREIGN KEY (attribute_id) REFERENCES attribute(attribute_id)
);

CREATE TABLE category_attribute (
    id BINARY(16) PRIMARY KEY,
    category_id BINARY(16) NOT NULL,
    attribute_id BINARY(16) NOT NULL,
    is_required BOOLEAN DEFAULT FALSE,
    display_order INT DEFAULT 0,

    FOREIGN KEY (category_id) REFERENCES category(category_id),
    FOREIGN KEY (attribute_id) REFERENCES attribute(attribute_id)
);

create table product
(
  product_id BINARY(16) PRIMARY KEY,                         
  product_code VARCHAR(50) NOT NULL UNIQUE,                  -- Mã sản phẩm (hiển thị cho người dùng xem)
  product_name VARCHAR(200) NOT NULL,                        -- Tên sản phẩm
  brand VARCHAR(100),                                        -- Thương hiệu
  category_id BINARY(16),                                     -- Danh mục sản phẩm
  unit VARCHAR(50),                                          -- Đơn vị tính: KG, cái, hộp,..
  description TEXT,                                          
  status VARCHAR(20) DEFAULT 'ACTIVE',                        -- ACTIVE, DISCONTINUED,... tất cả các size,.. sản phẩm
  FOREIGN KEY (category_id) REFERENCES category(category_id)
);

CREATE TABLE product_variant (
    variant_id BINARY(16) PRIMARY KEY,
    product_id BINARY(16) NOT NULL,                           
    sku_code VARCHAR(100) NOT NULL UNIQUE,                     -- SKU cụ thể cho từng biến thể
    base_cost DECIMAL(10,2),                                   -- Giá gốc 
    retail_price DECIMAL(10,2),                                -- Giá bán lẻ
    wholesale_price DECIMAL(10,2),                             -- Giá bán sỉ
    default_discount DECIMAL(5,2),                             -- Mặc định giảm giá (%)
    status VARCHAR(20) DEFAULT 'ACTIVE',                       -- Ngừng bán với sản phầm có size, màu,..
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE product_attribute_value (
    id BINARY(16) PRIMARY KEY,
    variant_id BINARY(16) NOT NULL,
    attribute_id BINARY(16) NOT NULL,
    value_id BINARY(16) NOT NULL,
   -- value VARCHAR(255) NOT NULL,

    FOREIGN KEY (variant_id) REFERENCES product_variant(variant_id),
    FOREIGN KEY (attribute_id) REFERENCES attribute(attribute_id)
);

CREATE TABLE product_tag (
  product_id BINARY(16),
  tag_id BINARY(16),
  PRIMARY KEY (product_id, tag_id),
  FOREIGN KEY (product_id) REFERENCES product(product_id),
  FOREIGN KEY (tag_id) REFERENCES tag(tag_id)
);

CREATE TABLE product_image (
    image_id BINARY(16) PRIMARY KEY,
    variant_id BINARY(16) NOT NULL,
    image_url VARCHAR(500) NOT NULL,                           
    is_primary BOOLEAN DEFAULT FALSE,                          -- Ảnh chính (thumbnail)
    description VARCHAR(255),                                  -- Ghi chú ảnh (góc chụp, bao bì…)

    FOREIGN KEY (variant_id) REFERENCES product_variant(variant_id)
);

CREATE TABLE warehouse (
    warehouse_id BINARY(16) PRIMARY KEY,
    warehouse_code VARCHAR(50) NOT NULL UNIQUE,
    warehouse_name VARCHAR(200),
    location VARCHAR(255)
);

CREATE TABLE product_batch (
    batch_id BINARY(16) PRIMARY KEY,
    variant_id BINARY(16) NOT NULL,
    warehouse_id BINARY(16) NOT NULL,
    batch_number VARCHAR(50),
    import_date DATE,
    purchase_price DECIMAL(10,2),                              -- Giá nhập thực tế theo lô
    quantity DECIMAL(10,2),                                    -- Số lượng hàng của lô
    location_in_warehouse VARCHAR(100),                        -- Vị trí trong kho

    FOREIGN KEY (variant_id) REFERENCES product_variant(variant_id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouse(warehouse_id)
);

CREATE TABLE stock_in (
    stock_in_id BINARY(16) PRIMARY KEY,
    stock_in_code VARCHAR(50) NOT NULL UNIQUE,
    warehouse_id BINARY(16) NOT NULL,
    supplier_id BINARY(16),
    import_date DATE NOT NULL DEFAULT CURRENT_DATE,
    note TEXT,

    FOREIGN KEY (warehouse_id) REFERENCES warehouse(warehouse_id),
    FOREIGN KEY (supplier_id) REFERENCES supplier(supplier_id)
);

CREATE TABLE stock_in_detail (
    stock_in_detail_id BINARY(16) PRIMARY KEY,
    stock_in_id BINARY(16) NOT NULL,
    variant_id BINARY(16) NOT NULL,
    batch_id BINARY(16) NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    purchase_price DECIMAL(10,2),

    FOREIGN KEY (stock_in_id) REFERENCES stock_in(stock_in_id),
    FOREIGN KEY (variant_id) REFERENCES product_variant(variant_id),
    FOREIGN KEY (batch_id) REFERENCES product_batch(batch_id)
);

CREATE TABLE stock_out (
    stock_out_id BINARY(16) PRIMARY KEY,
    stock_out_code VARCHAR(50) NOT NULL UNIQUE,
    warehouse_id BINARY(16) NOT NULL,
    export_date DATE NOT NULL DEFAULT CURRENT_DATE,
    customer_name VARCHAR(200),
    note TEXT,

    FOREIGN KEY (warehouse_id) REFERENCES warehouse(warehouse_id)
);

CREATE TABLE stock_out_detail (
    stock_out_detail_id BINARY(16) PRIMARY KEY,
    stock_out_id BINARY(16) NOT NULL,
    variant_id BINARY(16) NOT NULL,
    batch_id BINARY(16) NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (stock_out_id) REFERENCES stock_out(stock_out_id),
    FOREIGN KEY (variant_id) REFERENCES product_variant(variant_id),
    FOREIGN KEY (batch_id) REFERENCES product_batch(batch_id)
);

CREATE TABLE product_discount (
    discount_id BINARY(16) PRIMARY KEY,
    variant_id BINARY(16) NOT NULL,
    start_date DATE,
    end_date DATE,
    discount_percent DECIMAL(5,2),
    discount_amount DECIMAL(10,2),
    note TEXT,

    FOREIGN KEY (variant_id) REFERENCES product_variant(variant_id)
);

CREATE TABLE return_reason (
    reason_id BINARY(16) PRIMARY KEY,
    reason_code VARCHAR(50) UNIQUE NOT NULL,         -- Ví dụ: DEFECT, WRONG_SIZE
    description VARCHAR(255)                         -- Mô tả: "Hàng lỗi", "Sai kích thước"
);


CREATE TABLE return_in (
    return_id BINARY(16) PRIMARY KEY,
    order_id BINARY(16),                             -- Đơn hàng bị trả (nếu có)
    variant_id BINARY(16) NOT NULL,
    batch_id BINARY(16),
    warehouse_id BINARY(16) NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    reason_id BINARY(16),                            -- Liên kết tới lý do
    note TEXT,                                       -- Ghi chú chi tiết nếu cần
    return_date DATE DEFAULT CURRENT_DATE,

    FOREIGN KEY (order_id) REFERENCES `order`(order_id),
    FOREIGN KEY (variant_id) REFERENCES product_variant(variant_id),
    FOREIGN KEY (batch_id) REFERENCES product_batch(batch_id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouse(warehouse_id),
    FOREIGN KEY (reason_id) REFERENCES return_reason(reason_id)
);

CREATE TABLE customer (
    customer_id BINARY(16) PRIMARY KEY,
    full_name VARCHAR(200),
    phone VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE `order` (
    order_id BINARY(16) PRIMARY KEY,
    order_code VARCHAR(50) NOT NULL UNIQUE,
    customer_id BINARY(16),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDING', -- PENDING, CONFIRMED, SHIPPED, COMPLETED, CANCELLED
    note TEXT,

    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE order_detail (
    order_detail_id BINARY(16) PRIMARY KEY,
    order_id BINARY(16) NOT NULL,
    variant_id BINARY(16) NOT NULL,
    warehouse_id BINARY(16) NOT NULL, -- Hàng được lấy từ kho nào
    quantity DECIMAL(10,2) NOT NULL,
    price DECIMAL(10,2), -- Giá bán tại thời điểm đó
    discount_percent DECIMAL(5,2), -- Nếu có

    FOREIGN KEY (order_id) REFERENCES `order`(order_id),
    FOREIGN KEY (variant_id) REFERENCES product_variant(variant_id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouse(warehouse_id)
);

-- view tồn kho 

CREATE VIEW v_inventory AS
SELECT 
    si.variant_id,
    pb.warehouse_id,

    -- Tổng số lượng đã nhập
    SUM(sid.quantity) AS total_in,

    -- Số lượng hàng trả về đã nhập lại
    COALESCE((
        SELECT SUM(r.quantity)
        FROM return_in r
        WHERE r.variant_id = si.variant_id
          AND r.warehouse_id = pb.warehouse_id
    ), 0) AS total_return,

    -- Tổng số lượng đã xuất
    COALESCE((
        SELECT SUM(sod.quantity)
        FROM stock_out_detail sod
        JOIN product_batch pb2 ON sod.batch_id = pb2.batch_id
        WHERE sod.variant_id = si.variant_id
          AND pb2.warehouse_id = pb.warehouse_id
    ), 0) AS total_out,

    -- Tồn kho thực tế = nhập + trả hàng - xuất
    SUM(sid.quantity)
    + COALESCE((
        SELECT SUM(r.quantity)
        FROM return_in r
        WHERE r.variant_id = si.variant_id
          AND r.warehouse_id = pb.warehouse_id
    ), 0)
    - COALESCE((
        SELECT SUM(sod.quantity)
        FROM stock_out_detail sod
        JOIN product_batch pb2 ON sod.batch_id = pb2.batch_id
        WHERE sod.variant_id = si.variant_id
          AND pb2.warehouse_id = pb.warehouse_id
    ), 0) AS quantity_in_stock,

    -- Số lượng đang được đặt (chưa giao)
    COALESCE((
        SELECT SUM(od.quantity)
        FROM order_detail od
        JOIN `order` o ON od.order_id = o.order_id
        WHERE od.variant_id = si.variant_id
          AND od.warehouse_id = pb.warehouse_id
          AND o.status IN ('PENDING', 'CONFIRMED')
    ), 0) AS reserved_stock,

    -- Tồn khả dụng = tồn thực tế - đã đặt
    SUM(sid.quantity)
    + COALESCE((
        SELECT SUM(r.quantity)
        FROM return_in r
        WHERE r.variant_id = si.variant_id
          AND r.warehouse_id = pb.warehouse_id
    ), 0)
    - COALESCE((
        SELECT SUM(sod.quantity)
        FROM stock_out_detail sod
        JOIN product_batch pb2 ON sod.batch_id = pb2.batch_id
        WHERE sod.variant_id = si.variant_id
          AND pb2.warehouse_id = pb.warehouse_id
    ), 0)
    - COALESCE((
        SELECT SUM(od.quantity)
        FROM order_detail od
        JOIN `order` o ON od.order_id = o.order_id
        WHERE od.variant_id = si.variant_id
          AND od.warehouse_id = pb.warehouse_id
          AND o.status IN ('PENDING', 'CONFIRMED')
    ), 0) AS available_stock

FROM stock_in_detail sid
JOIN stock_in si ON sid.stock_in_id = si.stock_in_id
JOIN product_batch pb ON sid.batch_id = pb.batch_id
GROUP BY si.variant_id, pb.warehouse_id;

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
        SELECT JSON_OBJECTAGG(a.attribute_name, pav.value)
        FROM product_attribute_value pav
        JOIN attribute a ON pav.attribute_id = a.attribute_id
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



-- SET @uuid := UNHEX(REPLACE(UUID(), '-', ''));

-- SET @uuid_1 := UNHEX(REPLACE(UUID(), '-', ''));

-- SET @uuid_2 := UNHEX(REPLACE(UUID(), '-', ''));

-- insert into category (category_id, category_name) values (@uuid, 'Món Việt Nam');

-- insert into category (category_id, category_name) values (@uuid_1, 'Món Hàn');

-- insert into category (category_id, category_name) values (@uuid_2, 'Đồ uống');

-- SELECT HEX(category_id) AS id, category_name FROM category;