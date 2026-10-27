package com.example.demo.dto;

public class AuthResponse {
    public String access_token;
    public String refresh_token;

    public AuthResponse(String access, String refresh) {
        this.access_token = access;
        this.refresh_token = refresh;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
