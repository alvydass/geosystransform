package com.geosystem.transform.file;

import lombok.Getter;

public enum FileType {
    CSV("csv"),
    JSON("json");

    @Getter
    private final String fileType;

    FileType(String mimeType) {
        this.fileType = mimeType;
    }

    public String getFileType() {
        return fileType;
    }
}
