package com.fitech.app.users.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricTypeUOMDetailDto {
    private Integer id;
    private Integer metricTypeId;
    private String metricTypeName;
    private Integer unitOfMeasureId;
    private String unitOfMeasureName;
    private String unitOfMeasureSymbol;
    private String unitOfMeasureDescription;
    private boolean isDefault;
} 