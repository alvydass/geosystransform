package com.geosystem.transform.file.writer;

import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.geosystem.transform.converter.model.LksCoordinate;
import com.vaadin.flow.server.StreamResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class CsvWriterTest {

    @Test
    public void testWriteCsvFile() throws IOException {
        // Prepare some dummy coordinate data
        List<CoordinateWrapper> coordinates = Arrays.asList(
                new LksCoordinate(123, 456)
        );

        CsvWriter csvWriter = new CsvWriter();

        StreamResource streamResource = csvWriter.write(coordinates, "test");
        assertNotNull(streamResource);
    }

}
