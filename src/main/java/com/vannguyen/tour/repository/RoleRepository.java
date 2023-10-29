package com.vannguyen.tour.repository;

import com.vannguyen.tour.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
