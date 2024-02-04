package com.geosystem.transform.converter;

import com.geosystem.transform.converter.model.LksResponse;
import com.geosystem.transform.converter.model.Response;
import com.geosystem.transform.converter.model.WgsResponse;
import com.geosystem.transform.enums.CoordinateType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseFactory {

    public static Response getResponse(CoordinateType destinationType, double aVal, double bVal) {
        switch (destinationType) {
            case LKS: return new LksResponse((int) aVal, (int) bVal);
            case WGS: return new WgsResponse(aVal, bVal);
            default:
                throw new IllegalArgumentException("Unsupported destination type: " + destinationType);
        }
    }
}
