package com.geosystem.transform.file.writer;

import com.geosystem.transform.file.FileType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileWriterFactory {

    public static CoordinateFileWriter getWriter(String fileType) {
        FileType mimeType = FileType.valueOf(fileType);
        switch (mimeType) {
            case CSV: return new CsvWriter();
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }
}
