package com.javadeveloperzone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue
    public Long id;
    public String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    public List<User> users;



    public Role() {

    }

    @Override
    public String getAuthority() {
        return null;
    }
}