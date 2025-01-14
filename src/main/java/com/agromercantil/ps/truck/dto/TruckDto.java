package com.agromercantil.ps.truck.dto;

import com.agromercantil.ps.truck.TruckModel;

public record TruckDto(
        Long id,
        String licensePlate,
        String brand,
        String model,
        Integer manufacturingYear,
        Double fipePrice
) {
    public TruckDto(TruckModel truck) {
        this(truck.id, truck.licensePlate, truck.brand, truck.model, truck.manufacturingYear, truck.fipePrice);
    }
}