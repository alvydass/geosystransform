package com.geosystem.transform.converter.model;

import com.geosystem.transform.file.Coordinate;
import lombok.Value;

@Value
public class LksCoordinate implements CoordinateWrapper {

    Coordinate coordinate;

    public LksCoordinate(int aVal, int bVal) {
        this.coordinate = Coordinate.of(aVal, bVal);
    }

    @Override
    public String getAval() {
        int latitude = (int) coordinate.getLatitude();
        return String.valueOf(latitude);
    }

    @Override
    public String getBval() {
        int longitude = (int) coordinate.getLongitude();
        return String.valueOf(longitude);
    }

    @Override
    public String getText() {
        return String.format("LKS coordinates: X: %s, Y: %s", coordinate.getLatitude(), coordinate.getLongitude());
    }
}
