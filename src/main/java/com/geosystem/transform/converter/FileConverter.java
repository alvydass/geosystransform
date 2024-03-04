package com.geosystem.transform.converter;

import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.geosystem.transform.enums.CoordinateType;
import com.geosystem.transform.file.Coordinate;
import com.geosystem.transform.file.FileType;
import com.geosystem.transform.file.reader.CoordinateFileReader;
import com.geosystem.transform.file.reader.FileReaderFactory;
import com.geosystem.transform.file.writer.CoordinateFileWriter;
import com.geosystem.transform.file.writer.FileWriterFactory;
import com.vaadin.flow.server.StreamResource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileConverter {

    private final CoordinateConverter converter;

   public StreamResource convert(InputStream fileStream, String fileName, CoordinateType from, CoordinateType to, FileType destinationFileType) {
       CoordinateFileReader reader = FileReaderFactory.getReader(fileName);
       List<Coordinate> coordinates = reader.read(fileStream);
       List<CoordinateWrapper> transformedCoordinates = converter.convert(coordinates, from, to);
       CoordinateFileWriter writer = FileWriterFactory.getWriter(destinationFileType);
       return writer.write(transformedCoordinates, to.name().toLowerCase());
   }

    public StreamResource convert(InputStream fileStream, String fileName, CoordinateType from, CoordinateType to) {
        CoordinateFileReader reader = FileReaderFactory.getReader(fileName);
        List<Coordinate> coordinates = reader.read(fileStream);
        List<CoordinateWrapper> transformedCoordinates = converter.convert(coordinates, from, to);
        FileType destinationFileType = FileType.valueOf(StringUtils.toRootUpperCase(FileNameUtils.getExtension(fileName)));
        CoordinateFileWriter writer = FileWriterFactory.getWriter(destinationFileType);
        return writer.write(transformedCoordinates, to.name().toLowerCase());
    }
}
