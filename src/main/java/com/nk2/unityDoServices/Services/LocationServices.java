package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.DTOs.Location.LocationDTO;
import com.nk2.unityDoServices.Entities.Location;
import com.nk2.unityDoServices.Repositories.LocationRepository;
import com.nk2.unityDoServices.Utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LocationServices {

    @Autowired
    private LocationRepository repository;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;

    public Location save(LocationDTO location) {
        Location newLocation = modelMapper.map(location, Location.class);
        newLocation.setLocationName(location.getLocationName());
        newLocation.setLocationLongitude(location.getLocationLongitude());
        newLocation.setLocationLatitude(location.getLocationLatitude());
        newLocation.setGoogleMapLink(location.getGoogleMapLink());
        repository.saveAndFlush(newLocation);
        return modelMapper.map(newLocation, Location.class);
    }

    public Location findById(Integer locationId){
        Location location =  repository.findById(locationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "location id " + locationId + " Does Not Exist !!!"));
        return location;
    }
}
