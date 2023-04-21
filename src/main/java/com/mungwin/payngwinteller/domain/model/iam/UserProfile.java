package com.mungwin.payngwinteller.domain.model.iam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mungwin.payngwinteller.domain.model.BaseEntity;
import com.mungwin.payngwinteller.domain.model.iam.utils.JsonBWrapper;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@JsonIgnoreProperties({ "user", "version" })
public class UserProfile extends BaseEntity {
    private String firstName;
    private String lastName;
    private String phone;
    private String picture;
    private String address;
    private Double longitude;
    private Double latitude;
    @Formula("concat(first_name, ' ', last_name)")
    private String username;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonBWrapper<Map<String, String>> documentLinks = new JsonBWrapper<>(new HashMap<>());
    private String userId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public JsonBWrapper<Map<String, String>> getDocumentLinks() {
        return documentLinks;
    }

    public void setDocumentLinks(JsonBWrapper<Map<String, String>> documentLinks) {
        this.documentLinks = documentLinks;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
