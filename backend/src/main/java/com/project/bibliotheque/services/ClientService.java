package com.project.bibliotheque.services;

import com.google.zxing.WriterException;
import com.project.bibliotheque.dtos.ClientDto;
import com.project.bibliotheque.entities.Client;
import com.project.bibliotheque.mappers.ClientMapper;
import com.project.bibliotheque.repositories.ClientRepository;
import com.project.bibliotheque.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    @Autowired
    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public ClientDto addClient(ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDto(savedClient);
    }

    public byte[] getClientQRCode(String email) {
        Client client = clientRepository.findByEmail(email);
        if (client != null) {
            try {
                return QRCodeGenerator.generateQRCodeImage(client);
            } catch (WriterException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ClientDto updateClient(ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        Client updatedClient = clientRepository.save(client);
        return clientMapper.toDto(updatedClient);
    }

    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        return client != null ? clientMapper.toDto(client) : null;
    }
    public ClientDto getClientByEmail(String email) {
        Client client = clientRepository.findByEmail(email);
        return client != null ? clientMapper.toDto(client) : null;
    }
    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(clientMapper::toDto).collect(Collectors.toList());
    }

    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }
}