package com.agromercantil.ps;

import com.agromercantil.ps.fipe.FipeService;
import com.agromercantil.ps.truck.TruckModel;
import com.agromercantil.ps.truck.TruckRepository;
import com.agromercantil.ps.truck.TruckService;
import com.agromercantil.ps.truck.dto.CreateTruckDto;
import com.agromercantil.ps.truck.dto.TruckDto;
import com.agromercantil.ps.truck.dto.UpdateTruckDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TruckServiceTest {

    @Mock
    private TruckRepository truckRepository;

    @Mock
    private FipeService fipeService;

    @InjectMocks
    private TruckService truckService;

    private CreateTruckDto createTruckDto;
    private UpdateTruckDto updateTruckDto;
    private TruckModel truckModel;

    @BeforeEach
    void setUp() {
        createTruckDto = new CreateTruckDto("ABC1234", "VOLKSWAGEN", "Constellation 6x4", 2012);
        updateTruckDto = new UpdateTruckDto(Optional.of(null), Optional.of(null), Optional.of(2011));
        truckModel = new TruckModel(1L, "ABC1234", "VOLKSWAGEN", "Constellation 6x4", 2012, 125872.0);
    }


    @Test
    void shouldValidateTruckWithFipeServiceBeforeRegistering() {
        when(fipeService.validateTruckAndGetPrice(createTruckDto.brand(), createTruckDto.model(), createTruckDto.manufacturingYear()))
                .thenReturn(50000.0);
        when(truckRepository.save(any(TruckModel.class))).thenReturn(truckModel);

        TruckDto result = truckService.registerTruck(createTruckDto);

        assertNotNull(result);
        assertEquals(50000.0, result.fipePrice());
        verify(fipeService).validateTruckAndGetPrice(createTruckDto.brand(), createTruckDto.model(), createTruckDto.manufacturingYear());
    }

    @Test
    void shouldSaveFipePriceDuringRegistration() {
        when(fipeService.validateTruckAndGetPrice(createTruckDto.brand(), createTruckDto.model(), createTruckDto.manufacturingYear()))
                .thenReturn(50000.0);
        when(truckRepository.save(any(TruckModel.class))).thenReturn(truckModel);

        TruckDto result = truckService.registerTruck(createTruckDto);

        assertNotNull(result);
        assertEquals(50000.0, result.fipePrice());
        verify(truckRepository).save(any(TruckModel.class));
    }

    @Test
    void shouldSaveFipePriceDuringUpdate() {
        when(truckRepository.findById(1L)).thenReturn(Optional.of(truckModel));
        when(fipeService.validateTruckAndGetPrice(updateTruckDto.brand().orElse(truckModel.getBrand()), updateTruckDto.model().orElse(truckModel.getModel()), updateTruckDto.manufactoringYear().orElse(truckModel.getManufacturingYear())))
                .thenReturn(55000.0);
        when(truckRepository.save(any(TruckModel.class))).thenReturn(truckModel);

        TruckDto result = truckService.updateTruck(1L, updateTruckDto);

        assertNotNull(result);
        assertEquals(55000.0, result.fipePrice());
        verify(truckRepository).save(any(TruckModel.class));
    }

}