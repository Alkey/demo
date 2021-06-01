package com.example.demo.repository.impl;

import com.example.demo.entity.Place;
import com.example.demo.jooq.sample.model.tables.Places;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.util.PostGisUtil;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static org.jooq.impl.DSL.field;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepository {
    private final PostGisUtil util;
    private final DSLContext dsl;

    @Override
    public double add(Place place) {
        return Objects.requireNonNull(dsl.insertInto(Places.PLACES)
                .set(Places.PLACES.NAME, place.getName())
                .set(field("location", String.class), util.stLineFromText(place.getLocation()))
                .set(field("length", double.class), util.stLength(place.getLocation()))
                .returningResult(Places.PLACES.LENGTH)
                .fetchOne())
                .into(double.class);
    }

    @Override
    public Place get(long id) {
        return Objects.requireNonNull(dsl.select(Places.PLACES.ID, Places.PLACES.NAME, util.stAsText(Places.PLACES.LOCATION).as("location"), Places.PLACES.LENGTH)
                .from(Places.PLACES)
                .where(Places.PLACES.ID.eq(id))
                .fetchOne())
                .into(Place.class);
    }
}
