package com.example.demo.util.impl;

import com.example.demo.util.PostGisUtil;
import org.jooq.Field;
import org.springframework.stereotype.Component;

import static org.jooq.impl.DSL.field;

@Component
public class PostGisUtilImpl implements PostGisUtil {

    @Override
    public Field<?> stAsText(Field<?> location) {
        return field("st_astext({0})", String.class, location);
    }

    @Override
    public Field<Double> stLength(String location) {
        return field("st_length({0}::geography)/1000", double.class, location);
    }

    @Override
    public Field<String> stLineFromText(String location) {
        return field("st_linefromtext({0})", String.class, location);
    }
}
