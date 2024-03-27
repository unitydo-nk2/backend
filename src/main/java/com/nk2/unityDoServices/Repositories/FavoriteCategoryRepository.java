package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.FavoriteCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteCategoryRepository extends JpaRepository<FavoriteCategory,Integer> {
}
