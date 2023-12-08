package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.Activityreview;
import com.nk2.unityDoServices.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityReviewRepository extends JpaRepository<Activityreview,Integer> {
}
