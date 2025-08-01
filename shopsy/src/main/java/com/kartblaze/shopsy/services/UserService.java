package com.kartblaze.shopsy.services;

import com.kartblaze.shopsy.entities.AppUser;
import com.kartblaze.shopsy.entities.Address;
import com.kartblaze.shopsy.entities.Product;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByVerificationToken(String token);
    AppUser save(AppUser user);

    List<Address> getUserAddresses(AppUser user);
    List<Product> getUserWishlist(AppUser user);

    Address addAddress(AppUser user, Address address);
    Address updateAddress(Address address);
    void deleteAddress(Long addressId);
}
