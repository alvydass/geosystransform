package com.geosystem.transform.converter;

import com.geosystem.transform.file.Coordinate;
import com.geosystem.transform.file.reader.CoordinateFileReader;
import com.geosystem.transform.file.reader.FileReaderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileConverter {

    private final CoordinateConverter converter;

   public void convert(InputStream fileStream, String fileType) {
       CoordinateFileReader reader = FileReaderFactory.getReader(fileType);
       List<Coordinate> coordinates = reader.read(fileStream);

   }
}
