package com.agromercantil.ps.truck;

import com.agromercantil.ps.exceptions.FipeNotFound;
import com.agromercantil.ps.exceptions.NotFound;
import com.agromercantil.ps.truck.dto.CreateTruckDto;
import com.agromercantil.ps.truck.dto.TruckDto;
import com.agromercantil.ps.truck.dto.UpdateTruckDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/trucks")
public class TruckController {
    private final TruckService truckService;

    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @Operation(summary = "Cadastra um novo caminhão", description = "Valida os dados na tabela FIPE antes de salvar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Caminhão cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = TruckDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou já existentes",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<TruckDto> createTruck(@Valid @RequestBody CreateTruckDto createTruckDto) {
        TruckDto createdTruck = truckService.registerTruck(createTruckDto);
        return ResponseEntity.status(201).body(createdTruck);
    }

    @Operation(summary = "Lista caminhões cadastrados", description = "Suporta paginação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de caminhões retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Page.class))),
    })
    @GetMapping
    public ResponseEntity<Page<TruckDto>> listTrucks(Pageable pageable) {
        Page<TruckDto> trucks = truckService.listTrucks(pageable);
        return ResponseEntity.ok(trucks);
    }

    @Operation(summary = "Atualiza informações de um caminhão", description = "Valida os dados na tabela FIPE antes de atualizar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caminhão atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = TruckDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou já existentes",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Caminhão não encontrado",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<TruckDto> updateTruck(@PathVariable Long id, @Valid @RequestBody UpdateTruckDto updateTruckDto) {
        TruckDto updatedTruck = truckService.updateTruck(id, updateTruckDto);
        return ResponseEntity.ok(updatedTruck);
    }

    @ExceptionHandler({FipeNotFound.class})
    public ResponseEntity<Map<String, String>> handleException(FipeNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage(), "avaliable", ex.getAvaliable()));
    }

    @ExceptionHandler({NotFound.class})
    public ResponseEntity<String> handleException(NotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}