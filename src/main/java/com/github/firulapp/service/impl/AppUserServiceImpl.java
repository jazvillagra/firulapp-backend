package com.github.firulapp.service.impl;

import com.github.firulapp.domain.AppUser;
import com.github.firulapp.dto.AppSessionDto;
import com.github.firulapp.dto.AppUserDeviceDto;
import com.github.firulapp.dto.AppUserDto;
import com.github.firulapp.dto.AppUserProfile;
import com.github.firulapp.exceptions.AppUserException;
import com.github.firulapp.mapper.impl.AppUserMapper;
import com.github.firulapp.repository.AppUserRepository;
import com.github.firulapp.service.AppSessionService;
import com.github.firulapp.service.AppUserDetailsService;
import com.github.firulapp.service.AppUserDeviceService;
import com.github.firulapp.service.AppUserService;
import com.github.firulapp.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

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

    @Override
    public AppUserDto registerUser(AppUserProfile registerUserDto) throws AppUserException{
        if(appUserRepository.findByUsername(registerUserDto.getUsername()) == null){
            if(appUserRepository.findByEmail(registerUserDto.getEmail()) == null) {
                if(registerUserDto.getEncryptedPassword().equals(registerUserDto.getConfirmPassword())) {
                    AppUser userEntity = new AppUser();
                    userEntity.setEmail(registerUserDto.getEmail());
                    userEntity.setUsername(registerUserDto.getUsername());
                    userEntity.setEncryptedPassword(EncryptUtils.hashPassword(registerUserDto.getEncryptedPassword()));
                    //TODO email confirmation, in the meantime all users registered are enabled
                    userEntity.setEnabled(true);
                    userEntity.setLoggedIn(Boolean.TRUE);
                    userEntity.setUserType(registerUserDto.getUserType());
                    userEntity.setCreatedAt(LocalDateTime.now());
                    AppUser appUser = appUserRepository.save(userEntity);

                    appUserDetailsService.saveUserDetails(registerUserDto, appUser.getId());
                    AppUserDeviceDto userDeviceDto = appUserDeviceService.saveUserDevice(appUser.getId());
                    appSessionService.initiateSession(appUser.getId(), userDeviceDto.getId());

                    return appUserMapper.mapToDto(appUser);
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
        }else {
            throw AppUserException.notFound(userDto.getUsername().isEmpty() ? userDto.getUsername() : userDto.getEmail());
        }
    }

    @Override
    public void userLogout(AppSessionDto appSessionDto) throws AppUserException {
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
}
