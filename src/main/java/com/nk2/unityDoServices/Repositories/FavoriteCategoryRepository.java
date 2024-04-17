package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.Category;
import com.nk2.unityDoServices.Entities.FavoriteCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteCategoryRepository extends JpaRepository<FavoriteCategory,Integer> {
    @Query(value="SELECT f.* " +
            "FROM favoriteCategory f " +
            "WHERE f.userId = :userId " +
            "ORDER BY f.categoryRank ASC ", nativeQuery = true)
    List<FavoriteCategory> findCategoryFavoriteByUserId(Integer userId);
}