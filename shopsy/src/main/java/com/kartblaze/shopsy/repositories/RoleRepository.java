package com.kartblaze.shopsy.repositories;

import com.kartblaze.shopsy.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // Add custom query methods if needed
}
