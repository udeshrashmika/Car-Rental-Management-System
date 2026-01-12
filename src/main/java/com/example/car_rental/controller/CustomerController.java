package com.example.car_rental.controller;

import com.example.car_rental.model.Rental;
import com.example.car_rental.model.Vehicle;
import com.example.car_rental.repository.RentalRepository;
import com.example.car_rental.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private RentalRepository rentalRepository;
    @Autowired private FileStorageService fileStorageService;


    @GetMapping("/customer")
    public String showCustomerPage(Model model) {

        List<Vehicle> availableCars = vehicleRepository.findByStatusIgnoreCase("AVAILABLE");
        System.out.println("Found Available Cars: " + availableCars.size());

        model.addAttribute("vehicles", availableCars);
        return "customer_booking";
    }


    @PostMapping("/customer/book")
    public String bookVehicle(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam Long vehicleId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam("licenseImage") MultipartFile licenseImage) {

        try {
            Rental rental = new Rental();

            rental.setCustomerName(name);
            rental.setCustomerEmail(email);
            rental.setCustomerPhone(phone);


            if (!licenseImage.isEmpty()) {
                String fileName = fileStorageService.storeFile(licenseImage);
                rental.setLicenseImage(fileName);
            }


            Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
            rental.setVehicle(vehicle);


            rental.setStartDate(LocalDate.parse(startDate));
            rental.setEndDate(LocalDate.parse(endDate));


            rental.setStatus("PENDING");

            rentalRepository.save(rental);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return "redirect:/customer?success";
    }
}