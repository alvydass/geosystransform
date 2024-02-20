package com.geosystem.transform.converter.model;

import com.vaadin.flow.server.StreamResource;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ConversionResult {

    StreamResource streamResource;
    long totalRecordCount;
    long convertedCount;
    long failedCount;
}
