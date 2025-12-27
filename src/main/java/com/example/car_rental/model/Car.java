package com.example.car_rental.model;

public class Car {
    import jakarta.persistence.*;

    @Entity
    @Table(name = "cars")
    public class Car {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String brand;
        private String model;


}
