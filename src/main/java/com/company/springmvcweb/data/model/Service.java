package com.company.springmvcweb.data.model;

import com.company.springmvcweb.data.helper.Helper;
import com.company.springmvcweb.data.repository.BarbershopRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "duration")
    private int duration;
    @Column(name = "price")
    /**
     * price in cents
     */
    private int price;

    /**
     * format time into visual hours and minutes
     *
     * @return
     */
    public String getVDuration() {
        int m = duration % 60;
        int h = (duration - m) / 60;

        return h + "h " + (m < 10 ? "0" : "") + m + "m";
    }

    /**
     * format visual price in euros and cents
     *
     * @return
     */
    public String getVPrice() {
        return Helper.adCharToString(price, 2, ".");
    }

    /**
     * calculates start and end time
     *
     * @param dateTime
     * @param serviceId
     * @return
     */
    public List<Long> calculateServiceTimes(String dateTime, int serviceId) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        long startTime = 0;

        //parse to unix time stamp
        try {
            Date date = dateFormat.parse(dateTime);
            startTime = (long) date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //get service duration time
        BarbershopRepository repo = new BarbershopRepository();
        long serviceDuration = repo.getServiceTime(serviceId);

        //calculates service end time
        long endTime = startTime + (serviceDuration * 60);

        return List.of(startTime, endTime);

    }
}
