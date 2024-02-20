package com.geosystem.transform.file.writer;

import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.vaadin.flow.server.StreamResource;

import java.io.OutputStream;
import java.util.List;

public interface CoordinateFileWriter {

    StreamResource write(List<CoordinateWrapper> coordinates, String fileNamePart);
}
