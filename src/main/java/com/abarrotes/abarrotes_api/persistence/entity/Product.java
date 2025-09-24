package com.abarrotes.abarrotes_api.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;
    @Column(length = 50, nullable = false)
    private String name;
    // === Foreign key Category ===
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "category_id", // name id of category in Product
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_category_id"), // name of foreign key
            insertable = false,
            updatable = false
    )
    private Category category;
    @Column(name = "bar_code", length = 150)
    private String barCode;
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal price;
    @Column(name = "amount_stock")
    private int amountStock;
    private boolean status;
}
