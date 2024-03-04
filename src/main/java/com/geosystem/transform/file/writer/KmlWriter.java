package com.geosystem.transform.file.writer;

import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.vaadin.flow.server.StreamResource;
import de.micromata.opengis.kml.v_2_2_0.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
            Document document = kmlFile.createAndSetDocument();

            for (CoordinateWrapper coordinateWrapper : coordinates) {
                Placemark placemark = createPlacemark(coordinateWrapper);
                document.createAndAddPlacemark().withGeometry(placemark.getGeometry());
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
        List<Coordinate> coordinates = List.of(new Coordinate(
                Double.parseDouble(coordinateWrapper.getBval()),
                Double.parseDouble(coordinateWrapper.getAval())));
        point.setCoordinates(coordinates);

        Placemark placemark = new Placemark();
        placemark.setGeometry(point);

        return placemark;
    }
}
