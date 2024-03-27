package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.Activity;
import com.nk2.unityDoServices.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query(value="SELECT c.* " +
            "FROM mainCategory c " +
            "INNER JOIN favoriteCategory f ON c.categoryId = f.mainCategoryId " +
            "WHERE f.userId = :userId "+
            "ORDER BY f.rank ", nativeQuery = true)
    List<Category> findActivityFavoriteByUserId(Integer userId);

}
