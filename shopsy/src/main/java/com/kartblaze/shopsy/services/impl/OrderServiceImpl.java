package com.kartblaze.shopsy.services.impl;

import com.kartblaze.shopsy.entities.Order;
import com.kartblaze.shopsy.entities.AppUser;
import com.kartblaze.shopsy.entities.CartItem;
import com.kartblaze.shopsy.entities.Product;
import com.kartblaze.shopsy.repositories.OrderRepository;
import com.kartblaze.shopsy.repositories.CartRepository;
import com.kartblaze.shopsy.services.OrderService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Order> findByUser(AppUser user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public Order placeOrder(AppUser user) {
        var cartOpt = cartRepository.findByUser(user);
        if (cartOpt.isPresent()) {
            var cart = cartOpt.get();
            Order order = Order.builder()
                .user(user)
                .products(cart.getItems().stream().map(CartItem::getProduct).toList())
                .orderDate(LocalDateTime.now())
                .totalAmount(cart.getItems().stream().mapToDouble(item -> item.getProduct().getPrice()).sum())
                .build();
            cart.getItems().clear();
            cartRepository.save(cart);
            return orderRepository.save(order);
        }
        return null;
    }
}
