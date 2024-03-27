package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;

@Table(name = "favoriteCategory", indexes = {
        @Index(name = "fk_categoryInterested_mainCategory1_idx", columnList = "mainCategoryId"),
        @Index(name = "fk_categoryInterested_User1_idx", columnList = "userId")
})
@Entity
public class FavoriteCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favoriteCategoryId", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User userId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mainCategoryId", nullable = false)
    private MainCategory mainCategoryId;

    @Column(name = "categoryRank", nullable = false)
    private Integer categoryRank;

    public Integer getCategoryRank() {
        return categoryRank;
    }

    public void setCategoryRank(Integer categoryRank) {
        this.categoryRank = categoryRank;
    }

    public MainCategory getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(MainCategory mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}