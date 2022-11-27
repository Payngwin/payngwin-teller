package com.mungwin.payngwinteller.domain.model.iam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mungwin.payngwinteller.domain.model.BaseEntity;
import com.mungwin.payngwinteller.domain.repository.CSVListConverter;
import com.mungwin.payngwinteller.domain.repository.CSVMapConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.List;
import java.util.Map;

@Entity
@JsonIgnoreProperties({ "users", "version" })
public class Role extends BaseEntity {
    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    private String name;
    @Convert(converter = CSVListConverter.class)
    private List<String> authorities;
    @Convert(converter = CSVMapConverter.class)
    private Map<Long, List<String>> accountAuthorities;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public Map<Long, List<String>> getAccountAuthorities() {
        return accountAuthorities;
    }

    public void setAccountAuthorities(Map<Long, List<String>> accountAuthorities) {
        this.accountAuthorities = accountAuthorities;
    }
}
