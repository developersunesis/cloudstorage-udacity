package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper){
        this.noteMapper = noteMapper;
    }

    public int saveNote(Note note){
        return note.getNoteId() != 0 ? noteMapper.updateNote(note) : noteMapper.addNote(note);
    }

    public Note getNote(int id){
        return noteMapper.getNote(id);
    }

    public List<Note> getNotes(int userId) {
        return noteMapper.getNotes(userId);
    }

    public int deleteNote(int id){
        return noteMapper.deleteNote(id);
    }
}
