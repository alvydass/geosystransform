package com.geosystem.transform.file.reader;

import com.geosystem.transform.file.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KmlReader implements CoordinateFileReader {

    @Override
    public List<Coordinate> read(InputStream fileStream) {
        List<Coordinate> coordinates = new ArrayList<>();

        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(Kml.class);
            Unmarshaller u = jc.createUnmarshaller();
            Kml kml = (Kml) u.unmarshal(fileStream);

            Feature feature = kml.getFeature();
            if (feature instanceof Placemark placemark) {
                Geometry geometry = placemark.getGeometry();
                if (geometry instanceof Point point) {
                    List<de.micromata.opengis.kml.v_2_2_0.Coordinate> kmlCoordinates = point.getCoordinates();
                    coordinates.addAll(mapCoordinates(kmlCoordinates));
                }
            }
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return coordinates;
    }

    private Collection<Coordinate> mapCoordinates(List<de.micromata.opengis.kml.v_2_2_0.Coordinate> kmlCoordinates) {
        return kmlCoordinates.stream()
                .map(kml -> Coordinate.of(kml.getLatitude(), kml.getLongitude()))
                .toList();
    }
}
