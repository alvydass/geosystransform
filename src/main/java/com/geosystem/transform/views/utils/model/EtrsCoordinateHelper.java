package com.geosystem.transform.views.utils.model;

public class EtrsCoordinateHelper implements InputDataHelper {

    @Override
    public String getFirstCoordinate() {
        return "52.487459670";
    }

    @Override
    public String getSecondCoordinate() {
        return "13.378172195";
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
