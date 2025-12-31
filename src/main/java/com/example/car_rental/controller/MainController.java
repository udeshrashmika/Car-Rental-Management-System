package com.example.car_rental.controller;

import com.example.car_rental.model.Vehicle;
import com.example.car_rental.repository.RentalRepository;
import com.example.car_rental.repository.VehicleRepository;
import com.example.car_rental.service.RentalService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class MainController {

    private final VehicleRepository vehicleRepository;
    private final RentalRepository rentalRepository;
    private final RentalService rentalService;

    // Constructor Injection
    public MainController(VehicleRepository vehicleRepository, RentalRepository rentalRepository, RentalService rentalService) {
        this.vehicleRepository = vehicleRepository;
        this.rentalRepository = rentalRepository;
        this.rentalService = rentalService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping({"/", "/home"})
    public String showDashboard(Model model) {


        model.addAttribute("vehicles", vehicleRepository.findAll());


        model.addAttribute("rentals", rentalRepository.findAll());


        model.addAttribute("activeRentals", rentalRepository.findByStatus("Active"));


        model.addAttribute("totalCount", vehicleRepository.count());
        model.addAttribute("rentedCount", vehicleRepository.findAll().stream().filter(v -> "RENTED".equals(v.getStatus())).count());
        model.addAttribute("maintenanceCount", vehicleRepository.findAll().stream().filter(v -> "MAINTENANCE".equals(v.getStatus())).count());

        model.addAttribute("newVehicle", new Vehicle());

        return "index";
    }

    @PostMapping("/addVehicle")
    public String addVehicle(@ModelAttribute Vehicle vehicle) {
        if (vehicle.getStatus() == null || vehicle.getStatus().isEmpty()) {
            vehicle.setStatus("AVAILABLE");
        }
        vehicleRepository.save(vehicle);
        return "redirect:/home";
    }


    @PostMapping("/rent")
    public String rentVehicle(@RequestParam String customerName,
                              @RequestParam Long vehicleId,
                              @RequestParam LocalDate startDate,
                              @RequestParam LocalDate endDate) {


        rentalService.rentVehicle(customerName, vehicleId, startDate, endDate);

        return "redirect:/home";
    }


    @PostMapping("/return")
    public String returnVehicle(@RequestParam Long rentalId) {
        rentalService.returnVehicle(rentalId);
        return "redirect:/home";
    }

    @PostMapping("/vehicle/status")
    public String changeStatus(@RequestParam Long id, @RequestParam String status) {
        Vehicle v = vehicleRepository.findById(id).orElseThrow();
        v.setStatus(status);
        vehicleRepository.save(v);
        return "redirect:/home";
    }


}