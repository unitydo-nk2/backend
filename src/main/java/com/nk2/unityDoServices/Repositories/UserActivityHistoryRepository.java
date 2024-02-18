package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.UserActivityHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivityHistoryRepository extends JpaRepository<UserActivityHistory,Integer> {}