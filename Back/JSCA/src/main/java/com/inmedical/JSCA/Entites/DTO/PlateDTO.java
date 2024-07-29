package com.inmedical.JSCA.Entites.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
public class PlateDTO {
    @NotNull
    public String number;
    @NotNull
    private Set<DayPlateDTO> days;
}

