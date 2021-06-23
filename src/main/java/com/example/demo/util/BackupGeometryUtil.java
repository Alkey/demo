package com.example.demo.util;

import com.example.demo.service.AmazonS3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@EnableScheduling
public class BackupGeometryUtil {
    private static final String FILE_NAME = "mytab.sql";
    private final AmazonS3Service amazonS3Service;
    private final String password;

    public BackupGeometryUtil(AmazonS3Service amazonS3Service,
                              @Value("${spring.datasource.password}") String password) {
        this.amazonS3Service = amazonS3Service;
        this.password = password;
    }

    @Scheduled(cron = "* */15 * * * *")
    public void backupGeometries() throws IOException, InterruptedException {
        File file = new File(FILE_NAME);
        ProcessBuilder builder = new ProcessBuilder(getCommand(file.getAbsolutePath()));
        builder.environment().put("PGPASSWORD", password);
        if (builder.start().waitFor() == 0) {
            amazonS3Service.save(file);
            file.delete();
        }
    }

    private List<String> getCommand(String path) {
        return List.of("pg_dump",
                "-h", "localhost",
                "-p", "5432",
                "-U", "admin",
                "-d", "mydb",
                "-t", "polygon",
                "-t", "line",
                "-f", path);
    }
}
