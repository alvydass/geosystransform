package com.geosystem.transform.converter;

import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.geosystem.transform.converter.model.LksCoordinate;
import com.geosystem.transform.enums.CoordinateType;
import com.geosystem.transform.file.Coordinate;
import com.geosystem.transform.file.FileType;
import com.vaadin.flow.server.StreamResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class FileConverterTest {

    private FileConverter fileConverter;
    private CoordinateConverter coordinateConverter;

    @BeforeEach
    public void setUp() {
        coordinateConverter = mock(CoordinateConverter.class);
        fileConverter = new FileConverter(coordinateConverter);
    }

    @Test
    public void testConvertWithDestinationFileType() {
        InputStream fileStream = new ByteArrayInputStream("40.7128,-74.0060".getBytes());
        String fileName = "test.csv";
        CoordinateType from = CoordinateType.WGS;
        CoordinateType to = CoordinateType.LKS;
        FileType destinationFileType = FileType.CSV;

        List<Coordinate> coordinates = Arrays.asList(
                Coordinate.of(40.7128, -74.0060)
        );
        List<CoordinateWrapper> transformedCoordinates = Arrays.asList(
                new LksCoordinate(1007920, -8286147)
        );
        when(coordinateConverter.convert(coordinates, from, to)).thenReturn(transformedCoordinates);


        StreamResource result = fileConverter.convert(fileStream, fileName, from, to, destinationFileType);


        assertNotNull(result);
    }
}
