package com.mungwin.payngwinteller.domain.model.iam;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Authority {
    @Id
    private String id;

    public Authority(String id) {
        this.id = id;
    }

    public Authority() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
