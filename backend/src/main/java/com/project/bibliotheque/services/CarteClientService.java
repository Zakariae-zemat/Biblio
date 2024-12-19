package com.project.bibliotheque.services;

import com.project.bibliotheque.dtos.CarteClientDto;
import com.project.bibliotheque.entities.CarteClient;
import com.project.bibliotheque.entities.Client;
import com.project.bibliotheque.mappers.CarteClientMapper;
import com.project.bibliotheque.repositories.CartClientRepository;
import com.project.bibliotheque.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarteClientService {
    private final CartClientRepository carteClientRepository;
    private final ClientRepository clientRepository;
    private final CarteClientMapper carteClientMapper;

    @Autowired
    public CarteClientService(CartClientRepository carteClientRepository, ClientRepository clientRepository, CarteClientMapper carteClientMapper) {
        this.carteClientRepository = carteClientRepository;
        this.clientRepository = clientRepository;
        this.carteClientMapper = carteClientMapper;
    }

    public CarteClientDto addCarteClient(String email) {
        Client client = clientRepository.findByEmail(email);
        CarteClient carteClient = new CarteClient();
        if (client != null) {
            if(client.getAddresse().equalsIgnoreCase("casablanca")){
                carteClient.setEstResident(true);
            }else{
                carteClient.setEstResident(false);
                carteClient.setPrix(50);
            }
            carteClient.setClient(client);
            carteClient.setImageUrl(client.getImageUrl());
            client.setCarteClient(carteClient);
            client.setEstRegistered(true);
            // Set the dateFin one year after creation
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, 1);
            carteClient.setDateFin(c.getTime());

            carteClientRepository.save(carteClient);
            clientRepository.save(client);

            // Convert the CarteClient entity to CarteClientDto before returning
            return carteClientMapper.toCarteClientDto(carteClient);
        }
        return null;
    }

    public CarteClientDto updateCarteClient(CarteClientDto carteClientDto){
        CarteClient carteClient = carteClientMapper.toCarteClient(carteClientDto);
        CarteClient updatedCarteClient = carteClientRepository.save(carteClient);
        return carteClientMapper.toCarteClientDto(updatedCarteClient);
    }

    public CarteClientDto getCarteClientById(Long id){
        CarteClient carteClient = carteClientRepository.findById(id).orElse(null);
        return carteClient != null ? carteClientMapper.toCarteClientDto(carteClient) : null;
    }

    public List<CarteClientDto> getAllCarteClients(){
        List<CarteClient> carteClients = carteClientRepository.findAll();
        return carteClients.stream()
                .map(carteClientMapper::toCarteClientDto)
                .collect(Collectors.toList());
    }

    public void deleteCarteClientById(Long id){
        carteClientRepository.deleteById(id);
    }
}