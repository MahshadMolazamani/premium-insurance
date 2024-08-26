package com.insurance.premium.entity;


import jakarta.persistence.*;
import lombok.Data;

/**
 * Diese Entitätsklasse repräsentiert Daten zu einer Region.
 * Sie enthält Informationen wie Bundesland, Land, Stadt und Postleitzahl sowie eine Verknüpfung zur Location.
 */
@Entity
@Data
@Table(name = "region_data")
public class RegionData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bundesland_iso")
    private String bundeslandIso;

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

