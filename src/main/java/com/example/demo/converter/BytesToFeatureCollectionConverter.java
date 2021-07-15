package com.example.demo.converter;

import com.example.demo.entity.FeatureCollection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

@Component
@WritingConverter
public class BytesToFeatureCollectionConverter implements Converter<FeatureCollection, byte[]> {

    @Override
    public byte[] convert(FeatureCollection featureCollection) {
        return SerializationUtils.serialize(featureCollection);
    }
}
