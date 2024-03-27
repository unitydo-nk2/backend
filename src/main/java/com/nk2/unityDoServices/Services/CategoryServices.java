package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.DTOs.Category.AllCategoryDTO;
import com.nk2.unityDoServices.DTOs.Category.CategoryDTO;
import com.nk2.unityDoServices.Entities.Category;
import com.nk2.unityDoServices.Entities.MainCategory;
import com.nk2.unityDoServices.Entities.User;
import com.nk2.unityDoServices.Repositories.CategoryRepository;
import com.nk2.unityDoServices.Repositories.MainCategoryRepository;
import com.nk2.unityDoServices.Repositories.UserRepository;
import com.nk2.unityDoServices.Utils.ListMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryServices {
    @Autowired
    MainCategoryRepository mainCategoryRepository;

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

    public List<CategoryDTO> getUserCategoriesFavorite(HttpServletRequest request, Integer userId) {
        userServices.validateIdBelong(request,userId);
        List<Category> categoryList = categoryRepository.findActivityFavoriteByUserId(userId);
        return  listMapper.mapList(categoryList, CategoryDTO.class, modelMapper);
    }
}
