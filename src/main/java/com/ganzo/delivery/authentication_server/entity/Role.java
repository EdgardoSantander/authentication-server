package com.ganzo.delivery.authentication_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String title;
    private String description;
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<Enrollment> enrollments = new HashSet<>();
}
