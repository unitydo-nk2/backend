package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.ActivityFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivityFavoriteRepository extends JpaRepository<ActivityFavorite,Integer> {
    @Query(value="SELECT u.* " +
            "FROM activityFavorite u " +
            "WHERE u.activityId = :activityId "+
            "AND u.userId = :userId ", nativeQuery = true)
    ActivityFavorite findActivityFavorite(Integer activityId, Integer userId);

}
