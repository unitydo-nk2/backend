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

    @Query(value = "SELECT a.* " +
            "FROM activityReview a " +
            "INNER JOIN registration r " +
            "ON a.registrationId = r.registrationId  " +
            "WHERE r.activityid = :activityId  " +
            "AND r.userId = :userId  " +
            "AND r.status = 'success'", nativeQuery = true)
    ActivityReview existsByRegistrationId (Integer activityId, Integer userId);
}