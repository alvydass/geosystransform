package com.geosystem.transform.views.files;

import com.geosystem.transform.converter.FileConverter;
import com.geosystem.transform.enums.CoordinateType;
import com.geosystem.transform.file.FileType;
import com.geosystem.transform.views.main.MainLayoutView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import java.io.InputStream;
import java.util.Objects;

@Route(value = "multiple", layout = MainLayoutView.class)
@PageTitle("Multiple Coordinates Converter")
public class MultipleCoordinateConverterView extends VerticalLayout {

    private final MemoryBuffer buffer = new MemoryBuffer();
    private final Upload upload = new Upload(buffer);
    private final Button convertButton = new Button("Convert");
    private final Anchor downloadLink = new Anchor();

    private ComboBox<String> inputType = new ComboBox<>("Convert from:");
    private ComboBox<String> destinationType = new ComboBox<>("Convert to:");

    private ComboBox<String> destinationFileType = new ComboBox<>("Convert to file type:");

    Checkbox sameFileType = new Checkbox("Convert to same file type");

    private Button downloadButton =  new Button("Download file", new Icon(VaadinIcon.DOWNLOAD));

    private final FileConverter fileConverter;

    public MultipleCoordinateConverterView(FileConverter fileConverter) {
        this.fileConverter = fileConverter;

        H1 logo = new H1("File Coordinate Converter");
        addClassName("multiple-coordinate-view");

        inputType.setItems(CoordinateType.WGS.name(), CoordinateType.LKS.name());
        destinationType.setItems(CoordinateType.WGS.name(), CoordinateType.LKS.name());
        destinationFileType.setItems(FileType.CSV.name(), FileType.KML.name(), FileType.JSON.name());

        HorizontalLayout typesLayout = new HorizontalLayout();
        typesLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        typesLayout.add(inputType, destinationType);

        setDefaultHorizontalComponentAlignment(Alignment.AUTO);

        HorizontalLayout sameFileTypeLayout = new HorizontalLayout();
        sameFileTypeLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        sameFileTypeLayout.add(destinationFileType, sameFileType);

        //upload.setAcceptedFileTypes("text/csv", "application/json", "application/vnd.google-earth.kml+xml");
        upload.setMaxFiles(1);
        downloadButton.setVisible(false);
        downloadLink.add(downloadButton);

        sameFileType.addValueChangeListener(event -> {
            destinationFileType.setEnabled(!event.getValue());
        });

        convertButton.addClickListener(event -> {
            downloadButton.setVisible(false);
            InputStream fileStream = buffer.getInputStream();
            String fileName = buffer.getFileName();

            CoordinateType inputTypeValue = CoordinateType.valueOf(inputType.getValue());
            CoordinateType destinationTypeValue = CoordinateType.valueOf(destinationType.getValue());

            StreamResource streamResource;
            try {
                if (sameFileType.getValue()) {
                    streamResource = fileConverter.convert(fileStream, fileName, inputTypeValue, destinationTypeValue);
                } else {
                    FileType fileType = FileType.valueOf(destinationFileType.getValue());
                    streamResource = fileConverter.convert(fileStream, fileName, inputTypeValue, destinationTypeValue, fileType);
                }
            } catch (Exception exc) {
                Notification notification = new Notification("Conversion failed. Reason: " + exc.getMessage(), 5000, Notification.Position.MIDDLE);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
                return;
            }

            downloadLink.setHref(streamResource);
            downloadLink.getElement().setAttribute("download", true);

            downloadButton.setVisible(true);
            downloadLink.setEnabled(true);
        });

        add(logo, upload, typesLayout, sameFileTypeLayout, convertButton, downloadLink);
    }
}
