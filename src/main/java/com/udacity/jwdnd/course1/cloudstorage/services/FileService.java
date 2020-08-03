package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    FileMapper fileMapper;

    public FileService(FileMapper fileMapper){
        this.fileMapper = fileMapper;
    }

    public int addFile(Files file){
        return fileMapper.addFile(file);
    }

    public Files getFile(int id){
        return fileMapper.getFile(id);
    }

    public List<Files> getFiles(int userId){
        return fileMapper.getFiles(userId);
    }

    public int deleteFile(int id){
        return fileMapper.deleteFile(id);
    }

    public boolean fileNameExists(String filename) {
        return fileMapper.getFileByName(filename) != null;
    }
}
