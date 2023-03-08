package com.rewqty.fileslab.models;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;

@Entity
@Table(name="Users",
        uniqueConstraints={@UniqueConstraint(columnNames={"ID"})})
public class UserProfileModel implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(name = "login", nullable = false, unique = true, updatable = false)
    private String login;

    @Column(name = "pass", nullable = false, updatable = false)
    private String pass;

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    public UserProfileModel(){

    }

    public UserProfileModel(Long id, String login, String email, String pass) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.pass = pass;
    }

    public Long getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getPass() {
        return pass;
    }
    public String getEmail() {
        return email;
    }
}
