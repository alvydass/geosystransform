package com.geosystem.transform.file.reader;

import com.geosystem.transform.file.Coordinate;
import com.geosystem.transform.file.reader.exception.CoordinateReadException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class GeoJsonReaderTest {

    @Test
    public void testReadValidGeoJsonFile() {
        String geoJsonContent = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[-74.0060,40.7128]}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[-118.2437,34.0522]}}]}";
        InputStream fileStream = new ByteArrayInputStream(geoJsonContent.getBytes());

        GeoJsonReader geoJsonReader = new GeoJsonReader();

        List<Coordinate> coordinates = geoJsonReader.read(fileStream);

        assertEquals(2, coordinates.size());
        assertEquals(40.7128, coordinates.get(0).getLatitude());
        assertEquals(-74.0060, coordinates.get(0).getLongitude());
        assertEquals(34.0522, coordinates.get(1).getLatitude());
        assertEquals(-118.2437, coordinates.get(1).getLongitude());
    }

    @Test
    public void testReadEmptyGeoJsonFile() {
        String geoJsonContent = "{\"type\":\"FeatureCollection\",\"features\":[]}";
        InputStream fileStream = new ByteArrayInputStream(geoJsonContent.getBytes());

        GeoJsonReader geoJsonReader = new GeoJsonReader();

        List     <Coordinate> coordinates = geoJsonReader.read(fileStream);

        assertEquals(0, coordinates.size());
    }

    @Test
    public void testReadGeoJsonFileWithInvalidFormat() {
        String geoJsonContent = "{\"type\":\"Invalid\",\"features\":[]}";
        InputStream fileStream = new ByteArrayInputStream(geoJsonContent.getBytes());

        GeoJsonReader geoJsonReader = new GeoJsonReader();

        assertThrows(CoordinateReadException.class, () -> {
            geoJsonReader.read(fileStream);
        });
    }
}
