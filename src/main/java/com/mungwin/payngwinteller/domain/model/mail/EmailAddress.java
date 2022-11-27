package com.mungwin.payngwinteller.domain.model.mail;

public class EmailAddress {
    private String address;
    private String personal;

    public EmailAddress(String address) {
        this.address = address;
    }

    public EmailAddress(String address, String personal) {
        this(address);
        this.personal = personal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    @Override
    public String toString() {
        return "EmailAddress{" +
                "address='" + address + '\'' +
                ", personal='" + personal + '\'' +
                '}';
    }
}
