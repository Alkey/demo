package com.example.demo.util;

import org.jooq.Field;
import org.springframework.stereotype.Component;

import static org.jooq.impl.DSL.field;

@Component
public class PostGisUtil {

    public static Field<String> stAsText(Field<String> geometry) {
        return field("st_astext({0})", String.class, geometry);
    }

    public static Field<Double> stLength(String geometryString) {
        return field("st_length({0}::geography)", double.class, geometryString);
    }

    public static Field<String> stLineFromText(String geometryString) {
        return field("st_linefromtext({0})", String.class, geometryString);
    }

    public static Field<String> convertToGeoJsonAndCoordinates(Field<String> geometry) {
        return field("st_asgeojson({0}) :: json-> 'coordinates'", String.class, geometry);
    }

    public static Field<String> stPolygonFromText(String geometryString) {
        return field("st_polygonfromtext({0})", String.class, geometryString);
    }

    public static Field<Double> stArea(String geometryString) {
        return field("st_area({0}::geography)", double.class, geometryString);
    }
}
