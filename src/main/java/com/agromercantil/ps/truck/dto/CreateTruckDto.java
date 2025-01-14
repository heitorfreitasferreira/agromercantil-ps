package com.agromercantil.ps.truck.dto;

public record CreateTruckDto(
        String licensePlate,
        String brand,
        String model,
        Integer manufacturingYear
) {

}
