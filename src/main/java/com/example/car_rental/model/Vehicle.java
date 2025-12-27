package com.example.car_rental.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;       // e.g., Car, Truck

    private String makeModel;  // e.g., Honda Civic

    @Column(unique = true)     // VIN must be unique
    private String vin;

    private BigDecimal dailyRate; // e.g., 45.00

    private String status;     // Available, Rented, In-Maintenance

    // --- Constructors ---
    public Vehicle() {
    }

    public Vehicle(String type, String makeModel, String vin, BigDecimal dailyRate, String status) {
        this.type = type;
        this.makeModel = makeModel;
        this.vin = vin;
        this.dailyRate = dailyRate;
        this.status = status;
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMakeModel() { return makeModel; }
    public void setMakeModel(String makeModel) { this.makeModel = makeModel; }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public BigDecimal getDailyRate() { return dailyRate; }
    public void setDailyRate(BigDecimal dailyRate) { this.dailyRate = dailyRate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
