package com.udacity.jwdnd.course1.cloudstorage.models;

public class Note {

    private int noteId;
    private String noteTitle;
    private String noteDescription;
    private int userid;

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public int getUserid() {
        return userid;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteDescription='" + noteDescription + '\'' +
                ", userid=" + userid +
                '}';
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
