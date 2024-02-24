package com.geosystem.transform.file.writer;

import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.opencsv.CSVWriter;
import com.vaadin.flow.server.StreamResource;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class CsvWriter implements CoordinateFileWriter {

    @Override
    public StreamResource write(List<CoordinateWrapper> coordinates, String fileNamePart) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            CSVWriter csvWriter = new CSVWriter(writer);

            for (CoordinateWrapper coordinate : coordinates) {
                String[] data = {coordinate.getAval(), coordinate.getBval()};
                csvWriter.writeNext(data);
            }

            csvWriter.close();
            writer.close();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());

            return new StreamResource(fileNamePart + "Coordinates.csv", () -> byteArrayInputStream);
        } catch (IOException e) {
            throw new CoordinateWriteException("Failed to write csv file because " + e.getMessage(), e);
        }
    }
}
