package com.github.firulapp.service.impl;

import com.github.firulapp.constants.PetStatus;
import com.github.firulapp.constants.ReportStatus;
import com.github.firulapp.constants.ReportType;
import com.github.firulapp.domain.ReportPet;
import com.github.firulapp.dto.FoundPetReportDto;
import com.github.firulapp.dto.PetDto;
import com.github.firulapp.dto.ReportPetDto;
import com.github.firulapp.exceptions.*;
import com.github.firulapp.mapper.impl.ReportPetMapper;
import com.github.firulapp.repository.ReportPetRepository;
import com.github.firulapp.service.PetService;
import com.github.firulapp.service.ReportPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ReportPetServiceImpl implements ReportPetService {

    @Autowired
    private ReportPetRepository repository;

    @Autowired
    private ReportPetMapper mapper;

    @Autowired
    private PetService petService;

    @Override
    public List<ReportPetDto> getReportsByLocation(Double latitudeMin, Double longitudeMin, Double latitudeMax, Double longitudeMax) {
        return mapper.mapAsList(repository.findByLocationLatitudeAndLocationLongitude(latitudeMin, longitudeMin, latitudeMax, longitudeMax));
    }

    @Override
    public List<ReportPetDto> getReportsByCreationDate(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return mapper.mapAsList(repository.findByCreatedAt(LocalDateTime.parse(startDate, formatter), LocalDateTime.parse(endDate, formatter)));
    }

    @Override
    public ReportPetDto saveReport(ReportPetDto reportPetDto) throws ReportPetException {
        if(reportPetDto.getId() != null){
            if(reportPetDto.getModifiedBy()!=null) {
                reportPetDto.setModifiedAt(LocalDateTime.now());
            }else{
                throw ReportPetException.missingData();
            }
        } else {
            reportPetDto.setCreatedAt(LocalDateTime.now());
        }
        
        return mapper.mapToDto(repository.save(mapper.mapToEntity(reportPetDto)));
    }

    @Override
    public ReportPetDto getReportById(Long id) throws ReportPetException {
        Optional<ReportPet> report = repository.findById(id);
        if(report.isPresent()){
            return mapper.mapToDto(report.get());
        } else {
            throw ReportPetException.notFound(id);
        }
    }

    @Override
    public ReportPetDto saveLostPetReport(ReportPetDto reportPetDto) throws ReportPetException {
        PetDto pet = null;
        try {
            pet = petService.getPetById(reportPetDto.getPetId());
            petService.updatePetStatus(pet.getId(), PetStatus.PERDIDA, reportPetDto.getCreatedBy());
            reportPetDto.setReportType(ReportType.MASCOTA_PERDIDA);
            reportPetDto.setStatus(ReportStatus.ABIERTO);
            return saveReport(reportPetDto);
        } catch (PetException petException) {
            throw ReportPetException.petNotFound(reportPetDto.getPetId());
        }
    }

    @Override
    public ReportPetDto saveFoundPetReport(FoundPetReportDto foundPetReportDto) throws ReportPetException {
        PetDto petDto = foundPetReportDto.getPet();
        ReportPetDto reportPetDto = foundPetReportDto.getReport();
        try {
            petDto.setStatus(PetStatus.ENCONTRADA);
            PetDto petSaved = petService.registerOrUpdatePet(petDto);
            reportPetDto.setPetId(petSaved.getId());
            reportPetDto.setReportType(ReportType.MASCOTA_ENCONTRADA);
            reportPetDto.setStatus(ReportStatus.ABIERTO);
            return saveReport(reportPetDto);
        } catch (PetException | AppUserException | SpeciesException | BreedException e) {
            throw ReportPetException.petNotSaved(petDto);
        }
    }

    @Override
    public List<ReportPetDto> getOpenReports() {
        return mapper.mapAsList(repository.findByStatus(ReportStatus.ABIERTO));
    }

    @Override
    public ReportPetDto closeReport(ReportPetDto reportPetDto) throws ReportPetException {
        try {
            if(reportPetDto.getReportType().equals(ReportType.MASCOTA_PERDIDA)){
                PetDto pet = petService.getPetById(reportPetDto.getPetId());
                pet.setStatus(PetStatus.NORMAL);
                petService.registerOrUpdatePet(pet);
            }
            reportPetDto.setStatus(ReportStatus.CERRADO);
            return mapper.mapToDto(repository.save(mapper.mapToEntity(reportPetDto)));
        } catch (BreedException | PetException | AppUserException | SpeciesException e){
            throw ReportPetException.petNotFound(reportPetDto.getPetId());
        }
    }
}
