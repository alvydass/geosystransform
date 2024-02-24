package com.geosystem.transform.file.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.vaadin.flow.server.StreamResource;
import org.geojson.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GeoJsonWriter implements CoordinateFileWriter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public StreamResource write(List<CoordinateWrapper> coordinates, String fileNamePart) {
        try {
            FeatureCollection featureCollection = createFeatureCollection(coordinates);
            String geoJsonString = convertToJson(featureCollection);
            InputStream inputStream = new ByteArrayInputStream(geoJsonString.getBytes(StandardCharsets.UTF_8));

            return new StreamResource(fileNamePart + "File.json", () -> inputStream);
        } catch (IOException e) {
            throw new CoordinateWriteException("Failed to write json file because " + e.getMessage(), e);
        }
    }

    private FeatureCollection createFeatureCollection(List<CoordinateWrapper> coordinates) {
        FeatureCollection featureCollection = new FeatureCollection();
        for (CoordinateWrapper coordinate : coordinates) {
            double latitude = Double.parseDouble(coordinate.getAval());
            double longitude = Double.parseDouble(coordinate.getBval());
            LngLatAlt lngLatAlt = new LngLatAlt(longitude, latitude);

            Point point = new Point(lngLatAlt);
            Feature feature = new Feature();
            feature.setGeometry(point);

            featureCollection.add(feature);
        }
        return featureCollection;
    }

    private String convertToJson(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }
}
