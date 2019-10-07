package ru.itis.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TokenAuthentication implements Authentication {

    private UsersDetailsImpl userDetails;
    private String token;

    private boolean isAuthenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userDetails != null) {
            return userDetails.getAuthorities();
        } else return null;
    }

    @Override
    public Object getCredentials() {
        return userDetails.getPassword();
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        if (userDetails != null) {
            return userDetails.getUser();
        } else return null;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return token;
    }

    public void setUserDetails(UsersDetailsImpl userDetails) {
        this.userDetails = userDetails;
    }

    public void setToken(String token) {
        this.token = token;
    }
}