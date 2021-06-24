package com.example.demo.service;

import com.amazonaws.services.s3.model.S3Object;

import java.io.File;

public interface AmazonS3Service {
    void save(String fileName, File file);

    S3Object getS3Object(String fileName);
}
