package com.example.demo.repository.impl;

import com.example.demo.entity.Place;
import com.example.demo.jooq.sample.model.tables.Places;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.util.PostGisUtil;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.jooq.impl.DSL.field;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepository {
    private final DSLContext dsl;

    @Override
    public int add(Place place) {
        return dsl.insertInto(Places.PLACES)
                .set(Places.PLACES.NAME, place.getName())
                .set(field("location", String.class), PostGisUtil.stLineFromText(place.getLocation()))
                .set(field("length", double.class), PostGisUtil.stLength(place.getLocation()))
                .execute();
    }

    @Override
    public Optional<Place> findById(long id) {
        return dsl.select(Places.PLACES.ID, Places.PLACES.NAME, PostGisUtil.stAsGeoJson(Places.PLACES.LOCATION).as("location"), Places.PLACES.LENGTH)
                .from(Places.PLACES)
                .where(Places.PLACES.ID.eq(id))
                .fetchOptionalInto(Place.class);
    }
}
