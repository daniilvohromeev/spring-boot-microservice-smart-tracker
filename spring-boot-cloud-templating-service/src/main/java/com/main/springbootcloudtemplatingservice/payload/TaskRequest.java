package com.main.springbootcloudtemplatingservice.payload;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaskRequest {
    String beginDate;
    String endDate;
    String dailyDistance;
    String deposit;
    boolean agreement;
    String username;
}
