package com.javadeveloperzone.entity;

import jdk.jfr.Enabled;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue
    Long id;
    Date start;
    Date end;
    Integer dailyDistance;
    BigDecimal deposit;
    boolean agreement;
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
