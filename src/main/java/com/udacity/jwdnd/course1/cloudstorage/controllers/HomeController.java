package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Security;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    NoteService noteService;

    @Autowired
    CredentialService credentialService;

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    UserService userService;

    @Autowired
    FileService fileService;

    @ModelAttribute("notes")
    public List<Note> getNotes(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUser(auth.getName());
        return noteService.getNotes(user.getUserId());
    }

    @ModelAttribute("userCredentials")
    public List<Credential> getCredentials(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUser(auth.getName());
        return credentialService.getCredentials(user.getUserId()).stream().map(credential -> {
            credential.setDecryptedPassword(encryptionService
                    .decryptValue(credential.getPassword(), credential.getKey()));
            return credential;
        }).collect(Collectors.toList());
    }

    @ModelAttribute("files")
    public List<Files> getFiles(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUser(auth.getName());
        return fileService.getFiles(user.getUserId());
    }

    @GetMapping
    public String home(){
        return "home";
    }

}
