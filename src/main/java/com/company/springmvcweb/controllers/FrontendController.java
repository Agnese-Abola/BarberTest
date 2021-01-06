package com.company.springmvcweb.controllers;

import com.company.springmvcweb.data.repository.BarbershopRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;

@Controller
public class FrontendController {

    private BarbershopRepository repo;

    public FrontendController() {
        repo = new BarbershopRepository();
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        var workTime = repo.getWorkTime();

        model.addAttribute("title", "Barbershop");
        model.addAttribute("worktime", workTime);

        return "frontend/barbershop";
    }

    @GetMapping("/appointment")
    public String appointment(Model model, HttpSession session) {
        model.addAttribute("title", "Appointment");
        model.addAttribute("employeesList", repo.getEmployees());
        model.addAttribute("servicesList", repo.getServices());
        model.addAttribute("workTimeList", repo.getWorkTime());

        var errorItem = (String) session.getAttribute("error");
        session.removeAttribute("error");
        model.addAttribute("error", errorItem == "true" ? true : false);

        var successItem = (String) session.getAttribute("success");
        session.removeAttribute("success");
        model.addAttribute("success", successItem == "true" ? true : false);

        var messageItem = (String) session.getAttribute("message");
        session.removeAttribute("message");
        model.addAttribute("message", messageItem);

        return "frontend/appointment";
    }
}
