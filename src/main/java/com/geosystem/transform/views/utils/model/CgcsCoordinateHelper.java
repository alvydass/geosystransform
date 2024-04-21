package com.geosystem.transform.views.utils.model;

public class CgcsCoordinateHelper implements InputDataHelper {
    @Override
    public String getFirstCoordinate() {
        return "39.9042";
    }

    @Override
    public String getSecondCoordinate() {
        return "116.4074";
    }

    @Override
    public String getFirstLabel() {
        return "Enter latitude";
    }

    @Override
    public String getSecondLabel() {
        return "Enter longitude";
    }
}
