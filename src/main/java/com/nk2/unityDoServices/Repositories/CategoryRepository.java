package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
