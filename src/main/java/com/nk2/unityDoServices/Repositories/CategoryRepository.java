package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.Activity;
import com.nk2.unityDoServices.Entities.Category;
import com.nk2.unityDoServices.Entities.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
