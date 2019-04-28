package io.colby.modules.routes.readings.config;

import io.colby.modules.routes.sensors.entity.SensorType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SensorTypeEnumConverter implements Converter<String, SensorType> {

    /**
     * Converts the String passed by the HTTP request to
     * SensorType enum
     *
     * @param s String to convert
     * @return SensorType enum
     */
    @Override
    public SensorType convert(String s) {
        try {
            return SensorType.fromText(s);
        } catch (Exception e) {
            return null; // or SortEnum.asc
        }
    }
}
