package com.company.springmvcweb.controllers;

import com.company.springmvcweb.data.dto.AppointmentDto;
import com.company.springmvcweb.data.model.Appointment;
import com.company.springmvcweb.data.model.Service;
import com.company.springmvcweb.data.repository.BarbershopRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class FormController {

    private BarbershopRepository repo;

    public FormController() {
        repo = new BarbershopRepository();
    }

    @PostMapping("/appointment/create")
    public ModelAndView appointmentSave(@ModelAttribute("updateDto") AppointmentDto dto, Model model, HttpSession session) {
        Service service = new Service();
        Appointment appointment = new Appointment();

        appointment.setId(0);
        appointment.setEmployeeId(dto.getEmployee());
        appointment.setServiceId(dto.getService());
        var serviceTimes = service.calculateServiceTimes(dto.getDateAndTime(), dto.getService());
        var check = repo.checkEmployeeAvailability(dto.getEmployee(), serviceTimes.get(0), serviceTimes.get(1));

        if (check == true) {
            appointment.setStartTime(serviceTimes.get(0));
            appointment.setEndTime(serviceTimes.get(1));
            appointment.setClientName(dto.getName() + " " + dto.getSurname());
            appointment.setClientPhone(dto.getPhone());
            appointment.setClientEmail(dto.getEmail());

            var newId = repo.save(appointment);
            Appointment data = (Appointment) repo.getAppointment(newId);

            session.setAttribute("success", "true");
            session.setAttribute("message", "Your appointment for " + data.getService().getName() + " is booked at: " + data.getVStartTime() + ". The total price for the service is: " + data.getService().getVPrice());
        } else {
            session.setAttribute("error", "true");
            session.setAttribute("message", "Invalid time chosen");

        }

        return new ModelAndView("redirect:/appointment");
    }
}
