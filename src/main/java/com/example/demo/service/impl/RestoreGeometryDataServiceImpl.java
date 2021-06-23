package com.example.demo.service.impl;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.demo.service.AmazonS3Service;
import com.example.demo.service.RestoreGeometryDataService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class RestoreGeometryDataServiceImpl implements RestoreGeometryDataService {
    private static final String FILE_NAME = "restore.sql";
    private final AmazonS3Service amazonS3Service;
    private final String password;

    public RestoreGeometryDataServiceImpl(AmazonS3Service amazonS3Service,
                                          @Value("${spring.datasource.password}") String password) {
        this.amazonS3Service = amazonS3Service;
        this.password = password;
    }

    @Override
    public boolean restore() {
        S3Object s3Object = amazonS3Service.getBackupFile();
        File file = new File(FILE_NAME);
        try (S3ObjectInputStream inputStream = s3Object.getObjectContent()) {
            FileUtils.copyInputStreamToFile(inputStream, file);
            if (file.exists()) {
                ProcessBuilder builder = new ProcessBuilder(getCommand(file.getAbsolutePath()));
                builder.environment().put("PGPASSWORD", password);
                boolean isRestored = builder.start().waitFor() == 0;
                return file.delete() && isRestored;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private List<String> getCommand(String path) {
        return List.of("psql",
                "-h", "localhost",
                "-p", "5432",
                "-U", "admin",
                "-d", "mydb",
                "-f", path);
    }
}
