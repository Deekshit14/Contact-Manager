package com.scm.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Contact {

    @Id
    private String id;

    @Column (nullable = false)
    private String name;

    private String email;

    @Column (unique = true, nullable = false)
    private String phoneNumber;
    private String address;
    private String picture;

    @Column (columnDefinition = "TEXT")
    private String description;
    private boolean favorite = false;

    private String websiteLink;
    private String linkedInLink;
//    private List<String> socialLinks = new ArrayList<>();

    @ManyToOne
    private User user;


    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLink> links = new ArrayList<>();
}
