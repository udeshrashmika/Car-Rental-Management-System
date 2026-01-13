package com.example.car_rental.controller;

import com.example.car_rental.model.Rental;
import com.example.car_rental.model.Vehicle;
import com.example.car_rental.repository.RentalRepository;
import com.example.car_rental.repository.VehicleRepository;
import com.example.car_rental.service.FileStorageService;
import com.example.car_rental.service.RentalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final VehicleRepository vehicleRepository;
    private final RentalRepository rentalRepository;
    private final RentalService rentalService;
    private final FileStorageService fileStorageService;

    public AdminController(VehicleRepository vehicleRepository,
                           RentalRepository rentalRepository,
                           RentalService rentalService,
                           FileStorageService fileStorageService) {
        this.vehicleRepository = vehicleRepository;
        this.rentalRepository = rentalRepository;
        this.rentalService = rentalService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {

        List<Rental> allRentals = rentalRepository.findAll();

        model.addAttribute("rentalHistory", allRentals);

        List<Rental> pendingRentals = allRentals.stream()
                .filter(r -> "PENDING".equalsIgnoreCase(r.getStatus()))
                .collect(Collectors.toList());
        model.addAttribute("pendingRentals", pendingRentals);

        List<Rental> activeRentals = allRentals.stream()
                .filter(r -> "APPROVED".equalsIgnoreCase(r.getStatus()))
                .collect(Collectors.toList());
        model.addAttribute("activeRentals", activeRentals);

        List<Vehicle> activeVehicles = vehicleRepository.findAll().stream()
                .filter(v -> !"DELETED".equals(v.getStatus()))
                .collect(Collectors.toList());
        model.addAttribute("vehicles", activeVehicles);

        model.addAttribute("newVehicle", new Vehicle());

        long count = activeVehicles.size();
        model.addAttribute("totalCount", count);

        model.addAttribute("rentedCount", vehicleRepository.countByStatus("RENTED"));
        model.addAttribute("maintenanceCount", vehicleRepository.countByStatus("MAINTENANCE"));

        BigDecimal revenue = rentalRepository.getTotalRevenue();
        model.addAttribute("totalRevenue", revenue != null ? revenue : BigDecimal.ZERO);

        return "admin_dashboard";
    }

    @PostMapping("/approve")
    public String approveRental(@RequestParam Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow();
        rental.setStatus("APPROVED");
        rentalRepository.save(rental);
        Vehicle v = rental.getVehicle();
        v.setStatus("RENTED");
        vehicleRepository.save(v);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/reject")
    public String rejectRental(@RequestParam Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow();
        rental.setStatus("REJECTED");
        rentalRepository.save(rental);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/return")
    public String returnVehicle(@RequestParam Long rentalId, RedirectAttributes redirectAttributes) {
        rentalService.returnVehicle(rentalId);
        Rental rental = rentalRepository.findById(rentalId).orElseThrow();
        redirectAttributes.addFlashAttribute("message",
                "✅ Return Successful! Customer: " + rental.getCustomerName() +
                        " | Final Cost: Rs. " + rental.getTotalCost());
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/addVehicle")
    public String addVehicle(@ModelAttribute Vehicle vehicle,
                             @RequestParam("vehicleImage") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String fileName = fileStorageService.storeFile(file);
                vehicle.setImage(fileName);
            }

            if (vehicle.getStatus() == null || vehicle.getStatus().isEmpty()) {
                vehicle.setStatus("AVAILABLE");
            }
            vehicleRepository.save(vehicle);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/vehicle/status")
    public String changeStatus(@RequestParam Long id, @RequestParam String status) {
        Vehicle v = vehicleRepository.findById(id).orElseThrow();
        v.setStatus(status);
        vehicleRepository.save(v);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/vehicle/delete")
    public String deleteVehicle(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        Vehicle v = vehicleRepository.findById(id).orElseThrow();
        v.setStatus("DELETED");
        vehicleRepository.save(v);

        redirectAttributes.addFlashAttribute("message", "✅ Vehicle removed from fleet successfully!");

        return "redirect:/admin/dashboard";
    }
}