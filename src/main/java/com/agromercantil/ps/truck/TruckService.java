package com.agromercantil.ps.truck;

import com.agromercantil.ps.exceptions.NotFound;
import com.agromercantil.ps.fipe.FipeService;
import com.agromercantil.ps.truck.dto.CreateTruckDto;
import com.agromercantil.ps.truck.dto.TruckDto;
import com.agromercantil.ps.truck.dto.UpdateTruckDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TruckService {
    private final TruckRepository truckRepository;
    private final FipeService fipeService;

    public TruckService(TruckRepository truckRepository, FipeService fipeService) {
        this.truckRepository = truckRepository;
        this.fipeService = fipeService;
    }

    public Page<TruckDto> listTrucks(Pageable pageable) {
        return truckRepository.findAll(pageable).map(TruckDto::new);
    }

    public TruckDto registerTruck(@Valid CreateTruckDto createTruckDto) {
        var price = fipeService.validateTruckAndGetPrice(createTruckDto.brand(), createTruckDto.model(), createTruckDto.manufacturingYear());

        var truck = new TruckModel(createTruckDto, price);
        this.truckRepository.save(truck);
        return new TruckDto(truck);
    }

    public TruckDto updateTruck(Long id, @Valid UpdateTruckDto updateTruckDto) {
        var existingTruck = truckRepository.findById(id)
                .orElseThrow(() -> new NotFound(TruckModel.class, id));

        var price = fipeService.validateTruckAndGetPrice(updateTruckDto.brand()
                .orElse(existingTruck.brand), updateTruckDto.model().orElse(existingTruck.model), updateTruckDto.manufactoringYear().orElse(existingTruck.manufacturingYear));

        existingTruck.setBrand(updateTruckDto.brand().orElse(existingTruck.getBrand()));
        existingTruck.setModel(updateTruckDto.model().orElse(existingTruck.getModel()));
        existingTruck.setManufacturingYear(updateTruckDto.manufactoringYear().orElse(existingTruck.getManufacturingYear()));
        existingTruck.setFipePrice(price);

        truckRepository.save(existingTruck);

        return new TruckDto(existingTruck);
    }
}
