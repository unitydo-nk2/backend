package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "category", indexes = {
        @Index(name = "fk_Category_subCategory1_idx", columnList = "mainCategory")
})
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId", nullable = false)
    private Integer id;

    @Column(name = "category", nullable = false, length = 100)
    private String category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mainCategory", nullable = false)
    private MainCategory mainCategory;

    public MainCategory getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(MainCategory mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}