package com.company.springmvcweb.controllers;


import com.company.springmvcweb.data.repository.BarbershopRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BackendController {
    private BarbershopRepository repo;

    public BackendController() {
        repo = new BarbershopRepository();
    }

    @GetMapping("/back/employees")
    public String employee(Model model) {
        var items = repo.getEmployees();

        model.addAttribute("title", "Employees");
        model.addAttribute("employees", items);
        return "backend/employees";
    }

    @GetMapping("/back/clients")
    public String getClients(Model model) {
        var items = repo.getClients();

        model.addAttribute("title", "Clients");
        model.addAttribute("clients", items);

        return "backend/clients";
    }

    @GetMapping("/back/services")
    public String getServices(Model model) {
        var items = repo.getServices();

        model.addAttribute("title", "Services");
        model.addAttribute("services", items);

        return "backend/services";
    }

    @GetMapping("/back/appointments")
    public String getAppointments(Model model) {
        var items = repo.getAppointments();

        model.addAttribute("title", "Appointments");
        model.addAttribute("appointments", items);

        return "backend/appointments";
    }
}
