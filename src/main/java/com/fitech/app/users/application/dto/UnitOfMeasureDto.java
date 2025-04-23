package com.fitech.app.users.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfMeasureDto {
    private Integer id;
    
    @NotBlank(message = "El nombre es requerido")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    private String name;
    
    @NotBlank(message = "El símbolo es requerido")
    @Size(max = 10, message = "El símbolo no puede exceder los 10 caracteres")
    private String symbol;
    
    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;
} 