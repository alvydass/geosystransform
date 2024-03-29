package com.geosystem.transform.file.writer;

import com.geosystem.transform.file.FileType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileWriterFactory {

    public static CoordinateFileWriter getWriter(FileType fileType) {
        return switch (fileType) {
            case CSV -> new CsvWriter();
            case JSON -> new GeoJsonWriter();
            case KML -> new KmlWriter();
            default -> throw new IllegalArgumentException("Unsupported file type: " + fileType);
        };
    }
}
