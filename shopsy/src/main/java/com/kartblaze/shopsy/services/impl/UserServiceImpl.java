package com.kartblaze.shopsy.services.impl;

import com.kartblaze.shopsy.entities.AppUser;
import com.kartblaze.shopsy.repositories.UserRepository;
import com.kartblaze.shopsy.repositories.AddressRepository;
import com.kartblaze.shopsy.repositories.WishlistRepository;
import com.kartblaze.shopsy.entities.Address;
import com.kartblaze.shopsy.entities.Product;
import com.kartblaze.shopsy.entities.Wishlist;
import com.kartblaze.shopsy.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final WishlistRepository wishlistRepository;

    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository, WishlistRepository wishlistRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<AppUser> findByVerificationToken(String token) {
        return userRepository.findByVerificationToken(token);
    }

    @Override
    public AppUser save(AppUser user) {
        return userRepository.save(user);
    }

    @Override
    public List<Address> getUserAddresses(AppUser user) {
        return addressRepository.findByUser(user);
    }

    @Override
    public List<Product> getUserWishlist(AppUser user) {
        List<Wishlist> wishlistItems = wishlistRepository.findByUser(user);
        return wishlistItems.stream().map(Wishlist::getProduct).toList();
    }

    @Override
    public Address addAddress(AppUser user, Address address) {
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }
}
