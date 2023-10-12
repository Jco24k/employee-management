package com.proyect.employee.employee.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyect.employee.employee.entities.enums.EPermission;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
@DynamicInsert
@DynamicUpdate
public class Permission implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EPermission code;

    @Column(unique = true, length = 30,nullable = false)
    private String name;

    @Column(length = 120)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.MERGE }, mappedBy = "permissions" ,targetEntity = Role.class)
    @JsonBackReference
    private Set<Role> roles = new HashSet<>();

    public Permission(String code, String name, String description){
        this.code = EPermission.fromValue(code);
        this.name = name;
        this.description = description;
    }
}
