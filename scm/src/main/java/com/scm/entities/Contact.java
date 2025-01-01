package com.scm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"userId", "phoneNumber"}),
                @UniqueConstraint(columnNames = {"userId", "email"})
        } // Ensure combination is unique
)

public class Contact {

    @Id
    private String id;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false)
    private String email;

    @Column (nullable = false)
    private String phoneNumber;


    private String address;
    private String picture;

    @Column (columnDefinition = "TEXT")
    private String description;
    private boolean favorite = false;

    private String websiteLink;
    private String linkedInLink;
//    private List<String> socialLinks = new ArrayList<>();

    private String cloudinaryImagePublicId;

    @ManyToOne
    @ToString.Exclude  // Prevent circular reference
    @JoinColumn(name = "userId")  // Explicitly naming the foreign key column as 'userId'
    @JsonIgnore
    private User user;


    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLink> links = new ArrayList<>();
}
