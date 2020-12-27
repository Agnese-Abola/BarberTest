package com.company.springmvcweb.data.model;

import com.company.springmvcweb.data.helper.Helper;
import com.company.springmvcweb.data.repository.BarbershopRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.servlet.http.HttpSession;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "employee_id")
    private int employeeId;
    @Column(name = "start_time")
    private int startTime;
    @Column(name = "end_time")
    private int endTime;
    @Column(name = "day_id")
    private int dayId;

}

