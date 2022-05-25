package com.javadeveloperzone.entity;

import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
