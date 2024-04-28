package com.geosystem.transform.file.writer;

import com.geosystem.transform.file.FileType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class FileWriterFactoryTest {

    @Test
    public void testGetWriterForCsvFile() {
        FileType fileType = FileType.CSV;

        CoordinateFileWriter writer = FileWriterFactory.getWriter(fileType);

        assertTrue(writer instanceof CsvWriter);
    }

    @Test
    public void testGetWriterForJsonFile() {
        FileType fileType = FileType.JSON;

        CoordinateFileWriter writer = FileWriterFactory.getWriter(fileType);

        assertTrue(writer instanceof GeoJsonWriter);
    }

    @Test
    public void testGetWriterForKmlFile() {
        FileType fileType = FileType.KML;

        CoordinateFileWriter writer = FileWriterFactory.getWriter(fileType);

        assertTrue(writer instanceof KmlWriter);
    }
}
