package org.example.university.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "students")
public class Student extends Person {
    @Column(name = "average_grade")
    private Double averageGrade;
    private int attendance;
    @ManyToMany(mappedBy = "students", targetEntity = Course.class)
    private Set<Course> courses;
}