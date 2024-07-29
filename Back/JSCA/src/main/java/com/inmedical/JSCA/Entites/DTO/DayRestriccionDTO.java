package com.inmedical.JSCA.Entites.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
public class DayRestriccionDTO {
    @Temporal(TemporalType.TIME)
    private LocalTime hourStart;
    @NotNull
    @Temporal(TemporalType.TIME)
    private LocalTime hourFinal;
    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate dateDay;
}
