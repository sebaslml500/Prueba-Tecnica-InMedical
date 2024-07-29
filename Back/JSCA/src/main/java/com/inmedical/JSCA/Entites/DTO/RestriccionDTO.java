package com.inmedical.JSCA.Entites.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
public class RestriccionDTO {
    @NotNull
    private int numberFinal;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Set<DayRestriccionDTO> dateDay;
}
