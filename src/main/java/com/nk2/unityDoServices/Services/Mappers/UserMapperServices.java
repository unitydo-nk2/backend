package com.nk2.unityDoServices.Services.Mappers;

import com.nk2.unityDoServices.DTOs.User.RegistrantDTO;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapperServices {
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
        dto.setRegistrationDate(((Timestamp) result[22]).toInstant());
        dto.setRegistrationId(((Number) result[21]).intValue());
        return dto;
    }
}
