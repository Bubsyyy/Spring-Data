package org.example.hospital;

import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


import java.time.LocalDate;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String address;
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
    @Column(name = "date_of_birth")
    @PastOrPresent
    private LocalDate dateOfBirth;
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "is_med_ins_included")
    private boolean isMedicalInsuranceIncluded;
    @ManyToOne(optional = false)
    private GeneralPractitioner doctor;
    @ManyToMany
    @JoinTable(
            name = "patients_diagnoses",
            joinColumns = {@JoinColumn(name = "patient_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "diagnose_id", referencedColumnName = "id")}
    )
    private Set<Diagnose> diagnoses;
    @OneToMany(mappedBy = "prescribedFor")
    private Set<Medicament> medicaments;
}