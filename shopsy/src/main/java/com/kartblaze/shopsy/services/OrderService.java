package com.kartblaze.shopsy.services;

import com.kartblaze.shopsy.entities.Order;
import com.kartblaze.shopsy.entities.AppUser;
import java.util.List;

public interface OrderService {
    List<Order> findByUser(AppUser user);
    Order placeOrder(AppUser user);
}
