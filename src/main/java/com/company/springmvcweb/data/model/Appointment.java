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
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "date_created")
    private int dateCreated;
    @Column(name = "client_id")
    private int clientId;
    @Column(name = "employee_id")
    private int employeeId;
    @Column(name = "client_name")
    private String clientName;
    @Column(name = "client_phone")
    private String clientPhone;
    @Column(name = "client_email")
    private String clientEmail;
    @Column(name = "start_time")
    private long startTime;
    @Column(name = "end_time")
    private long endTime;
    @Column(name = "cancelled")
    private boolean cancelled;
    @Column(name = "cancellation_reason")
    private String cancellationReason;
    @Column(name = "service_id")
    private int serviceId;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", insertable = false, updatable = false)
    private Service service;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", insertable = false, updatable = false)
    private Employee employee;


    public String getClientFullContacts() {
        String r = "";
        r += "<a href=\"tel:"+clientPhone+"\">"+clientPhone+"</a><br/>";
        r += "<a href=\"mailto:"+clientEmail+"\">"+clientEmail+"</a>";
        return r;
    }

    public String getVStartTime() {
        return Helper.convertDate(startTime);
    }

    public String getVEndTime() {
        return Helper.convertDate(endTime);
    }
}

