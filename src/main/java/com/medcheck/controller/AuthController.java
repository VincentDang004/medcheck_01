package com.medcheck.controller;

import com.medcheck.model.User;
import com.medcheck.service.UserService;
import jakarta.validation.Valid;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthController {
    private final UserService userService;
    private final Environment environment;

    public AuthController(UserService userService, Environment environment) {
        this.userService = userService;
        this.environment = environment;
        this.userService.createAdminIfNotExist();
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/login/google")
    public RedirectView loginWithGoogle() {
        String clientId = environment.getProperty("app.google.client-id");
        String clientSecret = environment.getProperty("app.google.client-secret");

        if (clientId == null || clientId.isBlank() || clientSecret == null || clientSecret.isBlank()) {
            return new RedirectView("/login?googleConfigMissing");
        }

        return new RedirectView("/oauth2/authorization/google");
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "user/register";
        }
        userService.registerUser(user);
        return "redirect:/login?success";
    }
}
