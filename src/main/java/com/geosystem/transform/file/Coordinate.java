package com.geosystem.transform.file;

import lombok.Value;

@Value(staticConstructor = "of")
public class Coordinate {
    double latitude;
    double longitude;
}
