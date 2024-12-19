package com.project.bibliotheque.services;

import com.project.bibliotheque.dtos.DiscripteurDto;
import com.project.bibliotheque.entities.Discripteur;
import com.project.bibliotheque.mappers.DiscripteurMapper;
import com.project.bibliotheque.repositories.DiscripteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscripteurService {
    private final DiscripteurRepository discripteurRepository;
    private final DiscripteurMapper discripteurMapper;

    @Autowired
    public DiscripteurService(DiscripteurRepository discripteurRepository, DiscripteurMapper discripteurMapper) {
        this.discripteurRepository = discripteurRepository;
        this.discripteurMapper = discripteurMapper;
    }

    public DiscripteurDto addDiscripteur(DiscripteurDto discripteurDto){
        Discripteur discripteur = discripteurMapper.toEntity(discripteurDto);
        discripteur = discripteurRepository.save(discripteur);
        return discripteurMapper.toDto(discripteur);
    }

    public DiscripteurDto updateDiscripteur(DiscripteurDto discripteurDto){
        Discripteur discripteur = discripteurMapper.toEntity(discripteurDto);
        discripteur = discripteurRepository.save(discripteur);
        return discripteurMapper.toDto(discripteur);
    }

    public DiscripteurDto getDiscripteurById(Long id){
        Discripteur discripteur = discripteurRepository.findById(id).orElse(null);
        return discripteurMapper.toDto(discripteur);
    }

    public List<DiscripteurDto> getAllDiscripteur(){
        List<Discripteur> discripteurs = discripteurRepository.findAll();
        return discripteurs.stream()
                .map(discripteurMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteDiscripteurById(Long id){
        discripteurRepository.deleteById(id);
    }
}