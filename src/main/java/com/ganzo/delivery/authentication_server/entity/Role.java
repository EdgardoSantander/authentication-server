package com.ganzo.delivery.authentication_server.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String title;
    private String description;
    @OneToMany(mappedBy = "role")
    private Set<Enrollment> enrollments = new HashSet<>();
}
