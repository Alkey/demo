package com.example.demo;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public class ConnectionUtils {
    private static final String URL = "jdbc:postgresql://localhost:5432/foo";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "root";

    public static DSLContext getConnection() {
        return DSL.using(URL, USERNAME, PASSWORD);
    }
}
