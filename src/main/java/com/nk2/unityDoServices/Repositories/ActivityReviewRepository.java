package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.ActivityReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityReviewRepository extends JpaRepository<ActivityReview,Integer> {
    @Query(value="SELECT a.* FROM registration r " +
            "INNER JOIN activityReview a " +
            "ON a.registrationId = r.registrationId " +
            "WHERE r.activityId = :activityId " +
            "ORDER BY a.createTime ASC;", nativeQuery = true)
    List<ActivityReview> findAllActivityReviewByActivityId(Integer activityId);

    @Query(value="SELECT a.* FROM activityReview a " +
            "WHERE a.registrationId = :id " +
            "ORDER BY a.createTime ASC;", nativeQuery = true)
    ActivityReview findAllByRegistrationId (Integer id);

}