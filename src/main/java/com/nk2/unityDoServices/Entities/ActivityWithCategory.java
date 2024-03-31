package com.nk2.unityDoServices.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "activityWithCategoryView")
public class ActivityWithCategory {

    @Id
    @Column(name = "activityId")
    private Long activityId;

    @Column(name = "activityName")
    private String activityName;

    @Column(name = "categoryId")
    private Long categoryId;

    @Column(name = "mainCategoryId")
    private Long mainCategoryId;

    @Column(name = "mainCategory")
    private String mainCategory;

    // Constructors, getters, and setters
}
