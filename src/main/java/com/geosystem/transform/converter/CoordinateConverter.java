package com.geosystem.transform.converter;

import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.geosystem.transform.enums.CoordinateType;
import com.geosystem.transform.file.Coordinate;
import org.locationtech.proj4j.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoordinateConverter {

    public CoordinateWrapper convert(double lat, double lon, CoordinateType from, CoordinateType to) {
        CRSFactory crsFactory = new CRSFactory();
        CoordinateReferenceSystem fromCoordinateSystem = crsFactory.createFromName(from.getCode());
        CoordinateReferenceSystem toCoordinateSystem = crsFactory.createFromName(to.getCode());


        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CoordinateTransform coordinateTransformer = ctFactory.createTransform(fromCoordinateSystem, toCoordinateSystem);
        ProjCoordinate fromCoordinate = new ProjCoordinate(lat, lon);
        ProjCoordinate toCoordinate = new ProjCoordinate();
        coordinateTransformer.transform(fromCoordinate, toCoordinate);
        return ResponseFactory.getResponse(to, toCoordinate.x, toCoordinate.y);
    }

    public List<CoordinateWrapper> convert(List<Coordinate> coordinates, CoordinateType from, CoordinateType to) {
        CRSFactory crsFactory = new CRSFactory();
        CoordinateReferenceSystem fromCoordinateSystem = crsFactory.createFromName(from.getCode());
        CoordinateReferenceSystem toCoordinateSystem = crsFactory.createFromName(to.getCode());

        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CoordinateTransform coordinateTransformer = ctFactory.createTransform(fromCoordinateSystem, toCoordinateSystem);
        List<CoordinateWrapper> transformed = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            ProjCoordinate fromCoordinate = new ProjCoordinate(coordinate.getLatitude(), coordinate.getLongitude());
            ProjCoordinate toCoordinate = new ProjCoordinate();
            coordinateTransformer.transform(fromCoordinate, toCoordinate);
            transformed.add(ResponseFactory.getResponse(to, toCoordinate.x, toCoordinate.y));
        }
        return transformed;
    }
}
