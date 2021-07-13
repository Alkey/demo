package com.example.demo.converter;

import com.example.demo.entity.GeoJsonGeometry;
import com.example.demo.entity.GeoJsonLineGeometry;
import com.example.demo.entity.GeoJsonPolygonGeometry;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jooq.Converter;
import org.springframework.stereotype.Component;

import static com.example.demo.util.ObjectMapperUtil.getMapper;

@Component
public class GeoJsonGeometryConverter implements Converter<String, GeoJsonGeometry> {
    @Override
    public GeoJsonGeometry from(String geometry) {
        try {
            return getMapper().readValue(geometry, GeoJsonGeometry.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String to(GeoJsonGeometry geometry) {
        if (geometry.getType().equalsIgnoreCase("LineString")) {
            return ((GeoJsonLineGeometry) geometry).toEntity().toString();
        }
        return ((GeoJsonPolygonGeometry) geometry).toEntity().toString();
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<GeoJsonGeometry> toType() {
        return GeoJsonGeometry.class;
    }
}
