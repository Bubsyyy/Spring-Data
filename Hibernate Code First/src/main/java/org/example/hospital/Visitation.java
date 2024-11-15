package org.example.hospital;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "visitations")
public class Visitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @PastOrPresent
    private LocalDate date;
    private String comment;
    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    private GeneralPractitioner doctor;

}
