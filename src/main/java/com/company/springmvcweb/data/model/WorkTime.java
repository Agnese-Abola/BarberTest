package com.company.springmvcweb.data.model;

import com.company.springmvcweb.data.helper.Helper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "work_time")
public class WorkTime {
    @Id
    @GeneratedValue
    @Column(name = "day")
    private int day;
    @Column(name = "name")
    private String name;
    @Column(name = "start_time")
    private long startTime;
    @Column(name = "end_time")
    private long endTime;


    /**
     * gets visual working hours
     *
     * @return
     */
    public String getVStartTime() {
        return Helper.adCharToString(startTime, 2, ":");
    }

    /**
     * gets visual working hours
     *
     * @return
     */
    public String getVEndTime() {
        return Helper.adCharToString(endTime, 2, ":");
    }
}

