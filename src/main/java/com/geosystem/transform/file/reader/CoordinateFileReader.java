package com.geosystem.transform.file.reader;

import com.geosystem.transform.file.Coordinate;

import java.io.InputStream;
import java.util.List;

public interface CoordinateFileReader {

    List<Coordinate> read(InputStream fileStream);
}
