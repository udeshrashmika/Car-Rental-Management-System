package com.carrental.carrentalservice.service; // MEKA WENAS UNA

import com.carrental.carrentalservice.model.Vehicle;
import com.carrental.carrentalservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public void updateStatus(Long id, String newStatus) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(id);
        if (vehicleOptional.isPresent()) {
            Vehicle vehicle = vehicleOptional.get();
            vehicle.setStatus(newStatus);
            vehicleRepository.save(vehicle);
        } else {
            throw new RuntimeException("Vehicle not found with id: " + id);
        }
    }

    public long getCountByStatus(String status) {
        return vehicleRepository.countByStatus(status);
    }
}