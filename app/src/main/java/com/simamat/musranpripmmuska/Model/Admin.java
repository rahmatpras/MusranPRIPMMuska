package com.simamat.musranpripmmuska.Model;

public class Admin {
    private Long password;

    public Admin() {
    }

    public Admin(Long password) {
        this.password = password;
    }

    public Long getPassword() {
        return password;
    }

    public void setPassword(Long password) {
        this.password = password;
    }
}
