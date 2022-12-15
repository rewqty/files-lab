package com.rewqty.fileslab.models;

import java.util.Date;

public class FileModel {
    private String path;
    private String name;
    private Long size;
    private Date dateLastModified;
    public FileModel(String path, String name, Long size, Date lastModifiedDate) {
        this.path = path;
        this.name = name;
        this.size = size;
        this.dateLastModified = lastModifiedDate;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public Date getDateLastModified() {
        return dateLastModified;
    }
}
