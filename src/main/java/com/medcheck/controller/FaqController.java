package com.medcheck.controller;

import com.medcheck.service.FaqService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaqController {

    private final FaqService faqService;

    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping("/faqs")
    public String faqs(Model model) {
        model.addAttribute("faqs", faqService.getPublicFaqs());
        return "user/faqs";
    }
}

