package com.javadeveloperzone.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue
    Long id;
    java.sql.Date beginDate;
    java.sql.Date endDate;
    Integer dailyDistance;
    BigDecimal deposit;
    boolean agreement;
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
