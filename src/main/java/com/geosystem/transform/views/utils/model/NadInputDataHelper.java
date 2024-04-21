package com.geosystem.transform.views.utils.model;

public class NadInputDataHelper implements InputDataHelper {

    @Override
    public String getFirstCoordinate() {
        return "40.6892201";
    }

    @Override
    public String getSecondCoordinate() {
        return "-73.9791454";
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
