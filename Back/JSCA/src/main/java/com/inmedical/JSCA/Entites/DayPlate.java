package com.inmedical.JSCA.Entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "DayPlate_Table")
public class DayPlate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long dayId;
    @NotNull
    @Temporal(TemporalType.TIME)
    private LocalTime hourStart;
    @NotNull
    @Temporal(TemporalType.TIME)
    private LocalTime hourFinal;
    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate dateDay;

}
