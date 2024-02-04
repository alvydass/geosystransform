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
        return String.valueOf(coordinate.getLatitude());
    }

    @Override
    public String getBval() {
        return String.valueOf(coordinate.getLongitude());
    }

    @Override
    public String getText() {
        return String.format("LKS coordinates: X: %s, Y: %s", coordinate.getLatitude(), coordinate.getLongitude());
    }
}
