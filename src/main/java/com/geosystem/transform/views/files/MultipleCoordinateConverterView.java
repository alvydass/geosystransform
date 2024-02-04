package com.geosystem.transform.views.files;

import com.geosystem.transform.converter.FileConverter;
import com.geosystem.transform.views.main.MainLayoutView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

@Route(value = "multiple", layout = MainLayoutView.class)
@PageTitle("Multiple Coordinates Converter")
public class MultipleCoordinateConverterView extends VerticalLayout {

    private final MemoryBuffer buffer = new MemoryBuffer();
    private final Upload upload = new Upload(buffer);
    private final Button convertButton = new Button("Convert");

    private final FileConverter fileConverter;
    public MultipleCoordinateConverterView(FileConverter fileConverter) {
        this.fileConverter = fileConverter;
        H1 logo = new H1("Multiple Coordinates Converter");
        addClassName("multiple-coordinate-view");
        setDefaultHorizontalComponentAlignment(Alignment.AUTO);

        upload.setAcceptedFileTypes("text/csv");
        upload.setMaxFiles(1);

        // Handle the conversion button click
        convertButton.addClickListener(event -> {
            InputStream fileStream = buffer.getInputStream();
            String fileName = buffer.getFileName();

            //String fileType = event.getMIMEType();

            // Perform conversion based on the file type
            fileConverter.convert(fileStream, fileName);
        });

        // Add components to the layout
        add(logo, upload, convertButton);
    }
}
