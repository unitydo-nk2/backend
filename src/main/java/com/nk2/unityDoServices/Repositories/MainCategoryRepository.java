package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.Maincategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainCategoryRepository extends JpaRepository<Maincategory,Integer> {
}
