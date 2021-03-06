package com.github.firulapp.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;

public class AppUserDetailsDto {

    private Long id;
    private Long userId;
    private String document;
    private String documentType;
    private String name;
    private String surname;
    private Long city;
    private byte[] profilePicture;
    private LocalDate birthDate;
    private Boolean notifications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userId", userId)
                .append("document", document)
                .append("documentType", documentType)
                .append("name", name)
                .append("surname", surname)
                .append("city", city)
                .append("profilePicture", profilePicture)
                .append("birthDate", birthDate)
                .append("notifications", notifications)
                .toString();
    }
}
