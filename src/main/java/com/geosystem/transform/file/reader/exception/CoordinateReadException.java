package com.geosystem.transform.file.reader.exception;

public class CoordinateReadException extends RuntimeException {
    public CoordinateReadException(String message) {
        super(message);
    }

    public CoordinateReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
