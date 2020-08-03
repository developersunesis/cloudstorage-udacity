package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileId = #{id}")
    Files getFile(int id);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<Files> getFiles(int userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(Files file);

    @Delete("DELETE FILES WHERE fileId = #{id}")
    int deleteFile(int id);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    Files getFileByName(String filename);
}
