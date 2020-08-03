package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialController {

    @Autowired
    UserService userService;

    @Autowired
    CredentialService credentialService;

    private Boolean success, errorNotSaved;

    private String error;

    @PostMapping("/saveCredential")
    public String addCredential(Authentication authentication,
                                @RequestParam(value = "credentialId", required = false) Integer id,
                                @RequestParam("url") String url,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password,
                                RedirectAttributes redirectAttributes){
        User user = userService.getUser(authentication.getName());
        Credential credential = new Credential();

        if(id != null){
            credential = credentialService.getCredential(id);
        }

        credential.setUrl(url);
        credential.setUsername(username);
        credential.setPassword(password);
        credential.setUserid(user.getUserId());

        if(credentialService.saveCredential(credential) < 0){
            errorNotSaved = true;
            success = false;
        } else success = true;

        redirectAttributes.addFlashAttribute("error", error)
                .addFlashAttribute("success", success)
                .addFlashAttribute("errorNotSaved", errorNotSaved);
        return "redirect:/result";
    }

    @GetMapping("/deleteCredential/{id}")
    public String deleteCredential(@PathVariable int id, RedirectAttributes redirectAttributes){
        if(credentialService.deleteCredential(id) < 0){
            error = "Unable to delete credential";
            success = false;
        } else success = true;

        redirectAttributes.addFlashAttribute("error", error)
                .addFlashAttribute("success", success)
                .addFlashAttribute("errorNotSaved", errorNotSaved);
        return "redirect:/result";
    }

}
