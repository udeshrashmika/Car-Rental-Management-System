package com.example.car_rental.service;

import com.example.car_rental.model.Rental;
import com.example.car_rental.model.Vehicle;
import com.example.car_rental.repository.RentalRepository;
import com.example.car_rental.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentalService {


    private final RentalRepository rentalRepository;
    private final VehicleRepository vehicleRepository;


    public RentalService(RentalRepository rentalRepository, VehicleRepository vehicleRepository) {
        this.rentalRepository = rentalRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public void rentVehicle(String customerName, Long vehicleId, LocalDate startDate, LocalDate endDate) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + vehicleId));

        if ("AVAILABLE".equals(vehicle.getStatus())) {
            Rental rental = new Rental();
            rental.setCustomerName(customerName);
            rental.setVehicle(vehicle);
            rental.setStartDate(startDate);
            rental.setEndDate(endDate);
            rental.setStatus("ACTIVE");

            rentalRepository.save(rental);

            vehicle.setStatus("RENTED");
            vehicleRepository.save(vehicle);
        } else {

            throw new RuntimeException("Vehicle is not available for rent");
        }
    }

    public void returnVehicle(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found with ID: " + rentalId));

        if ("COMPLETED".equals(rental.getStatus())) return;

        LocalDate returnDate = LocalDate.now();

        if (rental.getStartDate() == null) {
            rental.setStartDate(returnDate);
        }

        rental.setReturnDate(returnDate);

        long days = ChronoUnit.DAYS.between(rental.getStartDate(), returnDate);
        if (days < 1) days = 1;

        BigDecimal dailyRate = rental.getVehicle().getDailyRate();
        BigDecimal totalCost = dailyRate.multiply(BigDecimal.valueOf(days));

        rental.setTotalCost(totalCost);
        rental.setStatus("COMPLETED");
        rentalRepository.save(rental);

        Vehicle vehicle = rental.getVehicle();
        vehicle.setStatus("AVAILABLE");
        vehicleRepository.save(vehicle);
    }
}
