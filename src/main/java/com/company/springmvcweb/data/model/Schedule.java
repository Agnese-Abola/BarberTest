package com.company.springmvcweb.data.model;

import com.company.springmvcweb.data.helper.Helper;
import com.company.springmvcweb.data.repository.BarbershopRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.DayOfWeek;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private Employee employee;

    public String getDayOfWeek() {
        return DayOfWeek.of(dayId).toString();
    }
    public String getVStartTime() {
        return Helper.adCharToString(startTime, 2, ":");
    }
    public String getVEndTime() {
        return Helper.adCharToString(endTime, 2, ":");
    }

    public String getEmployeeName() {
        return employee.getName();
    }

}

