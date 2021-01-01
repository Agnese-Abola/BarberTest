package com.company.springmvcweb.controllers;

import com.company.springmvcweb.data.dto.AppointmentDto;
import com.company.springmvcweb.data.dto.LoginDto;
import com.company.springmvcweb.data.model.Appointment;
import com.company.springmvcweb.data.model.Employee;
import com.company.springmvcweb.data.model.Service;
import com.company.springmvcweb.data.repository.BarbershopRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private BarbershopRepository repo;

    public LoginController() {
        repo = new BarbershopRepository();
    }

    @GetMapping("/back")
    public String login(Model model, HttpSession session) {
        var errorItem = (String) session.getAttribute("error");
        session.removeAttribute("error");
        model.addAttribute("error", errorItem == "true" ? true : false);

        var successItem = (String) session.getAttribute("success");
        session.removeAttribute("success");
        model.addAttribute("success", successItem == "true" ? true : false);

        var messageItem = (String) session.getAttribute("message");
        session.removeAttribute("message");
        model.addAttribute("message", messageItem);

        return "backend/login";
    }

    @GetMapping("/back/logout")
    public ModelAndView logout(Model model, HttpSession session) {
        session.removeAttribute("userId");
        session.setAttribute("success", "true");
        session.setAttribute("message", "User successfully logged out");

        return new ModelAndView("redirect:/back");
    }

    @PostMapping("/back/login")
    public ModelAndView login(@ModelAttribute("loginDto") LoginDto dto, Model model, HttpSession session) {
        if (dto.getUsername().isEmpty() || dto.getPassword().isEmpty()) {
            session.setAttribute("error", "true");
            session.setAttribute("message", "Please provide username and password");
            return new ModelAndView("redirect:/back");
        }

        var userId = repo.checkUserAndPassword(dto.getUsername(), dto.getPassword());
        if (userId > 0) {
            session.setAttribute("userId", userId);
            return new ModelAndView("redirect:/back/appointments");
        } else {
            session.setAttribute("error", "true");
            session.setAttribute("message", "Please provide correct username and password");
        }
        return new ModelAndView("redirect:/back");
    }
}
