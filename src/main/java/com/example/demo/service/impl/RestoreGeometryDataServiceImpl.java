package com.example.demo.service.impl;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.demo.service.AmazonS3Service;
import com.example.demo.service.RestoreGeometryDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
@EnableScheduling
public class RestoreGeometryDataServiceImpl implements RestoreGeometryDataService {
    private static final String BACKUP_FILE_NAME = "backup.sql";
    private static final String RESTORE_FILE_NAME = "restore.sql";
    private static final String PG_DUMP = "pg_dump";
    private static final String HOST_CMD = "-h";
    private static final String PORT_CMD = "-p";
    private static final String DATABASE_CMD = "-d";
    private static final String FILE_CMD = "-f";
    private static final String USER_CMD = "-U";
    private static final String TABLE_CMD = "-t";
    private static final String PSQL = "psql";
    private final AmazonS3Service amazonS3Service;
    private final String password;
    private final String user;
    private final String host;
    private final String port;
    private final String dbName;

    public RestoreGeometryDataServiceImpl(AmazonS3Service amazonS3Service,
                                          @Value("${spring.datasource.password}") String password,
                                          @Value("${spring.datasource.username}") String user,
                                          @Value("${spring.datasource.host}") String host,
                                          @Value("${spring.datasource.port}") String port,
                                          @Value("${spring.datasource.db.name}") String dbName) {
        this.amazonS3Service = amazonS3Service;
        this.password = password;
        this.user = user;
        this.host = host;
        this.port = port;
        this.dbName = dbName;
    }

    @Override
    public boolean restore() {
        S3Object s3Object = amazonS3Service.getFile(BACKUP_FILE_NAME);
        File file = new File(RESTORE_FILE_NAME);
        try (S3ObjectInputStream inputStream = s3Object.getObjectContent()) {
            Files.copy(inputStream, file.toPath());
            if (file.exists()) {
                ProcessBuilder builder = new ProcessBuilder(getRestoreCommand(file.getAbsolutePath()));
                builder.environment().put("PGPASSWORD", password);
                return builder.start().waitFor() == 0 && file.delete();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Scheduled(cron = "* */15 * * * *")
    public void backupGeometries() throws IOException, InterruptedException {
        File file = new File(BACKUP_FILE_NAME);
        ProcessBuilder builder = new ProcessBuilder(getBackupCommand(file.getAbsolutePath()));
        builder.environment().put("PGPASSWORD", password);
        if (builder.start().waitFor() == 0) {
            amazonS3Service.save(file);
            file.delete();
        }
        throw new IOException("Can't create backup.sql");
    }

    private List<String> getBackupCommand(String path) {
        return List.of(PG_DUMP,
                HOST_CMD, host,
                PORT_CMD, port,
                USER_CMD, user,
                DATABASE_CMD, dbName,
                TABLE_CMD, "polygon",
                TABLE_CMD, "line",
                FILE_CMD, path);
    }

    private List<String> getRestoreCommand(String path) {
        return List.of(PSQL,
                HOST_CMD, host,
                PORT_CMD, port,
                USER_CMD, user,
                DATABASE_CMD, dbName,
                FILE_CMD, path);
    }
}
