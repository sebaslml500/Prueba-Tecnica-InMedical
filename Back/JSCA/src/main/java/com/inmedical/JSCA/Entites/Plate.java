package com.inmedical.JSCA.Entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "plateTable")

public class Plate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plateId;
    @NotNull
    private String number;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "plateDay_id", referencedColumnName = "plateId")
    Set<DayPlate> dayPlates = new HashSet<>();
}
