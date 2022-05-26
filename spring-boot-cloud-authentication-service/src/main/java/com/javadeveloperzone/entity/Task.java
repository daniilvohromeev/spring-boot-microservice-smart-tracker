package com.javadeveloperzone.entity;

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
@Table(name="smart_task")
public class Task {
    @Id
    @GeneratedValue
    Long id;
    Date startDate;
    Date endDate;
    Integer dailyDistance;
    BigDecimal deposit;
    boolean agreement;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
