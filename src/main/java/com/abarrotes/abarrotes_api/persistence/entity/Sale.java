package com.abarrotes.abarrotes_api.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "sales")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // === Foreign key Client ===
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "client_id", // name id of client in Sales
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_client_id"), // name of foreign key
            insertable = false,
            updatable = false
    )
    private Client client;
    @Column(name = "shoping_date", nullable = false)
    private Date shopingDate;
    @Column(length = 200)
    private String comment;
}
