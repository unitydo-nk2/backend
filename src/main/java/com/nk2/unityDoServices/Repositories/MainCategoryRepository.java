package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MainCategoryRepository extends JpaRepository<MainCategory,Integer> {

    @Query(value="SELECT c.* " +
            "FROM mainCategory c " +
            "INNER JOIN favoriteCategory f ON c.mainCategoryId = f.mainCategoryId " +
            "WHERE f.userId = :userId "+
            "ORDER BY f.categoryRank ", nativeQuery = true)
    List<MainCategory> findActivityFavoriteByUserId(Integer userId);
}
