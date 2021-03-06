package com.github.firulapp.config;

import com.github.firulapp.constants.ApiPaths;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(ApiPaths.BASE_URL).permitAll()
                .antMatchers(ApiPaths.USER_ENDPOINTS_URL+ApiPaths.REGISTER_URL).permitAll()
                .antMatchers(ApiPaths.USER_ENDPOINTS_URL+ApiPaths.LOGIN_URL).permitAll()
                .antMatchers(ApiPaths.USER_ENDPOINTS_URL+ApiPaths.LOGOUT_URL).permitAll()
                .antMatchers(ApiPaths.USER_ENDPOINTS_URL+ApiPaths.GET_USER_BY_TYPE).permitAll()
                .antMatchers(ApiPaths.USER_ENDPOINTS_URL+ApiPaths.GET_USER_PROFILE_BY_ID).permitAll()
                .antMatchers(ApiPaths.USER_ENDPOINTS_URL+ApiPaths.UPDATE).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.SPECIES_BY_ID).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.SPECIES_ENDPOINTS).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.BREED_BY_ID).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.BREED_BY_SPECIES_ID).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.BREED_ENDPOINTS).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.CONDUCT_RULE_BY_ID).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.CONDUCT_RULE_ENDPOINTS).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.HELP_PAGE_BY_ID).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.HELP_PAGE_ENDPOINTS).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.SERVICE_TYPE_BY_ID).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.SERVICE_TYPE_ENDPOINTS).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.PET_CARE_BY_ID).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.PET_CARE_ENDPOINTS).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.PET_CARE_BY_BREED_AND_SPECIES).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.CITY_ENDPOINTS).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.CITY_BY_ID).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.CITY_BY_COUNTRY).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.CITY_BY_DISTRICT).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.GET_PET_BY_ID).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.GET_PET_BY_USER_AND_SPECIES).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.GET_PET_BY_USER_ID).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.SAVE).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.DELETE).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.GET_PET_BY_STATUS).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.GET_PET_MEDICAL_RECORD_BY_PET_ID).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.GET_PET_MEDICAL_RECORD_BY_PET_ID_AND_DATE).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.GET_PET_MEDICAL_RECORD_BY_ID).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.SAVE_OR_UPDATE_PET_MEDICAL_RECORD).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.DELETE_PET_MEDICAL_RECORD).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.GET_PET_VACCINATION_RECORD_BY_PET_ID).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.GET_PET_VACCINATION_RECORD_BY_ID).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.SAVE_OR_UPDATE_PET_VACCINATION_RECORD).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.DELETE_PET_VACCINATION_RECORD).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.PET_ACTIVITY_ENDPOINT_URL).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.PET_ACTIVITY_BY_ID).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.GET_PET_ACTIVITY_BY_PET_ID).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.SAVE_OR_UPDATE_PET_ACTIVITY).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.REQUEST_PET_ADOPTION).permitAll()
                .antMatchers(ApiPaths.USER_ENDPOINTS_URL+ApiPaths.USER_AGENDA_ENDPOINT_URL).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.REQUEST_FOSTER_PET).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.REPORT_URL).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.REPORT_BY_LOCATION).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.REPORT_BY_DATES).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.REPORT_BY_ID).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.REPORT_FOUND_PET).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.REPORT_LOST_PET).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.REPORT_CLOSE).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.REPORT_OPEN).permitAll()
                .antMatchers(ApiPaths.PET_ENDPOINTS_URL+ApiPaths.PET_TRANSFER_PROFILE).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.ORGANIZATION_REQUEST_ENDPOINTS).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.ORGANIZATION_APPROVE).permitAll()
                .antMatchers(ApiPaths.PARAM_ENDPOINTS_URL+ApiPaths.ORGANIZATION_REJECT).permitAll()
                .antMatchers(ApiPaths.USER_ENDPOINTS_URL+ApiPaths.ORGANIZATION_ENDPOINTS).permitAll()
                .antMatchers(ApiPaths.USER_ENDPOINTS_URL+ApiPaths.ORGANIZATION_BY_USER_ID).permitAll()
                .antMatchers(ApiPaths.USER_ENDPOINTS_URL+ApiPaths.ORGANIZATION_UPDATE).permitAll()
                .antMatchers(ApiPaths.USER_ENDPOINTS_URL+ApiPaths.ORGANIZATION_REQUEST_ENDPOINTS).permitAll()
                .antMatchers(ApiPaths.SERVICE_URL).permitAll()
                .antMatchers(ApiPaths.SERVICE_URL+ApiPaths.SERVICE_SAVE).permitAll()
                .antMatchers(ApiPaths.SERVICE_URL+ApiPaths.ID).permitAll()
                .antMatchers(ApiPaths.SERVICE_URL+ApiPaths.SERVICE_BY_TYPE).permitAll()
                .antMatchers(ApiPaths.SERVICE_URL+ApiPaths.SERVICE_BY_USER).permitAll()
                .antMatchers(ApiPaths.SERVICE_URL+ApiPaths.SERVICE_ALL).permitAll()
                .antMatchers(ApiPaths.SERVICE_URL+ApiPaths.SERVICE_FILTER).permitAll()
                .antMatchers(ApiPaths.SERVICE_URL+ApiPaths.SERVICE_APPOINTMENT_ENDPOINTS).permitAll()
                .antMatchers(ApiPaths.SERVICE_URL+ApiPaths.SERVICE_APPOINTMENT_BY_SERVICE_USER_PET).permitAll()
                .antMatchers(ApiPaths.SERVICE_URL+ApiPaths.SERVICE_APPOINTMENT_CANCEL).permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();

        http.formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
    }
}