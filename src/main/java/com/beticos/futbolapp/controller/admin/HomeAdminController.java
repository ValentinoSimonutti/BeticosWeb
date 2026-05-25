package com.beticos.futbolapp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeAdminController {

    @GetMapping("/")
    public String redirigirHome() {
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/home")
    public String homeAdmin() {
        return "admin/home";
    }
}