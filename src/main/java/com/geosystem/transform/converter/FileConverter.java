package com.geosystem.transform.converter;

import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.geosystem.transform.enums.CoordinateType;
import com.geosystem.transform.file.Coordinate;
import com.geosystem.transform.file.reader.CoordinateFileReader;
import com.geosystem.transform.file.reader.FileReaderFactory;
import com.geosystem.transform.file.writer.CoordinateFileWriter;
import com.geosystem.transform.file.writer.FileWriterFactory;
import com.vaadin.flow.server.StreamResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileConverter {

    private final CoordinateConverter converter;

   public StreamResource convert(InputStream fileStream, String fileType, CoordinateType from, CoordinateType to) {
       CoordinateFileReader reader = FileReaderFactory.getReader(fileType);
       List<Coordinate> coordinates = reader.read(fileStream);
       List<CoordinateWrapper> transformedCoordinates = converter.convert(coordinates, from, to);
       CoordinateFileWriter writer = FileWriterFactory.getWriter(fileType);
       return writer.write(transformedCoordinates, to.name().toLowerCase());
   }
}
