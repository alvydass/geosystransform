package com.geosystem.transform.file.writer;

public class CoordinateWriteException extends RuntimeException {

    public CoordinateWriteException(String message) {
        super(message);
    }

    public CoordinateWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
