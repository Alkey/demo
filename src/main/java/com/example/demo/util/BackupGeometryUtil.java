package com.example.demo.util;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class BackupGeometryUtil {
    private static final String FILE_PATH = "/home/akim/app/motionize/server/demo/mytab.sql";
    private final AmazonS3 client;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${aws.s3.bucket}")
    private String bucket;

    @Scheduled(cron = "* */15 * * * *")
    public void backupGeometries() throws IOException, InterruptedException {
        List<String> cmd = List.of("pg_dump",
                "-h", "localhost",
                "-p", "5432",
                "-U", "admin",
                "-d", "mydb",
                "-t", "polygon",
                "-t", "line",
                "-f", FILE_PATH);
        ProcessBuilder builder = new ProcessBuilder(cmd);
        builder.environment().put("PGPASSWORD", password);
        if (builder.start().waitFor() == 0) {
            client.putObject(bucket, "backup", new File(FILE_PATH));
        }
    }
}
