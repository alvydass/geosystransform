package com.geosystem.transform.converter.model;

import lombok.Value;

@Value
public class WgsCoordinate implements CoordinateWrapper {

    String aVal;
    String bVal;

    public WgsCoordinate(double aVal, double bVal) {
        this.aVal = String.valueOf(aVal);
        this.bVal = String.valueOf(bVal);
    }
    @Override
    public String getAval() {
        return aVal;
    }

    @Override
    public String getBval() {
        return bVal;
    }

    @Override
    public String getText() {
        return String.format("WGS coordinates: X: %s, Y: %s", aVal, bVal);
    }
}
