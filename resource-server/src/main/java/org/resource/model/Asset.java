package org.resource.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String symbol;
    private String description;
    @Enumerated(EnumType.STRING)
    private AssetType type;
    private double price;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="user_id")
    private User owner;
}
