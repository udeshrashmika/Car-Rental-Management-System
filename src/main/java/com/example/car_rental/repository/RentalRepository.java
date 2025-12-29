package com.example.car_rental.repository;

import com.example.car_rental.model.Rental;
import java.util.List;
import com.example.car_rental.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByStatus(String status);

    List<Rental> findByCustomerNameContainingIgnoreCase(String customerName);
}
