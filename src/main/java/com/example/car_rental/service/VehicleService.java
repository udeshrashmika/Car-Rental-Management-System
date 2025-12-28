package com.example.car_rental.service;

import com.example.car_rental.model.Vehicle;
import com.example.car_rental.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;


    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }


    public void saveVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }
    public long countTotal() {
        return vehicleRepository.count();
    }

    public long countByStatus(String status) {
        return vehicleRepository.countByStatus(status);
    }

    public List<Vehicle> getVehiclesByStatus(String status) {
        return vehicleRepository.findByStatus(status);
    }
    public void updateStatus(Long vehicleId, String newStatus) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
        vehicle.setStatus(newStatus);
        vehicleRepository.save(vehicle);
    }

}