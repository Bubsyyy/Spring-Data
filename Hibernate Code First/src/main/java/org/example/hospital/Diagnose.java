package org.example.hospital;

import lombok.Data;

import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "diagnoses")
public class Diagnose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String comments;
    @ManyToOne
    private GeneralPractitioner generalPractitioner;
    @ManyToMany(mappedBy = "diagnoses")
    private Set<Patient> patients;
}
