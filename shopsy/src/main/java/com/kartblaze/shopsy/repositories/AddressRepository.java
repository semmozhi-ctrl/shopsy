package com.kartblaze.shopsy.repositories;

import com.kartblaze.shopsy.entities.Address;
import com.kartblaze.shopsy.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(AppUser user);
}
