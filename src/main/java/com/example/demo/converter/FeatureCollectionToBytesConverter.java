package com.example.demo.converter;

import com.example.demo.entity.FeatureCollection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

@Component
@ReadingConverter
public class FeatureCollectionToBytesConverter implements Converter<byte[], FeatureCollection> {
    @Override
    public FeatureCollection convert(byte[] bytes) {
        return (FeatureCollection) SerializationUtils.deserialize(bytes);
    }
}
