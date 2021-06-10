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

    public static Field<String> stGeomFromText(String geometryString) {
        return field("st_geomfromtext({0})", String.class, geometryString);
    }

    public static Field<String> convertToGeoJsonAndCoordinates(Field<String> geometry) {
        return field("st_asgeojson({0}) :: json-> 'coordinates'", String.class, geometry);
    }

    public static Field<Double> stArea(String geometryString) {
        return field("st_area({0}::geography)", double.class, geometryString);
    }

    public static Field<String> stIntersects(Field<String> line, Field<String> polygon) {
        return field("st_intersects({0}, {1})", String.class, line, polygon);
    }

    public static Field<String> stWithIn(Field<String> line, Field<String> polygon) {
        return field("st_within({0}::geometry, {1}::geometry)", String.class, line, polygon);
    }

    public static Field<String> stDistance(Field<String> line, Field<String> polygon) {
        return field("st_distance({0}::geography, {1}::geography)", String.class, line, polygon);
    }

    public static Field<String> stIntersection(Field<String> firstGeometry, Field<String> secondGeometry) {
        return field("st_intersection({0}, {1})", String.class, firstGeometry, secondGeometry);
    }

    public static Field<String> convertToGeoJson(Field<String> geometry) {
        return field("st_asgeojson({0}) :: json", String.class, geometry);
    }
}
