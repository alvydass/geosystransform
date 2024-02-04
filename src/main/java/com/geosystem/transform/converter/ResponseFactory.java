package com.geosystem.transform.converter;

import com.geosystem.transform.converter.model.LksCoordinate;
import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.geosystem.transform.converter.model.WgsCoordinate;
import com.geosystem.transform.enums.CoordinateType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseFactory {

    public static CoordinateWrapper getResponse(CoordinateType destinationType, double aVal, double bVal) {
        switch (destinationType) {
            case LKS: return new LksCoordinate((int) aVal, (int) bVal);
            case WGS: return new WgsCoordinate(aVal, bVal);
            default:
                throw new IllegalArgumentException("Unsupported destination type: " + destinationType);
        }
    }
}
