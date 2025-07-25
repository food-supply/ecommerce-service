package com.se.ecommerce_service.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.se.ecommerce_service.helper.CodeGenerator;
import com.se.ecommerce_service.helper.UUIDUtil;
import com.se.ecommerce_service.mapper.GenericRowMapper;
import com.se.ecommerce_service.model.Cart;
import com.se.ecommerce_service.model.CartItem;
import com.se.ecommerce_service.model.Inventory;

@Repository
public class CartRepository {
    private final JdbcTemplate jdbc;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public CartRepository(JdbcTemplate jdbc, ProductRepository productRepository,
            WarehouseRepository warehouseRepository) {
        this.jdbc = jdbc;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public Optional<Cart> findActiveCart(UUID userId, String sessionId) {
        String sql = "SELECT * FROM cart WHERE status = 'ACTIVE' AND (user_id = ? OR session_id = ?) LIMIT 1";
        return jdbc.query(sql, new Object[] { userId, sessionId }, cartRowMapper()).stream().findFirst();
    }

    public void createCart(Cart cart) {
        String sql = "INSERT INTO cart (cart_id, user_id, session_id, status) VALUES (?, ?, ?, ?)";
        jdbc.update(sql, cart.getCartId(), cart.getUserId(), cart.getSessionId(), cart.getStatus());
    }

    public boolean upsertCartItem(CartItem item) {
        String sql = """
                    INSERT INTO cart_item (cart_item_id, cart_id, variant_id, quantity, price_at_add_time)
                    VALUES (?, ?, ?, ?, ?)
                    ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)
                """;
        int row  = jdbc.update(sql, item.getCartItemId(), item.getCartId(), item.getVariantId(), item.getQuantity(),
                item.getPriceAtAddTime());
        return row > 0;
    }

    public BigDecimal getCurrentPrice(UUID variantId) {
        return jdbc.queryForObject("SELECT price FROM product_variant WHERE variant_id = ?", BigDecimal.class,
                variantId);
    }

    public boolean assignCartToUser(UUID userId, String sessionId) {
        String sql = "UPDATE cart SET user_id = ?, session_id = NULL WHERE session_id = ? AND status = 'ACTIVE'";
        int row = jdbc.update(sql, userId, sessionId);
        return row > 0;
    }

    private RowMapper<Cart> cartRowMapper() {
        return (rs, rowNum) -> {
            Cart c = new Cart();
            c.setCartId(UUID.fromString(rs.getString("cart_id")));
            c.setUserId(rs.getObject("user_id", UUID.class));
            c.setSessionId(rs.getString("session_id"));
            c.setStatus(rs.getString("status"));
            return c;
        };
    }

    /**
     * customer Agree/Disagree order (having product invalid)
     * if agree -> call mergeCartToOrder
     * if disagree -> return
     * @param userId
     * @return
     */

    public boolean mergeValidCartToOrder(UUID userId){
        RowMapper<CartItem> rowMapper = new GenericRowMapper<>(CartItem.class);
        List<CartItem> cartItems = jdbc.query(
                "select ci.variant_id, ci.quantity, ci.price_at_add_time from cart c inner join cart_item ci on c.cart_id = ci.cart_id where c.user_id = ?",
                ps -> ps.setBytes(1, UUIDUtil.uuidToBytes(userId)), rowMapper);
        for (CartItem cartItem : cartItems) {
            Inventory warehouse = warehouseRepository.checkVariantFree(cartItem.getVariantId(), cartItem.getQuantity());
            boolean isActive = productRepository.getProductByIsActive(cartItem.getVariantId());
            if (warehouse.getAvailableStock().compareTo(BigDecimal.ZERO) < 0 || !isActive){
                return false;
            }  
        }
        return true;
    }

    public boolean mergeCartToOrder (UUID userId){
        RowMapper<CartItem> rowMapper = new GenericRowMapper<>(CartItem.class);
        List<CartItem> cartItems = jdbc.query("select ci.variant_id, ci.quantity, ci.price_at_add_time from cart c inner join cart_item ci on c.cart_id = ci.cart_id where c.user_id = ?", ps -> ps.setBytes(1, UUIDUtil.uuidToBytes(userId)), rowMapper);

        int rowOrder = 0;
        int rowItem = 0;

        if (!cartItems.isEmpty()) {
            UUID orderId = UUIDUtil.generateUuidV7();
            LocalDate date = LocalDate.now();
            String orderCode = CodeGenerator.generateOrderCode(date);

            Object[] paramsOrder = {
                    orderId,
                    orderCode,
                    UUIDUtil.uuidToBytes(userId),
                    "PENDING"
            };
            rowOrder = jdbc.update("insert into order (order_id, order_code, customer_id,  status) values (?, ?, ?, ?)", paramsOrder);
            
            for (CartItem cartItem : cartItems) {
                BigDecimal price = productRepository.getPriceAtAddTime(cartItem.getVariantId(), cartItem.getQuantity());
                Inventory warehouse = warehouseRepository.checkVariantFree(cartItem.getVariantId(), cartItem.getQuantity());
                boolean isActive = productRepository.getProductByIsActive(cartItem.getVariantId());
                if (warehouse.getAvailableStock().compareTo(BigDecimal.ZERO) > 0 && isActive) {
                    
                    UUID orderItemId = UUIDUtil.generateUuidV7();
                    Object[] paramsOrderItem = {
                        orderItemId,
                        orderId,
                        cartItem.getVariantId(),
                        warehouse.getWarehouseId(), 
                        cartItem.getQuantity(),
                        price
                    };
        
                    rowItem = jdbc.update("insert into order_items (order_items_id, order_id, variant_id, warehouse_id, quantity, price) values (?, ?, ?, ?, ?, ?)", paramsOrderItem);
                }
            }
        }
        return rowOrder > 0 && rowItem > 0; 
    }
}
