package org.example.sales.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "store_locations")
public class StoreLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "location_name")
    private String name;
    @OneToMany(mappedBy = "storeLocation", targetEntity = Sale.class, cascade = CascadeType.ALL)
    private Set<Sale> sales;
}
