package com.geosystem.transform.converter;

import com.geosystem.transform.converter.model.Response;
import com.geosystem.transform.enums.CoordinateType;
import org.locationtech.proj4j.*;

public class CoordinateConverter {


    public Response convert(double lat, double lon, CoordinateType from, CoordinateType to) {
        CRSFactory crsFactory = new CRSFactory();
        CoordinateReferenceSystem wgs84CRS = crsFactory.createFromName("EPSG:4326");
        CoordinateReferenceSystem lks94CRS = crsFactory.createFromName("EPSG:3346");


        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CoordinateTransform coordinateTransformer = ctFactory.createTransform(wgs84CRS, lks94CRS);
        ProjCoordinate wgs84Coord = new ProjCoordinate(lon, lat);
        ProjCoordinate lks94Coord = new ProjCoordinate();
        coordinateTransformer.transform(wgs84Coord, lks94Coord);
        return ResponseFactory.getResponse(to, lks94Coord.y, lks94Coord.x);
    }
}
