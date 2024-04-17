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
@Table(name = "userfavoriteCategories")
public class UserFavoriteCategories {
    @Id
    @Column(name = "userId")
    private Long userId;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "mainCategory_1")
    private Integer mainCategory_1;

    @Column(name = "mainCategory_2")
    private Integer mainCategory_2;

    @Column(name = "mainCategory_3")
    private Integer mainCategory_3;

    @Column(name = "mainCategory_4")
    private Integer mainCategory_4;

    @Column(name = "mainCategory_5")
    private Integer mainCategory_5;
}
