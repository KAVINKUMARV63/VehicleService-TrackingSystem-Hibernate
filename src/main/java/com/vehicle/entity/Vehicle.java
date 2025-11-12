package com.vehicle.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String registrationNumber;
    private String brand;
    private String model;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ServiceRecord> serviceRecords = new ArrayList<>();

    // ✅ Default constructor (required by Hibernate)
    public Vehicle() {}

    // ✅ Parameterized constructor (this was missing)
    public Vehicle(String registrationNumber, String brand, String model, Customer customer) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.customer = customer;
    }

    // ✅ Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<ServiceRecord> getServiceRecords() { return serviceRecords; }
    public void setServiceRecords(List<ServiceRecord> serviceRecords) { this.serviceRecords = serviceRecords; }
}
