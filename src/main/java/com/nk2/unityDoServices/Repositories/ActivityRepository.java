package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.DTOs.Activity.ActivityWithStatusDTO;
import com.nk2.unityDoServices.Entities.Activity;
import com.nk2.unityDoServices.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Integer> {
    @Query(value="SELECT * " +
            "FROM activity a " +
            "WHERE a.activitydate >= NOW() " +
            "AND a.registerEndDate >= NOW() " +
            "AND a.activityStatus = 'Active' "+
            "AND a.activityOwener = :activityOwnerId "+
            "ORDER BY ABS(TIMESTAMPDIFF(SECOND, NOW(), a.activitydate)) ", nativeQuery = true)
    List<Activity> findByActivityOwner(Integer activityOwnerId);

    @Query(value="SELECT * " +
            "FROM activity a " +
            "WHERE a.activitydate >= NOW() " +
            "AND a.registerEndDate >= NOW() " +
            "AND a.activityStatus = 'Active' " +
            "OR a.activityStatus = 'Done' " +
            "ORDER BY ABS(TIMESTAMPDIFF(SECOND, NOW(), a.activitydate)) ", nativeQuery = true)
    List<Activity> findAllAvailableActivity();

    @Query(value="SELECT * " +
            "FROM activity a " +
            "AND a.activityStatus = 'Active' "+
            "ORDER BY ABS(TIMESTAMPDIFF(SECOND, NOW(), a.activitydate)) ", nativeQuery = true)
    List<Activity> findAllActiveActivity();

    @Query(value="SELECT a.*, COUNT(ah.activityHistoryId) as userCount " +
            "FROM activity a " +
            "INNER JOIN userActivityHistory ah ON a.activityId = ah.activityId " +
            "GROUP BY a.activityId " +
            "ORDER BY userCount DESC "+
            "limit 5", nativeQuery = true)
    List<Object[]> FindAllByFromUserCount();

    @Query(value = "SELECT a.*,r.status " +
            "FROM activity a INNER JOIN registration r " +
            "ON a.activityId = r.activityId " +
            "WHERE r.userId = :userId "+
            "ORDER BY r.registrationDate DESC"
            ,nativeQuery = true)
    List<Object[]> FindActivityRegisteredByUserId(Integer userId);

    @Query(value = "SELECT a.* FROM activity a INNER JOIN registration r " +
            "ON a.activityId = r.activityId " +
            "WHERE r.status = :status " +
            "AND r.userId = :userId " +
            "ORDER BY r.registrationDate DESC"
            ,nativeQuery = true)
    List<ActivityWithStatusDTO> FindActivityByStatusAndUserId(Integer userId, String status);

    @Query(value="SELECT a.*, COUNT(r.userId) as userCount " +
            "FROM activity a " +
            "INNER JOIN registration r ON a.activityId = r.activityId " +
            "GROUP BY a.activityId " +
            "ORDER BY a.activityName ASC ", nativeQuery = true)
    List<Object[]> FindActivityWithRegisterAmount();

    @Query(value="SELECT a.*, COUNT(r.userId) as userCount " +
            "FROM activity a " +
            "INNER JOIN registration r ON a.activityId = r.activityId " +
            "WHERE a.activityOwner = :activityId "+
            "GROUP BY a.activityId " +
            "ORDER BY a.activityName ASC ", nativeQuery = true)
    List<Object[]> FindActivityByStatusAndUserIdOwnedByActivityOwner(Integer activityId);

    @Query(value="SELECT a.* " +
            "FROM activity a " +
            "INNER JOIN activityFavorite f ON a.activityId = f.activityId " +
            "WHERE f.userId = :userId ", nativeQuery = true)
    List<Activity> findActivityFavorite(Integer userId);

    @Query(value="SELECT * " +
            "FROM activity a " +
            "WHERE a.activitydate >= NOW() " +
            "AND a.registerEndDate >= NOW() " +
            "ORDER BY ABS(TIMESTAMPDIFF(SECOND, NOW(), a.activitydate)) " +
            "limit 4 ", nativeQuery = true)
    List<Activity> findUpComingActivity();

    @Query(value="SELECT a.*\n" +
            "FROM activity a\n" +
            "INNER JOIN category c ON a.categoryId = c.categoryId\n" +
            "WHERE ( c.categoryId = (\n" +
            "    SELECT categoryId\n" +
            "    FROM activity\n" +
            "    WHERE activityId = :activityId\n" +
            ")\n" +
            "OR c.mainCategory = (\n" +
            "    SELECT mainCategory\n" +
            "    FROM category\n" +
            "    WHERE categoryId = (\n" +
            "        SELECT categoryId\n" +
            "        FROM activity\n" +
            "        WHERE activityId = :activityId\n" +
            "    )\n" +
            ") )\n" +
            "AND activitydate >= NOW() \n" +
            "AND a.registerEndDate >= NOW() \n" +
            "AND a.activityStatus = 'Active'\n" +
            " limit 10;", nativeQuery = true)
    List<Activity> findSimilarActivities(Integer activityId);
}
