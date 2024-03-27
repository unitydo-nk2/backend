package com.nk2.unityDoServices.DTOs.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SetFavoriteCategoryDTO {
    private String  userEmail;
    private Integer mainCategoryId;
    private Integer categoryRank;
}
