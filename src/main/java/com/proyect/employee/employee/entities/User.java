package com.proyect.employee.employee.entities;

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
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 30,nullable = false)
    private String username;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(name = "last_name",length = 30)
    private String lastName;

    @Column(length = 60,nullable = false)
    private String password;

    @Column(name = "is_active", columnDefinition = "bit(1) default 1")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE} ,targetEntity = Role.class)
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    @JsonManagedReference
    private Set<Role> roles = new HashSet<>();

    public User(String username, String password,String name,String lastName,Set<Role> roles){
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.roles = roles;
    }
    @PrePersist()
    private void insert(){
        isActive = true;
    }
}
