package com.javadeveloperzone.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue
    Long id;
    @Lob
    byte[] content;
    String name;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

}
