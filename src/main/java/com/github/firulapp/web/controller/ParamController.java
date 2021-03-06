package com.github.firulapp.web.controller;

import com.github.firulapp.constants.ApiPaths;
import com.github.firulapp.dto.*;
import com.github.firulapp.exceptions.*;
import com.github.firulapp.service.*;
import com.github.firulapp.web.response.ListResponseDTO;
import com.github.firulapp.web.response.ObjectResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {ApiPaths.PARAM_ENDPOINTS_URL})
public class ParamController {

    @Autowired
    private SpeciesService speciesService;

    @Autowired
    private BreedService breedService;

    @Autowired
    private ConductRuleService conductRuleService;

    @Autowired
    private HelpPageService helpPageService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Autowired
    private PetCareService petCareService;

    @Autowired
    private CityService cityService;

    @Autowired
    private OrganizationRequestService organizationRequestService;

    @Autowired
    private AppUserService appUserService;

    @GetMapping(value = ApiPaths.SPECIES_ENDPOINTS)
    public ResponseEntity<ListResponseDTO> getAllSpecies(){
        return ResponseEntity.ok(ListResponseDTO.success(speciesService.getAllSpecies()));
    }

    @GetMapping(value = ApiPaths.SPECIES_BY_ID)
    public ResponseEntity<ObjectResponseDTO> getSpeciesById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(ObjectResponseDTO.success(speciesService.getSpeciesById(id)));
        } catch (Exception e){
            return new ResponseEntity<>(ObjectResponseDTO.error(SpeciesException.notFound(id).getErrorCode(), SpeciesException.notFound(id).getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = ApiPaths.SPECIES_ENDPOINTS)
    public ResponseEntity<ObjectResponseDTO> saveSpecies(@RequestBody SpeciesDto speciesDto) {
        return ResponseEntity.ok(ObjectResponseDTO.success(speciesService.saveSpecies(speciesDto)));
    }

    @DeleteMapping(value = ApiPaths.SPECIES_ENDPOINTS)
    public ResponseEntity<Void> deleteSpeciesRegister(@RequestBody SpeciesDto speciesDto) {
        speciesService.delete(speciesDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = ApiPaths.BREED_ENDPOINTS)
    public ResponseEntity<ListResponseDTO> getAllBreeds(){
        return ResponseEntity.ok(ListResponseDTO.success(breedService.getAllBreeds()));
    }

    @GetMapping(value = ApiPaths.BREED_BY_ID)
    public ResponseEntity<ObjectResponseDTO> getBreedById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(ObjectResponseDTO.success(breedService.getBreedById(id)));
        } catch (Exception e){
            BreedException exception = BreedException.notFound(id);
            return new ResponseEntity<>(ObjectResponseDTO.error(exception.getErrorCode(), exception.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = ApiPaths.BREED_ENDPOINTS)
    public ResponseEntity<ObjectResponseDTO> saveBreed(@RequestBody BreedDto breedDto){
        return ResponseEntity.ok(ObjectResponseDTO.success(breedService.saveBreed(breedDto)));
    }

    @GetMapping(value = ApiPaths.BREED_BY_SPECIES_ID)
    public ResponseEntity<ListResponseDTO> getBreedsBySpecies(@PathVariable(value = "id") Long speciesId){
        return ResponseEntity.ok(ListResponseDTO.success(breedService.getBreedBySpeciesId(speciesId)));
    }

    @DeleteMapping(value = ApiPaths.BREED_ENDPOINTS)
    public ResponseEntity<Void> deleteBreedRegister(@RequestBody BreedDto breedDto) {
        breedService.delete(breedDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = ApiPaths.CONDUCT_RULE_ENDPOINTS)
    public ResponseEntity<ListResponseDTO> getAllConductRules(){
        return ResponseEntity.ok(ListResponseDTO.success(conductRuleService.getAllRules()));
    }

    @GetMapping(value = ApiPaths.CONDUCT_RULE_BY_ID)
    public ResponseEntity<ObjectResponseDTO> getConductRuleById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(ObjectResponseDTO.success(conductRuleService.getRuleById(id)));
        } catch (Exception e){
            ConductRuleException exception = ConductRuleException.notFound(id);
            return new ResponseEntity<>(ObjectResponseDTO.error(exception.getErrorCode(), exception.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = ApiPaths.CONDUCT_RULE_ENDPOINTS)
    public ResponseEntity<ObjectResponseDTO> saveConductRule(@RequestBody ConductRuleDto conductRuleDto) {
        return ResponseEntity.ok(ObjectResponseDTO.success(conductRuleService.saveConductRule(conductRuleDto)));
    }

    @DeleteMapping(value = ApiPaths.CONDUCT_RULE_ENDPOINTS)
    public ResponseEntity<Void> deleteConductRule(@RequestBody ConductRuleDto conductRuleDto) {
        conductRuleService.delete(conductRuleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = ApiPaths.HELP_PAGE_ENDPOINTS)
    public ResponseEntity<ListResponseDTO> getAllHelpPages(){
        return ResponseEntity.ok(ListResponseDTO.success(helpPageService.getAllHelpPages()));
    }

    @GetMapping(value = ApiPaths.HELP_PAGE_BY_ID)
    public ResponseEntity<ObjectResponseDTO> getHelpPageById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(ObjectResponseDTO.success(helpPageService.getHelpPageById(id)));
        } catch (Exception e){
            HelpPageException exception = HelpPageException.notFound(id);
            return new ResponseEntity<>(ObjectResponseDTO.error(exception.getErrorCode(), exception.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = ApiPaths.HELP_PAGE_ENDPOINTS)
    public ResponseEntity<ObjectResponseDTO> saveHelpPage(@RequestBody HelpPageDto helpPageDto) {
        return ResponseEntity.ok(ObjectResponseDTO.success(helpPageService.saveHelpPage(helpPageDto)));
    }

    @DeleteMapping(value = ApiPaths.HELP_PAGE_ENDPOINTS)
    public ResponseEntity<Void> deleteHelpPage(@RequestBody HelpPageDto helpPageDto) {
        helpPageService.delete(helpPageDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = ApiPaths.SERVICE_TYPE_ENDPOINTS)
    public ResponseEntity<ListResponseDTO> getAllServiceTypes(){
        return ResponseEntity.ok(ListResponseDTO.success(serviceTypeService.getAllServiceTypes()));
    }

    @GetMapping(value = ApiPaths.SERVICE_TYPE_BY_ID)
    public ResponseEntity<ObjectResponseDTO> getServiceType(@PathVariable Long id){
        try {
            return ResponseEntity.ok(ObjectResponseDTO.success(serviceTypeService.getServiceTypeById(id)));
        } catch (Exception e){
            ServiceTypeException exception = ServiceTypeException.notFound(id);
            return new ResponseEntity<>(ObjectResponseDTO.error(exception.getErrorCode(), exception.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = ApiPaths.SERVICE_TYPE_ENDPOINTS)
    public ResponseEntity<ObjectResponseDTO> saveServiceType(@RequestBody ServiceTypeDto serviceTypeDto) {
        return ResponseEntity.ok(ObjectResponseDTO.success(serviceTypeService.saveServiceType(serviceTypeDto)));
    }

    @DeleteMapping(value = ApiPaths.SERVICE_TYPE_ENDPOINTS)
    public ResponseEntity<Void> deleteServiceType(@RequestBody ServiceTypeDto serviceTypeDto) {
        serviceTypeService.delete(serviceTypeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = ApiPaths.PET_CARE_ENDPOINTS)
    public ResponseEntity<ListResponseDTO> getAllPetCare(){
        return ResponseEntity.ok(ListResponseDTO.success(petCareService.getAllPetCares()));
    }

    @GetMapping(value = ApiPaths.PET_CARE_BY_ID)
    public ResponseEntity<ObjectResponseDTO> getPetCareId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(ObjectResponseDTO.success(petCareService.getPetCareById(id)));
        } catch (Exception e) {
            PetCareException exception = PetCareException.notFound(id);
            return new ResponseEntity<>(ObjectResponseDTO.error(exception.getErrorCode(), exception.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(ApiPaths.PET_CARE_ENDPOINTS)
    public ResponseEntity<ObjectResponseDTO> savePetCare(@RequestBody PetCareDto petCareDto) {
        return ResponseEntity.ok(ObjectResponseDTO.success(petCareService.savePetCare(petCareDto)));
    }

    @DeleteMapping(value = ApiPaths.PET_CARE_ENDPOINTS)
    public ResponseEntity<Void> deletePetCareRegister(@RequestBody PetCareDto petCareDto) {
        petCareService.delete(petCareDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = ApiPaths.CITY_ENDPOINTS)
    public ResponseEntity<ListResponseDTO> getAllCities(){
        return ResponseEntity.ok(ListResponseDTO.success(cityService.getAllCities()));
    }

    @GetMapping(value = ApiPaths.CITY_BY_ID)
    public ResponseEntity<ObjectResponseDTO> getCityById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(ObjectResponseDTO.success(cityService.getCityById(id)));
        } catch (Exception e){
            CityException exception = CityException.notFound(id);
            return new ResponseEntity<>(ObjectResponseDTO.error(exception.getErrorCode(), exception.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = ApiPaths.CITY_BY_COUNTRY)
    public ResponseEntity<ListResponseDTO> getCitiesByCountry(@PathVariable(name = "pais") String country){
        return ResponseEntity.ok(ListResponseDTO.success(cityService.getCitiesByCountry(country)));
    }

    @GetMapping(value = ApiPaths.CITY_BY_DISTRICT)
    public ResponseEntity<ListResponseDTO> getCitiesByDistrict(@PathVariable(name = "pais") String country,
                                                                 @PathVariable(name = "departamento") String district){
        return ResponseEntity.ok(ListResponseDTO.success(cityService.getCitiesByDistrictAndCountry(district, country)));
    }

    @PostMapping(value = ApiPaths.CITY_ENDPOINTS)
    public ResponseEntity<ObjectResponseDTO> saveCity(@RequestBody CityDto cityDto) {
        try {
            return ResponseEntity.ok(ObjectResponseDTO.success(cityService.saveCity(cityDto)));
        } catch (Exception e){
            CityException exception = CityException.duplicatedEntry(cityDto.getName(), cityDto.getCountry());
            return new ResponseEntity<>(ObjectResponseDTO.error(exception.getErrorCode(), exception.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = ApiPaths.CITY_ENDPOINTS)
    public ResponseEntity<ObjectResponseDTO> deleteCity(@RequestBody CityDto cityDto){
        try {
            cityService.deleteCity(cityDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            CityException exception = CityException.notFound(cityDto.getId());
            return new ResponseEntity<>(ObjectResponseDTO.error(exception.getErrorCode(), exception.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = ApiPaths.PET_CARE_BY_BREED_AND_SPECIES)
    public ResponseEntity<ListResponseDTO> getPetCareByBreedAndSpecies(@PathVariable(name = "breedId") Long breedId,
                                                                       @PathVariable(name = "speciesId") Long speciesId) {
        try {
            return ResponseEntity.ok(ListResponseDTO.success(petCareService.getPetCareBySpeciesAndBreed(speciesId, breedId)));
        } catch (PetCareException e) {
            return new ResponseEntity<>(ListResponseDTO.error(e.getErrorCode(), e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = ApiPaths.ORGANIZATION_APPROVE)
    public ResponseEntity<ObjectResponseDTO> approveOrganizationRequest(@PathVariable(name = "id") Long id,
                                                                        @PathVariable(name = "modifiedBy") Long modifiedBy){
        try {
            return ResponseEntity.ok(ObjectResponseDTO.success(appUserService.enableUser(id, modifiedBy)));
        } catch (AppUserException | OrganizationRequestException | EmailUtilsException e) {
            return new ResponseEntity<>(ObjectResponseDTO.error(e.getErrorCode(), e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = ApiPaths.ORGANIZATION_REJECT)
    public ResponseEntity<ObjectResponseDTO> rejectOrganizationRequest(@PathVariable(name = "id") Long id,
                                                                       @PathVariable(name = "modifiedBy") Long modifiedBy){
        try {
            return ResponseEntity.ok(ObjectResponseDTO.success(organizationRequestService.rejectRequest(id, modifiedBy)));
        } catch (OrganizationRequestException | OrganizationException | AppUserException e) {
            return new ResponseEntity<>(ObjectResponseDTO.error(e.getErrorCode(), e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = ApiPaths.ORGANIZATION_REQUEST_ENDPOINTS)
    public ResponseEntity<ListResponseDTO> getAllOrganizationRequests(){
        return ResponseEntity.ok(ListResponseDTO.success(organizationRequestService.getAllRequests()));
    }

}
