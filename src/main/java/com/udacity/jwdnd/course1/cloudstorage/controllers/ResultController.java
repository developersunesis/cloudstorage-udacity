package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ResultController {

    Boolean success, errorNotSaved;

    String error;

    @ModelAttribute("success")
    private Boolean getSuccess(){
        return success;
    }

    @ModelAttribute("error")
    private String getError(){
        return error;
    }

    @ModelAttribute("errorNotSaved")
    private Boolean getErrorNotSaved(){
        return errorNotSaved;
    }

    @GetMapping("/result")
    public String result(){
        return "result";
    }
}
