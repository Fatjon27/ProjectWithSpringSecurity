package com.example.demo.controllers;

import com.example.demo.dto.UserDto;
import com.example.demo.entities.Repair;
import com.example.demo.entities.Report;
import com.example.demo.entities.User;
import com.example.demo.services.RepairService;
import com.example.demo.services.ReportService;
import com.example.demo.services.RoleService;
import com.example.demo.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RepairService repairService;
    @Autowired
    private ReportService reportService;


    // handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }
        if (roleService.findByName("ROLE_TECHNICIAN").getUsers().size() < 1) {
            userService.saveUserWithTechnicianRole(userDto);
            return "redirect:/register?success";
        } else {
            userService.saveUserWithUserRole(userDto);
            return "redirect:/register?success";
        }
    }

    // handler method to handle list of users
    @GetMapping("/users")
    public String users(Model model, Principal principal){
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        model.addAttribute("user",user);
        model.addAttribute("repairs",user.getRepairs());
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("reports",user.getCustomerReports());

        if(userService.checkIfTechnician(user)){
            return "redirect:/technician/dashboard";
        }
        if(userService.checkIfAcceptance(user)){
            return "redirect:/acceptance/dashboard";
        }
        return "users";
    }
    // handler method to handle login request
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/acceptance/dashboard")
    public String acceptanceDashboard(Model model,Principal principal){
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        model.addAttribute("user",user);
        model.addAttribute("repairs",repairService.findAll());
        model.addAttribute("reports",reportService.findAll());

        return "acceptanceDashboard";
    }

    @GetMapping("/technician/dashboard")
    public String technicianDashboard(Model model,Principal principal){
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        model.addAttribute("user",user);
        model.addAttribute("reports",user.getTechnicianReports());

        return "technicianDashboard";
    }

    @GetMapping("/user/repair")
    public String newRepair(Model model,Principal principal){
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        Repair repair = new Repair();
        model.addAttribute("repair",repair);
        return "newRepair";
    }

    @PostMapping("/user/repair")
    public String createRepair(@Valid @ModelAttribute("repair")Repair repair,BindingResult result,Model model,Principal principal){
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        repair.setCustomer(user);
        repair.setCustomerName(user.getName());
//        if (result.hasErrors()){
//            model.addAttribute("users",userService.findAllUsers());
//            model.addAttribute("repair", new Repair());
//            return "newRepair";
//        }
//        else {
            repairService.createRepair(repair);
            return "redirect:/users";
//        }
    }

    @GetMapping("/acceptance/{id}")
    public String newReport(@PathVariable("id") Long id, Model model, @ModelAttribute("report")Report report,Principal principal){
        Repair repair = repairService.findById(id);
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        model.addAttribute("user",user);
        model.addAttribute("repair",repair);
        model.addAttribute("report",new Report());
        return "newReport";
    }
    @PostMapping("/acceptance/{id}")
    public String createReport(@PathVariable("id") Long id,@Valid @ModelAttribute("report") Report report,BindingResult result,Principal principal,Model model){
        Repair repair = repairService.findById(id);
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        model.addAttribute("user",user);
        model.addAttribute("repair",repair);

        if(result.hasErrors()){
            model.addAttribute("report",new Report());
            return "newReport";
        }
        else {
            report.setRepair(repair);
            report.setCustomer(repair.getCustomer());
            report.setAcceptance(userService.findById(1L));
            report.setTechnician(userService.findById(2L));
            reportService.createReport(report);
            return "redirect:/acceptance/dashboard";
        }
    }

    @GetMapping("/report/details/{id}")
    public String reportDetails(@PathVariable("id") Long id,Model model,Principal principal){
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        model.addAttribute("user",user);
        if (email == null) {
            return "redirect:/";
        }
        Report report = reportService.findById(id);
        model.addAttribute("report",report);
        return "reportDetails";
    }

    @GetMapping("/technician/report/{id}/{repairId}")
    public String editReport(@PathVariable("id") Long id,Model model,Principal principal){
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        model.addAttribute("user",user);
        if (email == null) {
            return "redirect:/";
        }
        Report report = reportService.findById(id);
        model.addAttribute("report",report);
        return "updateReport";
    }

    @PutMapping("/technician/report/{id}/{repairId}")
    public String updateReport(@PathVariable("id") Long id,Model model,@Valid @ModelAttribute("report") Report report,BindingResult result,@PathVariable("repairId") Long repairId,Principal principal){
        String email = principal.getName();
        User user = userService.findUserByEmail(email);
        model.addAttribute("user",user);
        model.addAttribute("report",report);
        if (email == null) {
        return "redirect:/";
        }
        if(result.hasErrors()){
            return "updateReport";
        }
        else {
            Repair repair = repairService.findById(repairId);
            report.setRepair(repair);
            report.setAcceptance(userService.findById(1L));
            report.setCustomer(repair.getCustomer());
            report.setTechnician(userService.findById(2L));
            reportService.updateReport(report);
            return "redirect:/technician/dashboard";
        }
    }
}
