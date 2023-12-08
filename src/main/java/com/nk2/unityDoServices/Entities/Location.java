package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "location")
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locationId", nullable = false)
    private Integer id;

    @Column(name = "locationName", nullable = false, length = 150)
    private String locationName;

    @Column(name = "googleMapLink", nullable = false, length = 300)
    private String googleMapLink;

    @Column(name = "locationLongitude", nullable = false)
    private Double locationLongitude;

    @Column(name = "locationLatitude", nullable = false)
    private Double locationLatitude;

    public Double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public Double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public String getGoogleMapLink() {
        return googleMapLink;
    }

    public void setGoogleMapLink(String googleMapLink) {
        this.googleMapLink = googleMapLink;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}