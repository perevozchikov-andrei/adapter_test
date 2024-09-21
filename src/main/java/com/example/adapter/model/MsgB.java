package com.example.adapter.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MsgB {
    private String txt;
    private LocalDateTime createdDt;
    private Integer currentTemp;
}
