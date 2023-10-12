package com.proyect.employee.employee.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 30,nullable = false)
    private String name;

    @Column(name = "is_active", columnDefinition = "bit(1) default 1")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE} ,targetEntity = Permission.class)
    @JoinTable(name="roles_permissions",
            joinColumns = @JoinColumn(name="role_id"),
            inverseJoinColumns = @JoinColumn(name="permit_id")
    )
    @JsonManagedReference
    private Set<Permission> permissions = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.MERGE }, mappedBy = "roles" ,targetEntity = User.class)
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    public Role(String name, Set<Permission> permissions){
        this.name = name;
        this.permissions = permissions;
    }

    @PrePersist()
    private void insert(){
        isActive = true;
    }
}
