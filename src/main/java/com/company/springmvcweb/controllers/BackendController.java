package com.company.springmvcweb.controllers;

import com.company.springmvcweb.data.dto.ScheduleDto;
import com.company.springmvcweb.data.model.Schedule;
import com.company.springmvcweb.data.repository.BarbershopRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
        int userId = (Integer) session.getAttribute("userId");
        var items = repo.getSchedules();

        model.addAttribute("title", "Schedules");
        model.addAttribute("dashboardlink", "/back/appointments");
        model.addAttribute("schedules", items);
        model.addAttribute("employeeName", repo.getEmployeeName((Integer) session.getAttribute("userId")));
        model.addAttribute("userId", userId);
        return "backend/schedules";
    }

    private boolean isLoggedIn(HttpSession session) {
        var obj = (Integer) session.getAttribute("userId");
        if (obj == null) {
            obj = 0;
        }
        return obj > 0;
    }

    @PostMapping("/back/schedules/save")
    public ModelAndView saveSchedule(@ModelAttribute("saveScheduleDto") ScheduleDto dto, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return new ModelAndView("redirect:/back");
        }
        Schedule schedule = new Schedule();
        schedule.setEmployeeId((Integer) session.getAttribute("userId"));

        schedule.setDayId(dto.getDay());

        var sTime = dto.getStartTime().replace(":", "");
        var eTime = dto.getEndTime().replace(":", "");

        schedule.setStartTime(Integer.valueOf(sTime));
        schedule.setEndTime(Integer.valueOf(eTime));

        repo.save(schedule);

        return new ModelAndView("redirect:/back/schedules");
    }

    @GetMapping("/back/schedules/edit/{id}")
    public String editSchedule(@PathVariable int id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "backend/login";
        }

        Schedule schedule = repo.getSchedule(id);
        int userId = (Integer) session.getAttribute("userId");

        if (schedule == null || userId != schedule.getEmployeeId()) {
            throw new IllegalArgumentException("system error");
        }
        model.addAttribute("oldId", schedule.getId());
        model.addAttribute("oldDayId", schedule.getDayId());
        model.addAttribute("oldStartTime", schedule.getVStartTime());
        model.addAttribute("oldEndTime", schedule.getVEndTime());

        return "backend/schedules_edit";
    }

    @GetMapping("/back/schedules/new")
    public String newSchedule(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "backend/login";
        }
        return "backend/schedules_new";
    }

    @PostMapping("/back/schedules/update")
    public ModelAndView updateSchedule(@ModelAttribute("updateScheduleDto") ScheduleDto dto, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return new ModelAndView("redirect:/back");
        }
        Schedule schedule = repo.getSchedule(dto.getId());
        int userId = (Integer) session.getAttribute("userId");

        if (schedule == null || userId != schedule.getEmployeeId()) {
            throw new IllegalArgumentException("system error");
        }

        schedule.setDayId(dto.getDay());

        var sTime = dto.getStartTime().replace(":", "");
        var eTime = dto.getEndTime().replace(":", "");

        schedule.setStartTime(Integer.valueOf(sTime));
        schedule.setEndTime(Integer.valueOf(eTime));
        schedule.setId(dto.getId());
        schedule.setEmployeeId(userId);
        repo.update(schedule);

        return new ModelAndView("redirect:/back/schedules");
    }

    @GetMapping("/back/schedules/delete/{id}")
    public ModelAndView deleteSchedule(@PathVariable int id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return new ModelAndView("redirect:/back");
        }
        Schedule schedule = repo.getSchedule(id);
        int userId = (Integer) session.getAttribute("userId");

        if (schedule == null || userId != schedule.getEmployeeId()) {
            throw new IllegalArgumentException("system error");
        }
        repo.delete(schedule);

        return new ModelAndView("redirect:/back/schedules");
    }
}
