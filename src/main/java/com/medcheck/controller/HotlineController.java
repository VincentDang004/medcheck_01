package com.medcheck.controller;

import com.medcheck.service.HotlineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HotlineController {

    private final HotlineService hotlineService;

    public HotlineController(HotlineService hotlineService) {
        this.hotlineService = hotlineService;
    }

    @GetMapping("/hotlines")
    public String hotlines(Model model) {
        model.addAttribute("hotlines", hotlineService.getPublicHotlines());
        return "user/hotlines";
    }
}

