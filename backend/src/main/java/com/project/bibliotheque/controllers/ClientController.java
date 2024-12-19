package com.project.bibliotheque.controllers;

import com.project.bibliotheque.dtos.ClientDto;
import com.project.bibliotheque.services.ClientService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8081", "https://library-fz9o.onrender.com/"})
@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientDto> getAllClients(){
        return clientService.getAllClients();
    }

    @GetMapping("/{client}")
    public ClientDto getClientByEmail(@RequestParam String email){
        return clientService.getClientByEmail(email);
    }

    @PostMapping
    public ClientDto addClient(@RequestBody ClientDto clientDto){
        return clientService.addClient(clientDto);
    }

    @PutMapping("/{id}")
    public ClientDto updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto){
        clientDto.setId(id);
        return clientService.updateClient(clientDto);
    }

    @GetMapping("/qrcode")
    public ResponseEntity<byte[]> getClientQRCode(@RequestParam String email) {
        byte[] qrCode = clientService.getClientQRCode(email);
        if (qrCode != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(qrCode, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteClientById(@PathVariable Long id){
        clientService.deleteClientById(id);
    }
}