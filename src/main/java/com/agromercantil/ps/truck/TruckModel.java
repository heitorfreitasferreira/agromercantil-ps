package com.agromercantil.ps.truck;

import com.agromercantil.ps.truck.dto.CreateTruckDto;
import jakarta.persistence.*;

@Entity()
@Table(name = "truck_model")
public class TruckModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(unique = true, nullable = false)
    public String licensePlate;
    @Column(nullable = false)
    public String brand;
    @Column(nullable = false)
    public String model;
    @Column(nullable = false)
    public Integer manufacturingYear;
    @Column(nullable = false)
    public Double fipePrice;

    public TruckModel() {
    }

    public TruckModel(Long id, String licensePlate, String brand, String model, Integer manufacturingYear, Double fipePrice) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.manufacturingYear = manufacturingYear;
        this.fipePrice = fipePrice;
    }

    public TruckModel(CreateTruckDto dto, Double fipePrice) {
        this.licensePlate = dto.licensePlate();
        this.brand = dto.brand();
        this.model = dto.model();
        this.manufacturingYear = dto.manufacturingYear();
        this.fipePrice = fipePrice;
    }

    public Long getId() {
        return id;
    }


    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(Integer manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public Double getFipePrice() {
        return fipePrice;
    }

    public void setFipePrice(Double fipePrice) {
        this.fipePrice = fipePrice;
    }
}
