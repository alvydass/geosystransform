package com.geosystem.transform.file.reader;

import com.geosystem.transform.file.Coordinate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class KmlReaderTest {

    @Test
    public void testReadValidKmlFile() {
        String kmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" +
                "    <Document>\n" +
                "        <Placemark>\n" +
                "            <Point>\n" +
                "                <coordinates>24.743957519531254,55.61132323643337</coordinates>\n" +
                "            </Point>\n" +
                "        </Placemark>\n" +
                "    </Document>\n" +
                "</kml>";
        InputStream fileStream = new ByteArrayInputStream(kmlContent.getBytes());

        // Create KmlReader instance
        KmlReader kmlReader = new KmlReader();

        // Call the read method
        List<Coordinate> coordinates = kmlReader.read(fileStream);

        // Assertions
        assertEquals(1, coordinates.size());
        assertEquals(55.61132323643337, coordinates.get(0).getLatitude());
        assertEquals(24.743957519531254, coordinates.get(0).getLongitude());
    }

    @Test
    public void testReadEmptyKmlFile() {
        String kmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document></Document></kml>";
        InputStream fileStream = new ByteArrayInputStream(kmlContent.getBytes());

        KmlReader kmlReader = new KmlReader();

        List<Coordinate> coordinates = kmlReader.read(fileStream);

        assertEquals(0, coordinates.size());
    }

    @Test
    public void testReadKmlFileWithInvalidFormat() {
        String kmlContent = "<invalid></invalid>";
        InputStream fileStream = new ByteArrayInputStream(kmlContent.getBytes());

        KmlReader kmlReader = new KmlReader();

        assertThrows(RuntimeException.class, () -> {
            kmlReader.read(fileStream);
        });
    }
}
