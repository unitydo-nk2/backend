package com.nk2.unityDoServices.DTOs.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllCategoryDTO {
    private Integer id;
    private String category;
    private Integer mainCategoryId;
    private String mainCategory;
}
