package com.insurance.premium.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "region_data")
public class RegionData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bundesland")
    private String bundesland;

    @Column(name = "land")
    private String land;

    @Column(name = "stadt")
    private String stadt;

    @Column(name = "postleitzahl")
    private String postleitzahl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
}

