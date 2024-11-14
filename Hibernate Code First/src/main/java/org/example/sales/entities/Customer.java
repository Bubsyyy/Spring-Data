package org.example.sales.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(name = "credit_card_number")
    private String creditCardNumber;
    @OneToMany(mappedBy = "customer", targetEntity = Sale.class)
    private Set<Sale> sales;
}
