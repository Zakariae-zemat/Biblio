package com.project.bibliotheque.services;

import com.project.bibliotheque.dtos.BibliothecaireDto;
import com.project.bibliotheque.entities.Bibliothecaire;
import com.project.bibliotheque.mappers.BibliothecaireMapper;
import com.project.bibliotheque.repositories.BibliothecaireRipository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BibliothecaireService {
    private final BibliothecaireRipository bibliothecaireRepository;
    private final BibliothecaireMapper bibliothecaireMapper;

    @Autowired
    public BibliothecaireService(BibliothecaireRipository bibliothecaireRepository, BibliothecaireMapper bibliothecaireMapper) {
        this.bibliothecaireRepository = bibliothecaireRepository;
        this.bibliothecaireMapper = bibliothecaireMapper;
    }

    public BibliothecaireDto addBibliothecaire(BibliothecaireDto bibliothecaireDto){
        Bibliothecaire bibliothecaire = bibliothecaireMapper.toEntity(bibliothecaireDto);
        return bibliothecaireMapper.toDto(bibliothecaireRepository.save(bibliothecaire));
    }

    public BibliothecaireDto updateBibliothecaire(BibliothecaireDto bibliothecaireDto){
        Bibliothecaire bibliothecaire = bibliothecaireMapper.toEntity(bibliothecaireDto);
        return bibliothecaireMapper.toDto(bibliothecaireRepository.save(bibliothecaire));
    }

    public BibliothecaireDto getBibliothecaireById(Long id){
        Bibliothecaire bibliothecaire = bibliothecaireRepository.findById(id).orElse(null);
        return bibliothecaireMapper.toDto(bibliothecaire);
    }
    public BibliothecaireDto getBibliothecaireByEmail(String email){
        Bibliothecaire bibliothecaire = bibliothecaireRepository.findByEmail(email);
        return bibliothecaireMapper.toDto(bibliothecaire);
    }

    public List<BibliothecaireDto> getAllBibliothecaires(){
        List<Bibliothecaire> bibliothecaires = bibliothecaireRepository.findAll();
        return bibliothecaires.stream().map(bibliothecaireMapper::toDto).collect(Collectors.toList());
    }

    public void deleteBibliothecaireById(Long id){
        bibliothecaireRepository.deleteById(id);
    }
}