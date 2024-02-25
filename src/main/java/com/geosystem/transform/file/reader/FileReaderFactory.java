package com.geosystem.transform.file.reader;

import com.geosystem.transform.file.FileType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileReaderFactory {

    public static CoordinateFileReader getReader(String fileName) {
        FileType mimeType = FileType.valueOf(StringUtils.toRootUpperCase(FileNameUtils.getExtension(fileName)));
        switch (mimeType) {
            case CSV: return new CsvReader();
            case JSON: return new GeoJsonReader();
            case KML: return new KmlReader();
            default:
                throw new IllegalArgumentException("Unsupported file type for file: " + fileName);
        }
    }
}
