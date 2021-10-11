package tech.ropaki.ivoting.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthentication implements Authentication {
    private final Object principal;
    private final String details;
    private final boolean authenticated;
    private final String name;
    private final Collection<GrantedAuthority> authorities;
    private final String university;

    public CustomAuthentication(Object principal, String details, boolean authenticated, String name, Collection<GrantedAuthority> authorities, String university) {
        this.principal = principal;
        this.details = details;
        this.authenticated = authenticated;
        this.name = name;
        this.authorities = authorities;
        this.university = university;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getDetails() {
        return this.details;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getUniversity() {
        return university;
    }
}
