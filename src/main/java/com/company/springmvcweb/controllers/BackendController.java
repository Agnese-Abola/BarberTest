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
        model.addAttribute("employees", items);
        return "backend/employees";
    }

    @GetMapping("/back/services")
    public String getServices(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "backend/login";
        }
        var items = repo.getServices();

        model.addAttribute("title", "Services");
        model.addAttribute("services", items);

        return "backend/services";
    }

    @GetMapping("/back/appointments")
    public String getAppointments(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "backend/login";
        }
        var items = repo.getAppointments();

        model.addAttribute("title", "Appointments");
        model.addAttribute("appointments", items);

        return "backend/appointments";
    }

    private boolean isLoggedIn(HttpSession session) {
        var obj = (Integer) session.getAttribute("userId");
        if (obj == null) {
            obj = 0;
        }
        return obj > 0;
    }
}
