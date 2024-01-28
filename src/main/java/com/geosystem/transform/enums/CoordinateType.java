package com.geosystem.transform.enums;

import lombok.Getter;

public enum CoordinateType {

    WGS("EPSG:4326"),
    LKS("EPSG:3346");

    @Getter
    private final String code;

    CoordinateType(String code) {
        this.code = code;
    }
}
