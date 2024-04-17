package com.nk2.unityDoServices.Controllers;

import com.nk2.unityDoServices.DTOs.Category.AllCategoryDTO;
import com.nk2.unityDoServices.DTOs.Category.CategoryDTO;
import com.nk2.unityDoServices.DTOs.Category.FavoriteCategoryDTO;
import com.nk2.unityDoServices.DTOs.Category.SetFavoriteCategoryDTO;
import com.nk2.unityDoServices.DTOs.User.UserDTO;
import com.nk2.unityDoServices.Entities.FavoriteCategory;
import com.nk2.unityDoServices.Services.CategoryServices;
import com.nk2.unityDoServices.Services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryServices categoryServices;

    @Autowired
    private UserServices userServices;

    @GetMapping("")
    public List<CategoryDTO> getCategoryList() {
        return categoryServices.getCategoryList();
    }

    @GetMapping("/list")
    public List<AllCategoryDTO> getCategoryWithSubCategory() {
        return categoryServices.getCategoryWithSubCategory();
    }

    @GetMapping("/favorite")
    public List<FavoriteCategoryDTO> getUserCategoriesFavorite() {
        return categoryServices.getUserCategoriesFavorite();
    }

    @PatchMapping("/favoriteCategory/{id}")
    public FavoriteCategory setUserFavoriteCategory(HttpServletRequest httpServletRequest, @PathVariable Integer id, @Valid @RequestPart("favoriteCategory")
                                                            FavoriteCategoryDTO favoriteCategory) {
        return userServices.updateUserFavoriteCategory(httpServletRequest,id,favoriteCategory);
    }
//    @PatchMapping("")
//    public List<FavoriteCategory> updateFavoriteCategories(){
//
//    }


}
