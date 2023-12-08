package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.DTOs.ActivityWithRegistrationNumberDTO;
import com.nk2.unityDoServices.DTOs.ActivityWithStatusDTO;
import com.nk2.unityDoServices.DTOs.ActivityWithUserCountDTO;
import com.nk2.unityDoServices.DTOs.RegistrantDTO;
import com.nk2.unityDoServices.Repositories.CategoryRepository;
import com.nk2.unityDoServices.Repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapperService {
    public List<RegistrantDTO> mapToRegistrantDTO(List<Object[]> resultList) {
        return resultList.stream()
                .map(this::mapToRegistrantDTO)
                .collect(Collectors.toList());
    }

    private RegistrantDTO mapToRegistrantDTO(Object[] result) {

        RegistrantDTO dto = new RegistrantDTO();
        dto.setUserId(((Number) result[0]).intValue());
        dto.setUsername((String) result[1]);
        dto.setName((String) result[3]);
        dto.setSurName((String) result[4]);
        dto.setGender((String) result[7]);
        dto.setStatus((String) result[20]);
        dto.setRegistrationDate(((Timestamp) result[19]).toInstant());
        return dto;
    }
}
