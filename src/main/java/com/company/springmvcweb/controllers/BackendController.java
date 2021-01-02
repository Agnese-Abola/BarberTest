package com.company.springmvcweb.controllers;


import com.company.springmvcweb.data.repository.BarbershopRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class BackendController {
    private BarbershopRepository repo;

    public BackendController() {
        repo = new BarbershopRepository();
    }

    @GetMapping("/back/employees")
    public String employee(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "backend/login";
        }
        var items = repo.getEmployees();

        model.addAttribute("title", "Employees");
        model.addAttribute("dashboardlink", "/back/appointments");
        model.addAttribute("employees", items);
        model.addAttribute("employeeName", repo.getEmployeeName((Integer) session.getAttribute("userId")));

        return "backend/employees";
    }

    @GetMapping("/back/services")
    public String getServices(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "backend/login";
        }
        var items = repo.getServices();

        model.addAttribute("title", "Services");
        model.addAttribute("dashboardlink", "/back/appointments");
        model.addAttribute("services", items);
        model.addAttribute("employeeName", repo.getEmployeeName((Integer) session.getAttribute("userId")));


        return "backend/services";
    }

    @GetMapping("/back/appointments")
    public String getAppointments(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "backend/login";
        }
        var items = repo.getAppointments();

        model.addAttribute("title", "Appointments");
        model.addAttribute("dashboardlink", "/back/appointments");
        model.addAttribute("appointments", items);
        model.addAttribute("employeeName", repo.getEmployeeName((Integer) session.getAttribute("userId")));

        return "backend/appointments";
    }

    @GetMapping("/back/schedules")
    public String getSchedule(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "backend/login";
        }
        var items = repo.getSchedule();

        model.addAttribute("title", "Schedules");
        model.addAttribute("dashboardlink", "/back/appointments");
        model.addAttribute("schedules", items);
        model.addAttribute("employeeName", repo.getEmployeeName((Integer) session.getAttribute("userId")));

        return "backend/schedules";
    }

    private boolean isLoggedIn(HttpSession session) {
        var obj = (Integer) session.getAttribute("userId");
        if (obj == null) {
            obj = 0;
        }
        return obj > 0;
    }
}
