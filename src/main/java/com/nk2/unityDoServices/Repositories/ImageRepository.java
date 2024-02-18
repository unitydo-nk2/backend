package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Integer> {
    @Query(value = "select u.* from image u where u.label = 'poster'", nativeQuery = true)
    List<Image> findActivityPoster();


    @Query(value = "SELECT i.* FROM image i " +
            "WHERE i.activityId = :activityId ",nativeQuery = true)
    List<Image> getImageListbyActivityId(Integer activityId);

    @Query(value = "SELECT i.* FROM image i " +
            "WHERE i.activityId = :activityId " +
            "AND i.label = 'poster'"
            ,nativeQuery = true)
    List<Image> getImagePosterbyActivityId(Integer activityId);

}
