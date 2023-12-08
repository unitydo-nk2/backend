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
    private Integer categoryId;

    @Column(name = "category", nullable = false, length = 100)
    private String category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mainCategory", nullable = false)
    private Maincategory mainCategory;

    public Maincategory getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(Maincategory mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getId() {
        return categoryId;
    }

    public void setId(Integer id) {
        this.categoryId = id;
    }
}