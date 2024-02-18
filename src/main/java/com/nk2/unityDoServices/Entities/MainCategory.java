package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "mainCategory")
@Entity
public class MainCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mainCategoryId", nullable = false)
    private Integer id;

    @Column(name = "mainCategory", nullable = false, length = 100)
    private String mainCategory;

    @Column(name = "description", length = 300)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}