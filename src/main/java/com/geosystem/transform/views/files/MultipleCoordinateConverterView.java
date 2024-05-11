package com.geosystem.transform.views.files;

import com.geosystem.transform.converter.FileConverter;
import com.geosystem.transform.enums.CoordinateType;
import com.geosystem.transform.file.FileType;
import com.geosystem.transform.views.main.MainLayoutView;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
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
import org.jetbrains.annotations.NotNull;

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

    private Accordion accordion = new Accordion();

    public MultipleCoordinateConverterView(FileConverter fileConverter) {

        H1 logo = new H1("File Coordinate Converter");
        addClassName("multiple-coordinate-view");

        inputType.setItems(CoordinateType.getCoordinateSystemNames());
        destinationType.setItems(CoordinateType.getCoordinateSystemNames());
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
            if (isAnyInputEmpty()) {
                markEmptyComponents();
                Notification notification = new Notification("Please fill mandatory fields (marked red)", 5000, Notification.Position.MIDDLE);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
                return;
            }
            clearInputErrors();
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

        VerticalLayout kmlLayout = getKmlCOntentExample();

        VerticalLayout jsonLayout = getJsonCOntentExample();

        accordion.add("Kml format example", kmlLayout);
        accordion.add("Json format example", jsonLayout);
        accordion.close();
        add(logo, upload, typesLayout, sameFileTypeLayout, convertButton, downloadLink, accordion);
    }

    @NotNull
    public static VerticalLayout getJsonCOntentExample() {
        Div jsonContentDiv = new Div();
        jsonContentDiv.getElement().setProperty("innerHTML",
                "{<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;\"type\": \"FeatureCollection\",<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;\"features\": [<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"type\": \"Feature\",<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"properties\": {},<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"geometry\": {<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"type\": \"Point\",<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"coordinates\": [<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;25.282911,<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;54.687046<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;]<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;]<br>" +
                        "}"
        );

        VerticalLayout jsonLayout = new VerticalLayout(jsonContentDiv);
        jsonLayout.setSpacing(false);
        jsonLayout.setPadding(false);
        return jsonLayout;
    }

    @NotNull
    public static VerticalLayout getKmlCOntentExample() {
        Div kmlContentDiv = new Div();
        kmlContentDiv.getElement().setProperty("innerHTML",
                "&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;<br>" +
                        "&lt;kml xmlns=\"http://www.opengis.net/kml/2.2\"&gt;<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&lt;Document&gt;<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Placemark&gt;<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;Point&gt;<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;coordinates&gt;25.282911,54.687046&lt;/coordinates&gt;<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/Point&gt;<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/Placemark&gt;<br>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;&lt;/Document&gt;<br>" +
                        "&lt;/kml&gt;"
        );
        VerticalLayout kmlLayout = new VerticalLayout(kmlContentDiv);
        kmlLayout.setSpacing(false);
        kmlLayout.setPadding(false);
        return kmlLayout;
    }

    private void markEmptyComponents() {
        if (Objects.isNull(buffer.getFileData())) {
            upload.getElement().executeJs("this.classList.add('invalid-upload')");
        }
        if (Objects.isNull(inputType.getValue())) {
            inputType.setInvalid(true);
        }
        if (Objects.isNull(destinationType.getValue())) {
            destinationType.setInvalid(true);
        }
        if (!validFileTypeSelect()) {
            destinationFileType.setInvalid(true);
        }
    }

    private boolean isAnyInputEmpty() {
        return  Objects.isNull(inputType.getValue()) ||
                Objects.isNull(destinationType.getValue()) ||
                Objects.isNull(buffer.getFileData()) ||
                !validFileTypeSelect();
    }

    private boolean validFileTypeSelect() {
        if (Boolean.TRUE.equals(sameFileType.getValue())) {
            return false;
        }
        return Objects.nonNull(destinationFileType.getValue());
    }

    private void clearInputErrors() {

        upload.getElement().executeJs("this.classList.remove('invalid-upload')");

        destinationFileType.setInvalid(false);

        inputType.setInvalid(false);

        destinationType.setInvalid(false);

    }
}
