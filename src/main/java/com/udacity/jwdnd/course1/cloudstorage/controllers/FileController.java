package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    UserService userService;

    @Autowired
    FileService fileService;

    private Boolean success, errorNotSaved;

    private String error;

    @PostMapping("/upload")
    public String addCredential(Authentication authentication,
                                @RequestParam("fileUpload") MultipartFile fileUpload,
                                RedirectAttributes redirectAttributes){
        User user = userService.getUser(authentication.getName());
        String filename = fileUpload.getOriginalFilename();

        if(!fileService.fileNameExists(filename)) {
            Files file = new Files();
            file.setUserid(user.getUserId());

            try {
                file.setFiledata(fileUpload.getBytes());
                file.setContenttype(fileUpload.getContentType());
                file.setFilename(filename);
                file.setFilesize(String.valueOf(fileUpload.getSize()));
            } catch (IOException e) {
                e.printStackTrace();
                error = "An error was encountered reading the file";
            }

            if (fileService.addFile(file) < 0) {
                errorNotSaved = true;
                success = false;
            } else success = true;

        } else {
            error = "This file already exists please upload another file.";
            success = false;
        }

        redirectAttributes.addFlashAttribute("error", error)
                .addFlashAttribute("success", success)
                .addFlashAttribute("errorNotSaved", errorNotSaved);
        return "redirect:/result";
    }

    @GetMapping("/deleteFile/{id}")
    public String deleteCredential(@PathVariable int id, RedirectAttributes redirectAttributes){
        if(fileService.deleteFile(id) < 0){
            error = "Unable to delete credential";
            success = false;
        } else success = true;

        redirectAttributes.addFlashAttribute("error", error)
                .addFlashAttribute("success", success)
                .addFlashAttribute("errorNotSaved", errorNotSaved);
        return "redirect:/result";
    }

    @GetMapping("/file/{id}")
    public String downloadFile(Authentication authentication, @PathVariable int id, RedirectAttributes redirectAttributes){
        User user = userService.getUser(authentication.getName());
        Files theFile = fileService.getFile(id);

        if(theFile == null){
            redirectAttributes
                    .addFlashAttribute("error", "File doesn't exist");
            return "redirect:/result";
        } else if(theFile.getUserid() != user.getUserId()){
            redirectAttributes
                    .addFlashAttribute("error", "You are not permitted to view this file");
            return "redirect:/result";
        }

        return "redirect:/file/" + id + "/view";
    }

    @GetMapping("/file/{id}/view")
    public ResponseEntity<byte[]> downloadFile(@PathVariable int id, RedirectAttributes redirectAttributes){
        Files theFile = fileService.getFile(id);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", theFile.getContenttype());
        headers.setContentLength(Long.parseLong(theFile.getFilesize()));
        ContentDisposition disposition = ContentDisposition
                .builder("inline")
                .filename(theFile.getFilename())
                .build();
        headers.setContentDisposition(disposition);
        return new ResponseEntity<>(theFile.getFiledata(), headers, HttpStatus.OK);
    }
}
