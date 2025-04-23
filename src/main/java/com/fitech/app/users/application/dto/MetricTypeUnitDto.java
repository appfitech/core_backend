package com.fitech.app.users.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricTypeUnitDto {
    private Integer id;
    
    @NotNull(message = "El tipo de métrica es requerido")
    private Integer metricTypeId;
    
    @NotNull(message = "La unidad de medida es requerida")
    private Integer unitOfMeasureId;
    
    private boolean isDefault;
} 