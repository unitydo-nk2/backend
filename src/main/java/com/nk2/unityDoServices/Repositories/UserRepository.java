package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "select u.* from user u where u.username = :username", nativeQuery = true)
    User findUserbyUserName (String username);
    @Query(value = "select u.* from user u inner join registration r on u.userId = r.userid where r.activityId = :activityId", nativeQuery = true)
    List<User> findRegisteredUserFromActivityId (Integer activityId);
    @Query(value = "select u.* , r.status, r.registrationId, r.registrationDate from user u inner join registration r on u.userId = r.userid where r.activityId = :activityId", nativeQuery = true)
    List<Object[]> findRegisteredUserWithStatusFromActivityId (Integer activityId);
}
