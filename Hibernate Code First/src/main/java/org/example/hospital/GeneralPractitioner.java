package org.example.hospital;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "doctors")
public class GeneralPractitioner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "doctor", targetEntity = Patient.class)
    private Set<Patient> patients;
    @OneToMany(mappedBy = "generalPractitioner", targetEntity = Diagnose.class)
    private Set<Diagnose> diagnoses;
    @OneToMany(mappedBy = "prescribedBy", targetEntity = Medicament.class)
    private Set<Medicament> medicaments;
    @OneToMany(mappedBy = "doctor", targetEntity = Visitation.class)
    private Set<Visitation> visitations;
}
