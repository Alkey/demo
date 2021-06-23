package com.example.demo.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.demo.service.RestoreGeometryDataService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestoreGeometryDataServiceImpl implements RestoreGeometryDataService {
    private static final String FILE_PATH = "/home/akim/app/motionize/server/demo/restore.sql";
    private final AmazonS3 client;
    @Value("${aws.s3.bucket}")
    private String bucket;
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public boolean restore() throws IOException, InterruptedException {
        S3Object s3Object = client.getObject(bucket, "backup");
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        FileUtils.copyInputStreamToFile(inputStream, new File(FILE_PATH));
        List<String> cmd = List.of("psql",
                "-h", "localhost",
                "-p", "5432",
                "-U", "admin",
                "-d", "mydb",
                "-f", FILE_PATH);
        ProcessBuilder builder = new ProcessBuilder(cmd);
        builder.environment().put("PGPASSWORD", password);
        return builder.start().waitFor() == 0;
    }
}
