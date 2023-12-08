package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.DTOs.AllCategoryDTO;
import com.nk2.unityDoServices.DTOs.CategoryDTO;
import com.nk2.unityDoServices.Entities.Category;
import com.nk2.unityDoServices.Entities.Maincategory;
import com.nk2.unityDoServices.Repositories.CategoryRepository;
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
    CategoryRepository categoryRepository;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;

    public List<CategoryDTO> getCategoryList() {
        List<Maincategory> categoryList = mainCategoryRepository.findAll();
        System.out.println("categoryList " + categoryList);
        return listMapper.mapList(categoryList, CategoryDTO.class, modelMapper);
    };

    public List<AllCategoryDTO> getCategoryWithSubCategory(){
        List<Category> categoryList = categoryRepository.findAll();
        System.out.println("categoryList " + categoryList);
        return  listMapper.mapList(categoryList, AllCategoryDTO.class, modelMapper);
    }
}
