package com.company.springmvcweb.data.dto;

import lombok.Data;

@Data
public class ScheduleDto {
    private int id;
    private int employeeId;
    private int day;
    private String startTime;
    private String endTime;
}
