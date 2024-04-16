package com.example.couriercompany.repository;
import com.example.couriercompany.model.Curier;

import org.springframework.data.jpa.repository.JpaRepository;
public interface CurierRepository extends JpaRepository<Curier, Long> {

}
