package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String username,
                         @RequestParam String password, Model model){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);

        String signUpError = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            signUpError = "The username already exists.";
        }

        if (signUpError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signUpError = "There was an error signing you up. Please try again.";
            }
        }

        if (signUpError == null) {
            model.addAttribute("signUpSuccess", true);
        } else {
            model.addAttribute("signUpError", signUpError);
        }

        return "signup";
    }
}
