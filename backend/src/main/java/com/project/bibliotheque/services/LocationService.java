package com.project.bibliotheque.services;

import com.project.bibliotheque.dtos.AlocationDto;
import com.project.bibliotheque.dtos.CarteClientDto;
import com.project.bibliotheque.dtos.DocumentDto;
import com.project.bibliotheque.dtos.PretDto;
import com.project.bibliotheque.entities.Document;
import com.project.bibliotheque.entities.Location;
import com.project.bibliotheque.entities.Reservation;
import com.project.bibliotheque.mappers.AlocationMapper;
import com.project.bibliotheque.mappers.CarteClientMapper;
import com.project.bibliotheque.mappers.DocumentMapper;
import com.project.bibliotheque.repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService  {
    private final LocationRepository locationRepository;
    private final DocumentMapper documentMapper;
    private final AlocationMapper alocationMapper;
    private final CarteClientMapper carteClientMapper;
    public LocationService(LocationRepository locationRepository,DocumentMapper documentMapper, CarteClientMapper carteClientMapper, AlocationMapper alocationMapper) {
        this.locationRepository = locationRepository;
        this.alocationMapper = alocationMapper;
        this.documentMapper = documentMapper;
        this.carteClientMapper = carteClientMapper;
    }
    public Location addLocation(DocumentDto documentDto, CarteClientDto carteClientDto){
        Location location = new Location();
        Document document = documentMapper.toEntity(documentDto);
        location.setDocument(document);
        location.setCarteClient(carteClientMapper.toCarteClient(carteClientDto));
        location.setDateDebut(java.sql.Date.valueOf(java.time.LocalDate.now()));
        location.setDateFin(java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(7)));
        location.setFraixExige(10);
        return locationRepository.save(location);
    }
    public Location updateLocation(Location location){
        return locationRepository.save(location);
    }
    public Location getLocationById(Long id){
        return locationRepository.findById(id).orElse(null);
    }
    public List<AlocationDto> getAllLocations(){
        List<Location> locations = locationRepository.findAll();
        List<AlocationDto> locationDto =  locations.stream().map(alocationMapper::toDto).collect(Collectors.toList());
        locationDto.forEach(AlocationDto::updateStatueLocation);
        return locationDto;
    }
    public void deleteLocationById(Long id){
        locationRepository.deleteById(id);
    }

    public Object calculMontant(Location location) {
        return null;
    }
}
