package com.nk2.unityDoServices.Repositories;

import com.nk2.unityDoServices.Entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Integer> {
}
