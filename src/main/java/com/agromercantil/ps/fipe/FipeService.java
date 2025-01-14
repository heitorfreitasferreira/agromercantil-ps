package com.agromercantil.ps.fipe;


import com.agromercantil.ps.exceptions.FipeNotFound;
import com.agromercantil.ps.fipe.dto.FipeBrand;
import com.agromercantil.ps.fipe.dto.FipeModel;
import com.agromercantil.ps.fipe.dto.FipeVehicleDetail;
import com.agromercantil.ps.fipe.dto.FipeYear;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FipeService {
    private final WebClient fipeWebClient;

    public FipeService(WebClient.Builder webClientBuilder) {
        this.fipeWebClient = webClientBuilder.baseUrl("https://fipe.parallelum.com.br/api/v2").build();
    }

    public Double validateTruckAndGetPrice(String brandName, String modelName, Integer year) {

        return validateYearAndFetchPrice(brandName, modelName, year);

    }

    private Double validateYearAndFetchPrice(String brandName, String modelName, Integer year) {
        var brandId = validateBrand(brandName);
        var modelId = validateModel(brandName, modelName);

        var years = fipeWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trucks/brands/{brandId}/models/{modelId}/years")
                        .build(brandId, modelId))
                .retrieve()
                .bodyToFlux(FipeYear.class)
                .collectList()
                .block();

        var yearCode = years.stream()
                .filter(y -> y.getName().contains(String.valueOf(year)))
                .findFirst()
                .orElseThrow(() -> new FipeNotFound("Year not avaliable for model: " + modelName + ", brand: " + brandName, years.stream().map(FipeYear::getName).reduce((a, b) -> a + ", " + b).orElse("")))
                .getCode();

        var vehicleDetail = fipeWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trucks/brands/{brandId}/models/{modelId}/years/{yearId}")
                        .build(brandId, modelId, yearCode))
                .retrieve()
                .bodyToMono(FipeVehicleDetail.class)
                .block();

        if (vehicleDetail == null)
            throw new FipeNotFound("Year not avaliable for model: " + modelName + ", brand: " + brandName, years.stream().map(FipeYear::getName).reduce((a, b) -> a + ", " + b).orElse(""));
        return Double.valueOf(vehicleDetail.getPrice().replace("R$", "").replace(".", "").replace(",", ".").trim());
    }

    private String validateBrand(String brandName) {
        var brands = fipeWebClient.get()
                .uri("/trucks/brands")
                .retrieve()
                .bodyToFlux(FipeBrand.class)
                .collectList()
                .block();

        return brands.stream()
                .filter(brand -> brand.getName().equalsIgnoreCase(brandName))
                .findFirst()
                .orElseThrow(() -> new FipeNotFound("Brand not found: " + brandName, brands.stream().map(FipeBrand::getName).reduce((a, b) -> a + ", " + b).orElse("")))
                .getCode();
    }

    private String validateModel(String brandName, String modelName) {
        var brandId = validateBrand(brandName);

        var models = fipeWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trucks/brands/{brandId}/models")
                        .build(brandId))
                .retrieve()
                .bodyToFlux(FipeModel.class)
                .collectList()
                .block();

        if (models == null)
            throw new FipeNotFound("Model not found for brand: " + brandName, models.stream().map(FipeModel::getName).reduce((a, b) -> a + ", " + b).orElse(""));

        return models.stream()
                .filter(model -> model.getName().toLowerCase().contains(modelName.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new FipeNotFound("Model not found for brand: " + brandName, models.stream().map(FipeModel::getName).reduce((a, b) -> a + ", " + b).orElse("")))
                .getCode();
    }
}
