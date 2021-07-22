package com.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements UserDetails {
    private static final long serialVersionUID = -8546972979375001850L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer uid;

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;

    private String password;
    private String auth;
    private int coins;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    public User() {
    }

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.coins = 0;
    }

    public User(String username, String email, String password, Authority authority){
        this.username = username;
        this.email = email;
        this.password = password;
        this.coins = 0;
        this.auth = authority.getAuthority();
        this.authorities.add(authority);
    }

    public void addAuth (Authority authority) {
        int access = authority.getAccessLevel();
        String temp = authority.getAuthority();
        for (Authority auth : authorities){
            if (auth.getAccessLevel() > access) {
                temp = auth.getAuthority();
            };
        }
        this.authorities.add(authority);
        this.auth = temp;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getAuth() {
        return auth;
    }

    public Integer getUid() {
        return uid;
    }
}
