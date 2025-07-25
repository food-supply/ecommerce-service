package com.se.ecommerce_service.service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.se.ecommerce_service.dto.AddToCartRequestDTO;
import com.se.ecommerce_service.model.Cart;
import com.se.ecommerce_service.model.CartItem;
import com.se.ecommerce_service.repository.CartRepository;

@Service
public class CartService {
    private final CartRepository cartRepo;

    public CartService(CartRepository cartRepo) {
        this.cartRepo = cartRepo;
    }

    public boolean addToCart(AddToCartRequestDTO req) {
        UUID userId = req.getUserId();
        String sessionId = req.getSessionId();

        Optional<Cart> cartOpt = cartRepo.findActiveCart(userId, sessionId);
        Cart cart = cartOpt.orElseGet(() -> {
            Cart c = new Cart();
            c.setCartId(UUID.randomUUID());
            c.setUserId(userId);
            c.setSessionId(sessionId);
            c.setStatus("ACTIVE");
            cartRepo.createCart(c);
            return c;
        });

        BigDecimal price = cartRepo.getCurrentPrice(req.getVariantId());

        CartItem item = new CartItem();
        item.setCartItemId(UUID.randomUUID());
        item.setCartId(cart.getCartId());
        item.setVariantId(req.getVariantId());
        item.setQuantity(req.getQuantity());
        item.setPriceAtAddTime(price);

        return cartRepo.upsertCartItem(item);
    }

    public boolean mergeCartAfterLogin(String sessionId, String userId) {
        UUID userUuid = UUID.fromString(userId);
        return cartRepo.assignCartToUser(userUuid, sessionId);
    }

    public boolean mergeValidCartToOrder(UUID userId){
        return cartRepo.mergeValidCartToOrder(userId);
    }

    public boolean mergeCartToOrder(UUID uuid){
        return cartRepo.mergeCartToOrder(uuid);
    }
}
