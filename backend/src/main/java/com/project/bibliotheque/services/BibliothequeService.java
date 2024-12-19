package com.project.bibliotheque.services;

import com.project.bibliotheque.dtos.BibliothequeDto;
import com.project.bibliotheque.entities.Bibliotheque;
import com.project.bibliotheque.mappers.BibliothequeMapper;
import com.project.bibliotheque.repositories.BibliothequeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BibliothequeService {
    private final BibliothequeRepository bibliothequeRepository;
    private final BibliothequeMapper bibliothequeMapper;

    @Autowired
    public BibliothequeService(BibliothequeRepository bibliothequeRepository, BibliothequeMapper bibliothequeMapper) {
        this.bibliothequeRepository = bibliothequeRepository;
        this.bibliothequeMapper = bibliothequeMapper;
    }

    public BibliothequeDto addBibliotheque(BibliothequeDto bibliothequeDto){
        Bibliotheque bibliotheque = bibliothequeMapper.toEntity(bibliothequeDto);
        return bibliothequeMapper.toDto(bibliothequeRepository.save(bibliotheque));
    }

    public BibliothequeDto updateBibliotheque(BibliothequeDto bibliothequeDto){
        Bibliotheque bibliotheque = bibliothequeMapper.toEntity(bibliothequeDto);
        return bibliothequeMapper.toDto(bibliothequeRepository.save(bibliotheque));
    }

    public BibliothequeDto getBibliothequeById(Long id){
        Bibliotheque bibliotheque = bibliothequeRepository.findById(id).orElse(null);
        return bibliothequeMapper.toDto(bibliotheque);
    }

    public List<BibliothequeDto> getAllBibliotheques(){
        List<Bibliotheque> bibliotheques = bibliothequeRepository.findAll();
        return bibliotheques.stream().map(bibliothequeMapper::toDto).collect(Collectors.toList());
    }

    public void deleteBibliothequeById(Long id){
        bibliothequeRepository.deleteById(id);
    }
}