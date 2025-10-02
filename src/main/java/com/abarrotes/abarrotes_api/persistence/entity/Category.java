package com.abarrotes.abarrotes_api.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long id;
    @Column(length = 50)
    private String description;
    @Column(nullable = false)
    private boolean status;
    // === OneToMany with Product ===
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
