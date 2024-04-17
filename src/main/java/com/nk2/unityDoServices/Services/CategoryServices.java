package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.DTOs.Category.AllCategoryDTO;
import com.nk2.unityDoServices.DTOs.Category.CategoryDTO;
import com.nk2.unityDoServices.DTOs.Category.FavoriteCategoryDTO;
import com.nk2.unityDoServices.DTOs.User.UserDetailsDTO;
import com.nk2.unityDoServices.Entities.Category;
import com.nk2.unityDoServices.Entities.FavoriteCategory;
import com.nk2.unityDoServices.Entities.MainCategory;
import com.nk2.unityDoServices.Repositories.CategoryRepository;
import com.nk2.unityDoServices.Repositories.FavoriteCategoryRepository;
import com.nk2.unityDoServices.Repositories.MainCategoryRepository;
import com.nk2.unityDoServices.Utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServices {
    @Autowired
    MainCategoryRepository mainCategoryRepository;

    @Autowired
    FavoriteCategoryRepository favoriteCategoryRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserServices userServices;

    public List<CategoryDTO> getCategoryList() {
        List<MainCategory> categoryList = mainCategoryRepository.findAll();
        System.out.println("categoryList " + categoryList);
        return listMapper.mapList(categoryList, CategoryDTO.class, modelMapper);
    };

    public List<AllCategoryDTO> getCategoryWithSubCategory(){
        List<Category> categoryList = categoryRepository.findAll();
        System.out.println("categoryList " + categoryList);
        return  listMapper.mapList(categoryList, AllCategoryDTO.class, modelMapper);
    }

    public List<FavoriteCategoryDTO> getUserCategoriesFavorite() {
        UserDetailsDTO user = userServices.getUserByEmail();
        System.out.println("find fav category of : "+ user.getUserId());
        List<FavoriteCategory> categoryList = favoriteCategoryRepository.findCategoryFavoriteByUserId(user.getUserId());
        return  listMapper.mapList(categoryList, FavoriteCategoryDTO.class, modelMapper);
    }



}
