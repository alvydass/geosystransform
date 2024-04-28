package com.geosystem.transform.file.writer;

import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.geosystem.transform.converter.model.LksCoordinate;
import com.vaadin.flow.server.StreamResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class KmlWriterTest {

    @Test
    public void testWriteKmlFile() {
        List<CoordinateWrapper> coordinates = Arrays.asList(
                new LksCoordinate(123, 456)
        );

        KmlWriter kmlWriter = new KmlWriter();

        StreamResource streamResource = kmlWriter.write(coordinates, "test");

        assertNotNull(streamResource);
    }
}
