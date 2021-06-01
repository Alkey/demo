package com.example.demo.util;

import org.jooq.Field;

public interface PostGisUtil {
    Field<?> stAsText(Field<?> location);

    Field<Double> stLength(String location);

    Field<String> stLineFromText(String location);
}
