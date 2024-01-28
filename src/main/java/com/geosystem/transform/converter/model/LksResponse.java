package com.geosystem.transform.converter.model;

import lombok.Value;

@Value
public class LksResponse implements Response {

    String aVal;
    String bVal;

    public LksResponse(int aVal, int bVal) {
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
        return String.format("Coordinates converted successfully: X: %s, Y: %s", aVal, bVal);
    }
}
