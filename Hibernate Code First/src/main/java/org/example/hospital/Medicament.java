package org.example.hospital;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "medicaments")
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "prescribed_by")
    private GeneralPractitioner prescribedBy;
    @ManyToOne
    @JoinColumn(name = "prescribed_for")
    private Patient prescribedFor;
}
