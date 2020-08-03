package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.rmi.runtime.Log;

import java.util.logging.Logger;

@Controller
public class NoteController {

    @Autowired
    UserService userService;

    @Autowired
    NoteService noteService;

    private Boolean success, errorNotSaved;

    private String error;

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

    @PostMapping("/saveNote")
    public String addNote(Authentication authentication,
                                @RequestParam(value = "noteId", required = false) Integer id,
                                @RequestParam("noteTitle") String title,
                                @RequestParam("noteDescription") String description,
                                RedirectAttributes redirectAttributes){
        User user = userService.getUser(authentication.getName());
        Note note = new Note();

        if(id != null){
            note = noteService.getNote(id);
        }

        note.setNoteTitle(title);
        note.setNoteDescription(description);
        note.setUserid(user.getUserId());

        if(noteService.saveNote(note) < 0){
            errorNotSaved = true;
            success = false;
        } else success = true;

        redirectAttributes.addFlashAttribute("error", error)
                .addFlashAttribute("success", success)
                .addFlashAttribute("errorNotSaved", errorNotSaved);
        return "redirect:/result";
    }

    @GetMapping("/deleteNote/{noteId}")
    public String deleteNote(@PathVariable int noteId, RedirectAttributes redirectAttributes){
        if(noteService.deleteNote(noteId) < 0){
            error = "Unable to delete note";
            success = false;
        } else success = true;


        redirectAttributes.addFlashAttribute("error", error)
                .addFlashAttribute("success", success)
                .addFlashAttribute("errorNotSaved", errorNotSaved);
        return "redirect:/result";
    }

}
