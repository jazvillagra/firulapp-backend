package com.github.firulapp.service.impl;

import com.github.firulapp.domain.Breed;
import com.github.firulapp.dto.BreedDto;
import com.github.firulapp.exceptions.BreedException;
import com.github.firulapp.mapper.impl.BreedMapper;
import com.github.firulapp.repository.BreedRepository;
import com.github.firulapp.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BreedServiceImpl implements BreedService {

    @Autowired
    private BreedRepository breedRepository;

    @Autowired
    private BreedMapper breedMapper;

    @Override
    public List<BreedDto> getAllBreeds(){
        return breedMapper.mapAsList(breedRepository.findAll());
    }

    @Override
    public BreedDto getBreedById(Long id) throws BreedException {
        Optional<Breed> breed = breedRepository.findById(id);
        return breed.map(b -> breedMapper.mapToDto(b)).orElseThrow(BreedException.notFound(id));
    }

    @Override
    public BreedDto saveBreed(BreedDto breedDto) {
        if(breedDto.getId() != null){
            breedDto.setModifiedAt(LocalDateTime.now());
            return breedMapper.mapToDto(breedRepository.save(breedMapper.mapToEntity(breedDto)));
        }else {
            Breed entity = breedMapper.mapToEntity(breedDto);
            entity.setStatus(Boolean.TRUE);
            entity.setCreatedAt(LocalDateTime.now());
            return breedMapper.mapToDto(breedRepository.save(entity));
        }
    }

    @Override
    public List<BreedDto> getBreedBySpeciesId(Long speciesId) {
        return breedMapper.mapAsList(breedRepository.findBySpeciesId(speciesId));
    }

    @Override
    public void delete(BreedDto breedDto) {
        Optional<Breed> breed = breedRepository.findById(breedDto.getId());
        breed.ifPresent(value -> breedRepository.delete(value));
    }
}
