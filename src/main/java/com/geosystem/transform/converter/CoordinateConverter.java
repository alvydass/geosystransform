package com.geosystem.transform.converter;

import com.geosystem.transform.converter.model.Response;
import com.geosystem.transform.enums.CoordinateType;
import org.locationtech.proj4j.*;
import org.springframework.stereotype.Component;

@Component
public class CoordinateConverter {


    public Response convert(double lat, double lon, CoordinateType from, CoordinateType to) {
        CRSFactory crsFactory = new CRSFactory();
        CoordinateReferenceSystem fromCoordinateSystem = crsFactory.createFromName(from.getCode());
        CoordinateReferenceSystem toCoordinateSystem = crsFactory.createFromName(to.getCode());


        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CoordinateTransform coordinateTransformer = ctFactory.createTransform(fromCoordinateSystem, toCoordinateSystem);
        ProjCoordinate fromCoordinate = new ProjCoordinate(lon, lat);
        ProjCoordinate toCoordinate = new ProjCoordinate();
        coordinateTransformer.transform(fromCoordinate, toCoordinate);
        return ResponseFactory.getResponse(to, toCoordinate.x, toCoordinate.y);
    }
}
