package com.company.springmvcweb.data.dto;

import lombok.Data;

@Data
public class AppointmentDto {
    private int employee;
    private int service;
    private String dateAndTime;
    private String name;
    private String surname;
    private String phone;
    private String email;

}
