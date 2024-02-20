package com.geosystem.transform.views.utils.model;

public class WgsInputDataHelper implements InputDataHelper {

    @Override
    public String getFirstCoordinate() {
        return "54.687046";
    }

    @Override
    public String getSecondCoordinate() {
        return "25.282911";
    }

    @Override
    public String getFirstLabel() {
        return "Enter lat coordinate";
    }

    @Override
    public String getSecondLabel() {
        return "Enter lon coordinate";
    }
}
