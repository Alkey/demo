package com.example.demo.service;

import com.amazonaws.services.s3.model.S3Object;

import java.io.File;

public interface AmazonS3Service {
    void save(File file);

    S3Object getFile(String fileName);
}
