package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE noteid = #{id}")
    Note getNote(int id);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getNotes(int userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addNote(Note note);

    @Delete("DELETE NOTES WHERE noteid = #{id}")
    int deleteNote(int id);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid = #{noteId}")
    int updateNote(Note note);

}
