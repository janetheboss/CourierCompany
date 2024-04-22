package com.example.couriercompany.repository;

import com.example.couriercompany.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByStatusType(String type);
    Optional<Status> findById(Long id);

}
