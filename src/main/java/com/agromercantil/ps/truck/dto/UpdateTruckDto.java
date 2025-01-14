package com.agromercantil.ps.truck.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateTruckDto(
        @JsonProperty("brand")
        Optional<String> brand,
        @JsonProperty("model")
        Optional<String> model,
        @JsonProperty("manufactoringYear")
        Optional<Integer> manufactoringYear
) {
}
