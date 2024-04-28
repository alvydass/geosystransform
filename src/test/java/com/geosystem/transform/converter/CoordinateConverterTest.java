package com.geosystem.transform.converter;

import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.geosystem.transform.enums.CoordinateType;
import com.geosystem.transform.file.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class CoordinateConverterTest {

    private CoordinateConverter coordinateConverter;

    @BeforeEach
    public void setUp() {
        coordinateConverter = new CoordinateConverter();
    }

    @Test
    public void testConvertSingleCoordinate() {
        double lat = 40.7128;
        double lon = -74.0060;
        CoordinateType from = CoordinateType.WGS;
        CoordinateType to = CoordinateType.LKS;

        CoordinateWrapper result = coordinateConverter.convert(lat, lon, from, to);

        assertEquals(result.getAval(), "1007920");
        assertEquals(result.getBval(), "-8286147");
    }

    @Test
    public void testConvertListOfCoordinates() {
        List <Coordinate> coordinates = Arrays.asList(
                Coordinate.of(40.7128, -74.0060)
        );
        CoordinateType from = CoordinateType.WGS;
        CoordinateType to = CoordinateType.LKS;

        List<CoordinateWrapper> result = coordinateConverter.convert(coordinates, from, to);

        assertEquals(coordinates.size(), result.size());
        assertEquals(result.get(0).getAval(), "1007920");
        assertEquals(result.get(0).getBval(), "-8286147");
    }
}
