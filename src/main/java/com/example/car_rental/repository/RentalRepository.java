package com.example.car_rental.repository;

import com.example.car_rental.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByStatus(String status);

    List<Rental> findByCustomerNameContainingIgnoreCase(String customerName);

    @Query("SELECT SUM(r.totalCost) FROM Rental r")
    BigDecimal getTotalRevenue();
}