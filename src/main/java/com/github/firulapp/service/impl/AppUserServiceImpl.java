package com.github.firulapp.service.impl;

import com.github.firulapp.constants.OrganizationRequestStatus;
import com.github.firulapp.domain.AppUser;
import com.github.firulapp.dto.*;
import com.github.firulapp.exceptions.AppUserException;
import com.github.firulapp.exceptions.EmailUtilsException;
import com.github.firulapp.exceptions.OrganizationRequestException;
import com.github.firulapp.mapper.impl.AppUserMapper;
import com.github.firulapp.repository.AppUserRepository;
import com.github.firulapp.service.*;
import com.github.firulapp.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    public static final String USER_TYPE_ORGANIZATION = "ORGANIZACION";

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Autowired
    private AppSessionService appSessionService;

    @Autowired
    private AppUserDeviceService appUserDeviceService;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private OrganizationRequestService organizationRequestService;

    @Autowired
    private OrganizationService organizationService;

    @Override
    public AppSessionDto registerUser(AppUserProfileDto registerUserDto) throws AppUserException, OrganizationRequestException {
        if(appUserRepository.findByUsername(registerUserDto.getUsername()) == null){
            if(appUserRepository.findByEmail(registerUserDto.getEmail()) == null) {
                if(registerUserDto.getEncryptedPassword().equals(registerUserDto.getConfirmPassword())) {
                    if(registerUserDto.getSurname()==null &&
                            !registerUserDto.getUserType().toUpperCase(Locale.ROOT).equals(USER_TYPE_ORGANIZATION)){
                        throw AppUserException.missingData();
                    }
                    AppUser userEntity = new AppUser();
                    userEntity.setEmail(registerUserDto.getEmail());
                    userEntity.setUsername(registerUserDto.getUsername());
                    userEntity.setEncryptedPassword(EncryptUtils.hashPassword(registerUserDto.getEncryptedPassword()));
                    userEntity.setLoggedIn(Boolean.TRUE);
                    userEntity.setUserType(registerUserDto.getUserType());
                    userEntity.setCreatedAt(LocalDateTime.now());
                    userEntity.setEnabled(!userEntity.getUserType().toUpperCase(Locale.ROOT).equals(USER_TYPE_ORGANIZATION));
                    AppUser appUser = appUserRepository.save(userEntity);
                    if(appUser.getUserType().toUpperCase(Locale.ROOT).equals(USER_TYPE_ORGANIZATION)){
                        OrganizationRequestDto organizationRequestDto = new OrganizationRequestDto();
                        organizationRequestDto.setStatus(OrganizationRequestStatus.PENDIENTE);
                        organizationRequestDto.setUserId(appUser.getId());
                        organizationRequestDto.setEmail(appUser.getEmail());
                        organizationRequestDto.setOrganizationName(registerUserDto.getName());
                        organizationRequestDto.setCreatedBy(appUser.getId());
                        organizationRequestDto.setRuc(registerUserDto.getDocument());
                        organizationRequestService.saveOrganizationRequest(organizationRequestDto);
                    }
                    appUserDetailsService.saveUserDetails(registerUserDto, appUser.getId());
                    AppUserDeviceDto userDeviceDto = appUserDeviceService.saveUserDevice(appUser.getId());
                    return appSessionService.initiateSession(appUser.getId(), userDeviceDto.getId());
                }else{
                    throw AppUserException.passwordDoNotMatch();
                }
            }else{
                throw AppUserException.emailRegistered(registerUserDto.getEmail());
            }
        }else{
            throw AppUserException.usernameRegistered(registerUserDto.getUsername());
        }
    }

    @Override
    public AppSessionDto userLogin(AppUserDto userDto) throws AppUserException{
        AppUser user = appUserRepository.findByEmailOrUsername(userDto.getEmail(), userDto.getUsername());
        if(user != null){
            if(EncryptUtils.matchPassword(userDto.getEncryptedPassword(), user.getEncryptedPassword())){
                AppUserDeviceDto device = new AppUserDeviceDto();
                device.setUserId(user.getId());
                device.setAsociatedAt(LocalDateTime.now());
                AppUserDeviceDto appUserDeviceDto = appUserDeviceService.saveUserDevice(device.getUserId());
                user.setLoggedIn(Boolean.TRUE);
                appUserRepository.save(user);
                return appSessionService.initiateSession(user.getId(), appUserDeviceDto.getId());
            }else{
                throw AppUserException.passwordDoNotMatch();
            }
        } else {
            if(userDto.getUsername().isEmpty() || userDto.getUsername().isBlank()) {
                throw AppUserException.notFound(userDto.getEmail());
            }else{
                throw AppUserException.notFound(userDto.getUsername());
            }
        }
    }

    @Override
    public void userLogout(AppSessionDto appSessionDto) {
        Optional<AppUser> appUser = appUserRepository.findById(appSessionDto.getUserId());
        if(appUser.isPresent()) {
            appSessionService.closeSession(appSessionDto);
            AppUser user = appUser.get();
            user.setLoggedIn(Boolean.FALSE);
            appUserRepository.save(user);
        }
    }

    @Override
    public List<AppUserDto> getUserByType(String userType) {
        return appUserMapper.mapAsList(appUserRepository.findByUserType(userType));
    }

    @Override
    public AppUserProfileDto getUserById(Long id) throws AppUserException {
        Optional<AppUser> userOptional = appUserRepository.findById(id);
        if(userOptional.isPresent()){
            AppUser appUser = userOptional.get();
            AppUserDetailsDto userDetails = appUserDetailsService.getByUserId(id);
            return prepareUserProfileDto(appUser, userDetails);
        }else{
            throw AppUserException.notFound(String.valueOf(id));
        }
    }

    @Override
    public AppUserProfileDto updateUser(AppUserProfileDto userProfileDto) throws AppUserException{
        if(userProfileDto.getUsername() != null && userProfileDto.getEmail() != null
                && userProfileDto.getName() != null && userProfileDto.getSurname() != null
                && userProfileDto.getBirthDate() != null && userProfileDto.getDocument() != null
                && userProfileDto.getDocumentType() != null && userProfileDto.getUserId() != null) {
            Optional<AppUser> userOptional = appUserRepository.findById(userProfileDto.getUserId());
            if(userOptional.isPresent()) {
                AppUser userEntity = userOptional.get();
                AppUserDetailsDto userDetails = appUserDetailsService.getByUserId(userEntity.getId());

                userEntity.setUsername(userProfileDto.getUsername());
                userEntity.setEmail(userProfileDto.getEmail());
                userEntity.setModifiedAt(LocalDateTime.now());
                userEntity.setEnabled(userProfileDto.getEnabled());
                AppUser updatedUser = appUserRepository.save(userEntity);

                AppUserDetailsDto updatedDetailsDto = appUserDetailsService.saveUserDetails(userProfileDto, userDetails.getUserId());

                return prepareUserProfileDto(updatedUser, updatedDetailsDto);
            }else{
                throw AppUserException.notFound(userProfileDto.getUsername());
            }
        }else{
            throw AppUserException.missingData();
        }
    }

    private AppUserProfileDto prepareUserProfileDto(AppUser appUser, AppUserDetailsDto userDetails) {
        AppUserProfileDto profileDto = new AppUserProfileDto();
        profileDto.setId(userDetails.getId());
        profileDto.setUserId(appUser.getId());
        profileDto.setUsername(appUser.getUsername());
        profileDto.setEmail(appUser.getEmail());
        profileDto.setUserType(appUser.getUserType());
        profileDto.setDocument(userDetails.getDocument());
        profileDto.setDocumentType(userDetails.getDocumentType());
        profileDto.setName(userDetails.getName());
        profileDto.setSurname(userDetails.getSurname());
        profileDto.setCity(userDetails.getCity());
        profileDto.setProfilePicture(userDetails.getProfilePicture());
        profileDto.setBirthDate(userDetails.getBirthDate());
        profileDto.setNotifications(userDetails.isNotifications());
        profileDto.setEnabled(appUser.isEnabled());
        return profileDto;
    }

    @Override
    public AppUserDto getUserByUsername(String username) throws AppUserException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser != null){
            return appUserMapper.mapToDto(appUser);
        } else {
            throw AppUserException.notFound(username);
        }
    }

    @Override
    public AppUserDto getUserByEmail(String email) throws AppUserException {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser != null){
            return appUserMapper.mapToDto(appUser);
        } else {
            throw AppUserException.notFound(email);
        }
    }

    //TODO make this method work for all user types. For now, we only need to approve and enable organization type users
    @Override
    public AppUserDto enableUser(Long organizationUserId, Long modifiedBy) throws AppUserException, OrganizationRequestException, EmailUtilsException {
        Long userId = organizationRequestService.getRequestById(organizationUserId).getUserId();
        Optional<AppUser> appUser = appUserRepository.findById(userId);
        if(appUser.isPresent()){
            AppUser user = appUser.get();
            user.setEnabled(Boolean.TRUE);
            user.setModifiedAt(LocalDateTime.now());
            organizationRequestService.approveRequest(organizationUserId, modifiedBy);
            return appUserMapper.mapToDto(appUserRepository.save(user));
        } else {
            throw AppUserException.notFound(userId.toString());
        }
    }
}
