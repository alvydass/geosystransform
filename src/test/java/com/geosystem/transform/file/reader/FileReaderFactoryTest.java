package com.geosystem.transform.file.reader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class FileReaderFactoryTest {

    @Test
    public void testGetReaderForCsvFile() {
        String fileName = "test.csv";

        CoordinateFileReader reader = FileReaderFactory.getReader(fileName);

        assertTrue(reader instanceof CsvReader);
    }

    @Test
    public void testGetReaderForJsonFile() {
        String fileName = "test.json";

        CoordinateFileReader reader = FileReaderFactory.getReader(fileName);

        assertTrue(reader instanceof GeoJsonReader);
    }

    @Test
    public void testGetReaderForKmlFile() {
        String fileName = "test.kml";

        CoordinateFileReader reader = FileReaderFactory.getReader(fileName);

        assertTrue(reader instanceof KmlReader);
    }

    @Test
    public void testGetReaderForUnsupportedFileType() {
        String fileName = "test.txt";

        assertThrows(IllegalArgumentException.class, () -> {
            FileReaderFactory.getReader(fileName);
        });
    }
}
