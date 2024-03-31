package com.nk2.unityDoServices.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "userCategoryRankingView")
public class UserCategoryRanking {

    @Id
    @Column(name = "favoriteCategoryId")
    private Long favoriteCategoryId;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "age")
    private Integer age;

    @Column(name = "mainCategoryId")
    private Long mainCategoryId;

    @Column(name = "categoryRank")
    private Integer categoryRank;

}
