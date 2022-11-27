package com.mungwin.payngwinteller.domain.request.iam;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignUpUserRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "(.*[^a-zA-Z0-9]+.*)+")
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 6)
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
}
