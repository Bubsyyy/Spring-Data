package org.example.university.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "teachers")
public class Teacher extends Person {
    private String email;
    @Column(name = "salary_per_hour")
    private BigDecimal salaryPerHour;
    @OneToMany(mappedBy = "teacher", targetEntity = Course.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Course> courses;
}