package com.ssafy.cobook.util;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FileUploadsProperty {
    private String uploadDir = "/home/ubuntu/images";
//    private String uploadDir = "c:\\";

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}