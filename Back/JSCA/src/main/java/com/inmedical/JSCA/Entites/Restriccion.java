package com.inmedical.JSCA.Entites;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table
public class Restriccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restriccionDayId;
    @NotNull
    private int numberFinal;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "restriccionDay_id", referencedColumnName = "restriccionDayId")
    Set<DayRestriccion> dayRestriccions = new HashSet<>();
}
