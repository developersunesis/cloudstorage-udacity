package com.udacity.jwdnd.course1.cloudstorage.models;

public class Files {

    private int fileId;
    private String filename;
    private String contenttype;
    private String filesize;
    private byte[] filedata;
    private int userid;

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Files{" +
                "fileId=" + fileId +
                ", filename='" + filename + '\'' +
                ", contenttype='" + contenttype + '\'' +
                ", filesize='" + filesize + '\'' +
                ", userid=" + userid +
                '}';
    }
}
