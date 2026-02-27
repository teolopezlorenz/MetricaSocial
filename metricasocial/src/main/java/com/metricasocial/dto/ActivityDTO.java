package com.metricasocial.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ActivityDTO {

    @NotBlank(message = "El tipo de actividad es obligatorio")
    private String type;

    @NotNull(message = "Los puntos son obligatorios")
    @Min(value = 0, message = "Los puntos no pueden ser negativos")
    private Integer points;
    private LocalDateTime timestamp;
    private String notes;
}
