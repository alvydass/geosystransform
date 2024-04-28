package com.geosystem.transform.file.reader;

import com.geosystem.transform.file.Coordinate;
import com.geosystem.transform.file.reader.CoordinateFileReader;
import com.geosystem.transform.file.reader.exception.CoordinateReadException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CsvReader implements CoordinateFileReader {

    @Override
    public List<Coordinate> read(InputStream fileStream) {
        List<Coordinate> coordinates = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(fileStream))) {
            List<String[]> lines = reader.readAll();
            for (String[] parts : lines) {
                double latitude = Double.parseDouble(parts[0]);
                double longitude = Double.parseDouble(parts[1]);
                coordinates.add(Coordinate.of(latitude, longitude));
            }
        } catch (IOException | CsvException | NumberFormatException e) {
            throw new CoordinateReadException("Failed to read csv file because " + e.getMessage(), e);
        }

        return coordinates;
    }
}
