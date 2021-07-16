package com.example.demo.converter;

import com.example.demo.entity.FeatureCollection;
import com.example.demo.entity.FeatureCollectionWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.demo.util.ObjectMapperUtil.getMapper;

@Component
public class FeatureCollectionConverter {

    public FeatureCollectionWrapper toWrapper(FeatureCollection featureCollection) throws JsonProcessingException {
        return new FeatureCollectionWrapper(featureCollection.getId(), getMapper().writeValueAsBytes(featureCollection));
    }

    public FeatureCollection fromWrapper(FeatureCollectionWrapper wrapper) throws IOException {
        return getMapper().readValue(wrapper.getFeatureCollection(), FeatureCollection.class);
    }
}
