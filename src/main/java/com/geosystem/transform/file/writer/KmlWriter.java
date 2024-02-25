package com.geosystem.transform.file.writer;

import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.opencsv.CSVWriter;
import com.vaadin.flow.server.StreamResource;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class KmlWriter implements CoordinateFileWriter {

    @Override
    public StreamResource write(List<CoordinateWrapper> coordinates, String fileNamePart) {
        byte[] kmlBytes = generateKmlBytes(coordinates);

        StreamResource streamResource = new StreamResource(fileNamePart + ".kml",
                () -> new ByteArrayInputStream(kmlBytes));
        return streamResource;
    }
    private byte[] generateKmlBytes(List<CoordinateWrapper> coordinates) {
        try {
            Kml kmlFile = new Kml();
            Document document  = new Document();
            for (CoordinateWrapper coordinateWrapper : coordinates) {
                Placemark placemark = createPlacemark(coordinateWrapper);
                document.getFeature().add(placemark);
            }

            JAXBContext jc = JAXBContext.newInstance(Kml.class);
            Marshaller marshaller = jc.createMarshaller();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            marshaller.marshal(kmlFile, outputStream);
            return outputStream.toByteArray();

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private Placemark createPlacemark(CoordinateWrapper coordinateWrapper) {
        Point point = new Point();
        point.setCoordinates(coordinateWrapper.getAval(), coordinateWrapper.getBval());

        Placemark placemark = new Placemark();
        placemark.setGeometry(point);

        return placemark;
    }

}
