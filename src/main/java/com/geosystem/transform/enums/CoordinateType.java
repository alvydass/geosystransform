package com.geosystem.transform.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CoordinateType {

    WGS("EPSG:4326"),
    LKS("EPSG:3346"),
    NAD("EPSG:4269"),
    ETRS("EPSG:4258"),
    SIRGAS("EPSG:4674"),
    GDA("EPSG:7844"),
    JGD("EPSG:6668"),
    PZ90("EPSG:4200"),
    CGCS2019("EPSG:4490"),
    HARTEBEESTHOEK("EPSG:4148");


    @Getter
    private final String code;

    CoordinateType(String code) {
        this.code = code;
    }

    public static List<String> getCoordinateSystemNames() {
        return Arrays.stream(values()).map(Enum::name).collect(Collectors.toList());
    }
}
