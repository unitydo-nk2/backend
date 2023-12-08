package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.Image;
import com.nk2.unityDoServices.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Integer> {
    @Query(value = "select u.* from image u where u.label = 'poster'", nativeQuery = true)
    List<Image> findActivityPoster();
}
