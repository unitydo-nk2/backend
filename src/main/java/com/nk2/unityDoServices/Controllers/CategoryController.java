package com.nk2.unityDoServices.Controllers;

import com.nk2.unityDoServices.DTOs.AllCategoryDTO;
import com.nk2.unityDoServices.DTOs.CategoryDTO;
import com.nk2.unityDoServices.Services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryServices categoryServices;

    @GetMapping("")
    public List<CategoryDTO> getCategoryList() {
        return categoryServices.getCategoryList();
    }

    @GetMapping("/list")
    public List<AllCategoryDTO> getCategoryWithSubCategory() {
        return categoryServices.getCategoryWithSubCategory();
    }
}
