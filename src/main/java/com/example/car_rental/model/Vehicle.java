package com.carrental.carrentalservice.model; // MEKA WENAS UNA

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String makeModel;
    private String vin;
    private double dailyRate;
    private String status;

    public Vehicle() {
    }

    public Vehicle(String makeModel, String vin, double dailyRate, String status) {
        this.makeModel = makeModel;
        this.vin = vin;
        this.dailyRate = dailyRate;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMakeModel() { return makeModel; }
    public void setMakeModel(String makeModel) { this.makeModel = makeModel; }
    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }
    public double getDailyRate() { return dailyRate; }
    public void setDailyRate(double dailyRate) { this.dailyRate = dailyRate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}