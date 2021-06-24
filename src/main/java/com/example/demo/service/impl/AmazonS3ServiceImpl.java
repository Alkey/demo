package com.example.demo.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.example.demo.service.AmazonS3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {
    private static final String BACKUP_FILE_NAME = "backup.sql";
    private final AmazonS3 client;
    private final String bucket;

    public AmazonS3ServiceImpl(AmazonS3 client,
                               @Value("${aws.s3.bucket}") String bucket) {
        this.client = client;
        this.bucket = bucket;
    }

    @Override
    public void save(File file) {
        client.putObject(bucket, BACKUP_FILE_NAME, file);
    }

    @Override
    public S3Object getFile(String fileName) {
        return client.getObject(bucket, fileName);
    }
}
