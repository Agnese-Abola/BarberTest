package com.company.springmvcweb.controllers;

import com.company.springmvcweb.data.dto.ScheduleDto;
import com.company.springmvcweb.data.dto.ServiceDto;
import com.company.springmvcweb.data.helper.Helper;
import com.company.springmvcweb.data.model.Schedule;
import com.company.springmvcweb.data.model.Service;
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

    private boolean isLoggedIn(HttpSession session) {
        var obj = (Integer) session.getAttribute("userId");
        if (obj == null) {
            obj = 0;
        }
        return obj > 0;
    }

    @GetMapping("/back/employees")
    public String getEmployee(Model model, HttpSession session) {
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

    @PostMapping("/back/schedules/save")
    public ModelAndView saveSchedule(@ModelAttribute("saveScheduleDto") ScheduleDto dto, HttpSession session) {
        if (!isLoggedIn(session)) {
            return new ModelAndView("redirect:/back");
        }
        Schedule schedule = new Schedule();
        schedule.setEmployeeId((Integer) session.getAttribute("userId"));

        schedule.setDayId(dto.getDay());

        var startTime = dto.getStartTime().replace(":", "");
        var endTime = dto.getEndTime().replace(":", "");

        schedule.setStartTime(Integer.parseInt(startTime));
        schedule.setEndTime(Integer.parseInt(endTime));

        repo.save(schedule);

        return new ModelAndView("redirect:/back/schedules");
    }

    @GetMapping("/back/schedules/new")
    public String newSchedule(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "backend/login";
        }
        model.addAttribute("employeeName", repo.getEmployeeName((Integer) session.getAttribute("userId")));
        return "backend/schedules_new";
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
        model.addAttribute("employeeName", repo.getEmployeeName((Integer) session.getAttribute("userId")));

        return "backend/schedules_edit";
    }

    @PostMapping("/back/schedules/update")
    public ModelAndView updateSchedule(@ModelAttribute("updateScheduleDto") ScheduleDto dto, HttpSession session) {
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

        schedule.setStartTime(Integer.parseInt(sTime));
        schedule.setEndTime(Integer.parseInt(eTime));
        schedule.setId(dto.getId());
        schedule.setEmployeeId(userId);
        repo.update(schedule);

        return new ModelAndView("redirect:/back/schedules");
    }

    @GetMapping("/back/schedules/delete/{id}")
    public ModelAndView deleteSchedule(@PathVariable int id, HttpSession session) {
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

    @GetMapping("/back/services/new")
    public String newServices(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "backend/login";
        }
        model.addAttribute("employeeName", repo.getEmployeeName((Integer) session.getAttribute("userId")));
        return "backend/services_new";
    }

    @PostMapping("/back/services/save")
    public ModelAndView saveService(@ModelAttribute("saveServiceDto") ServiceDto dto, HttpSession session) {
        if (!isLoggedIn(session)) {
            return new ModelAndView("redirect:/back");
        }
        Service service = new Service();

        service.setName(dto.getName());
        service.setDuration(dto.getDuration());
        service.setPrice(Helper.getPriceInEuro(dto.getPrice()));

        repo.save(service);

        return new ModelAndView("redirect:/back/services");
    }

    @GetMapping("/back/services/edit/{id}")
    public String editService(@PathVariable int id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "backend/login";
        }

        Service service = repo.getService(id);

        if (service == null) {
            throw new IllegalArgumentException("system error");
        }
        model.addAttribute("oldId", service.getId());
        model.addAttribute("oldName", service.getName());
        model.addAttribute("oldDuration", service.getDuration());
        model.addAttribute("oldPrice", service.getVPrice());
        model.addAttribute("employeeName", repo.getEmployeeName((Integer) session.getAttribute("userId")));


        return "backend/services_edit";
    }

    @PostMapping("/back/services/update")
    public ModelAndView updateService(@ModelAttribute("updateServiceDto") ServiceDto dto, HttpSession session) {
        if (!isLoggedIn(session)) {
            return new ModelAndView("redirect:/back");
        }
        Service service = repo.getService(dto.getId());

        if (service == null) {
            throw new IllegalArgumentException("system error");
        }
        service.setId(dto.getId());
        service.setName(dto.getName());
        service.setDuration(dto.getDuration());
        service.setPrice(Helper.getPriceInEuro(dto.getPrice()));

        repo.update(service);

        return new ModelAndView("redirect:/back/services");
    }

    @GetMapping("/back/services/delete/{id}")
    public ModelAndView deleteService(@PathVariable int id, HttpSession session) {
        if (!isLoggedIn(session)) {
            return new ModelAndView("redirect:/back");
        }
        Service service = repo.getService(id);

        if (service == null) {
            throw new IllegalArgumentException("system error");
        }
        repo.delete(service);

        return new ModelAndView("redirect:/back/services");
    }
}
