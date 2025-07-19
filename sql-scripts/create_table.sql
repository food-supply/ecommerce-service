-- create database ecommerce_service;
use ecommerce_service;

create table category 
(
    category_id BINARY(16) PRIMARY KEY,
    category_name VARCHAR(200) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tag (
  tag_id BINARY(16) PRIMARY KEY,
  tag_name VARCHAR(50) NOT NULL UNIQUE  -- new, hot, discount, vegan, limited
);

CREATE TABLE attribute (
    attribute_id BINARY(16) PRIMARY KEY,
    attribute_name VARCHAR(100) NOT NULL
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
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
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

CREATE TABLE product_review (
    review_id BINARY(16) PRIMARY KEY,
    product_id BINARY(16) NOT NULL,                  
    user_id BINARY(16) NOT NULL,                     -- Người dùng đánh giá
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5), -- Điểm đánh giá (1–5)
    comment TEXT,                                    
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,   
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_approved BOOLEAN DEFAULT FALSE,               -- Quản trị duyệt trước khi hiển thị
    variant_id BINARY(16),
    FOREIGN KEY (variant_id) REFERENCES product_variant(variant_id),

    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE review_image (
    image_id BINARY(16) PRIMARY KEY,
    review_id BINARY(16) NOT NULL,
    image_url VARCHAR(500) NOT NULL,

    FOREIGN KEY (review_id) REFERENCES product_review(review_id)
);

CREATE TABLE review_reaction (
    reaction_id BINARY(16) PRIMARY KEY,
    review_id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    reaction_type ENUM('LIKE', 'DISLIKE'),

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (review_id) REFERENCES product_review(review_id)
    -- FOREIGN KEY (user_id) REFERENCES user(user_id)
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
    import_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    purchase_price DECIMAL(10,2),                              -- Giá nhập thực tế theo lô
    quantity DECIMAL(10,2),                                    -- Số lượng hàng của lô
    location_in_warehouse VARCHAR(100),                        -- Vị trí trong kho

    FOREIGN KEY (variant_id) REFERENCES product_variant(variant_id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouse(warehouse_id)
);

CREATE TABLE seller (
    seller_id BINARY(16) PRIMARY KEY,
    user_id BINARY(16) NOT NULL,
    store_name VARCHAR(255),
    logo_url VARCHAR(500),
    phone VARCHAR(20),
    email VARCHAR(100),
    status VARCHAR(20) DEFAULT 'ACTIVE',

    -- FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE product_offering (
    offering_id BINARY(16) PRIMARY KEY,
    product_id BINARY(16) NOT NULL,
    seller_id BINARY(16) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    FOREIGN KEY (seller_id) REFERENCES seller(seller_id)
);



CREATE TABLE supplier (
    supplier_id BINARY(16) PRIMARY KEY,
    name VARCHAR(200),
    phone VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(255)
);


CREATE TABLE stock_in (
    stock_in_id BINARY(16) PRIMARY KEY,
    stock_in_code VARCHAR(50) NOT NULL UNIQUE,
    warehouse_id BINARY(16) NOT NULL,
    supplier_id BINARY(16),
    import_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
    export_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
    reason_code VARCHAR(50) UNIQUE NOT NULL,         
    description VARCHAR(255),                         
    CHECK (reason_code IN ('CANCELLED_BY_CUSTOMER', 'OUT_OF_STOCK', 'DELIVERY_FAILED', 'DEFECT', 'WRONG_SIZE'))
);

CREATE TABLE customer (
    customer_id BINARY(16) PRIMARY KEY,
    user_id BINARY(16) NOT NULL, 
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
    check(status in ('PENDING', 'CONFIRMED', 'SHIPPED', 'COMPLETED', 'CANCELLED')),

    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
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
    return_date DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (order_id) REFERENCES `order`(order_id),
    FOREIGN KEY (variant_id) REFERENCES product_variant(variant_id),
    FOREIGN KEY (batch_id) REFERENCES product_batch(batch_id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouse(warehouse_id),
    FOREIGN KEY (reason_id) REFERENCES return_reason(reason_id)
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


CREATE TABLE product_audit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BINARY(16) NOT NULL,
    changed_by BINARY(16) NOT NULL,         -- ID người dùng chỉnh sửa
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    action_type ENUM('CREATE', 'UPDATE', 'DELETE') NOT NULL,
    old_data JSON,                    -- dữ liệu cũ
    new_data JSON                     -- dữ liệu mới
);


-- SET @uuid := UNHEX(REPLACE(UUID(), '-', ''));

-- SET @uuid_1 := UNHEX(REPLACE(UUID(), '-', ''));

-- SET @uuid_2 := UNHEX(REPLACE(UUID(), '-', ''));

insert into category (category_id, category_name) values (UNHEX(REPLACE('550e8400-e29b-41d4-a716-446655440000', '-', '')), 'Món Việt Nam');

-- insert into category (category_id, category_name) values (@uuid_1, 'Món Hàn');

-- insert into category (category_id, category_name) values (@uuid_2, 'Đồ uống');

-- SELECT HEX(category_id) AS id, category_name FROM category;