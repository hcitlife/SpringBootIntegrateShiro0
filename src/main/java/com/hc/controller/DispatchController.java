package com.hc.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DispatchController {

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("msg","Hello,Shiro");
        return "index";
    }

    @RequestMapping("/login")
    public String login(Model model){
        return "login";
    }

    @RequestMapping("/logout")
    public String lgout(Model model){
        SecurityUtils.getSubject().logout();
        return "index";
    }

    @RequestMapping("/unauth")
    public String unauth(Model model){
        return "unauth";
    }

}
