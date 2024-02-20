package com.geosystem.transform.file.writer;

import com.geosystem.transform.file.FileType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileWriterFactory {

    public static CoordinateFileWriter getWriter(String fileName) {
        FileType mimeType = FileType.valueOf(StringUtils.toRootUpperCase(FileNameUtils.getExtension(fileName)));
        switch (mimeType) {
            case CSV: return new CsvWriter();
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileName);
        }
    }
}
