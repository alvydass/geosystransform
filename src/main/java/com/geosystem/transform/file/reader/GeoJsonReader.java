package com.geosystem.transform.file.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geosystem.transform.file.Coordinate;
import lombok.SneakyThrows;
import org.geojson.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GeoJsonReader implements CoordinateFileReader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public List<Coordinate> read(InputStream fileStream) {
        FeatureCollection geometryCollection = objectMapper.readValue(fileStream, FeatureCollection.class);

        List<Feature> features = geometryCollection.getFeatures();
        List<Coordinate> coordinates = new ArrayList<>();
        for (Feature feature : features) {
            GeoJsonObject geometry = feature.getGeometry();
            if (geometry instanceof Point) {
                LngLatAlt coordinate = ((Point) geometry).getCoordinates();
                coordinates.add(Coordinate.of(coordinate.getLatitude(), coordinate.getLongitude()));
            }
        }
        return coordinates;
    }
}
