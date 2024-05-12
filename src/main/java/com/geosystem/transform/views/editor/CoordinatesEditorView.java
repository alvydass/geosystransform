package com.geosystem.transform.views.editor;

import com.geosystem.transform.converter.CoordinateConverter;
import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.geosystem.transform.enums.CoordinateType;
import com.geosystem.transform.file.reader.CoordinateFileReader;
import com.geosystem.transform.file.reader.FileReaderFactory;
import com.geosystem.transform.views.main.MainLayoutView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.locationtech.jts.geom.Coordinate;
import org.vaadin.addons.maplibre.MapLibre;
import org.vaadin.addons.maplibre.Marker;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.geosystem.transform.views.files.MultipleCoordinateConverterView.getJsonCOntentExample;
import static com.geosystem.transform.views.files.MultipleCoordinateConverterView.getKmlCOntentExample;

@Route(value = "editor", layout = MainLayoutView.class)
@PageTitle("Interactive Map")
public class CoordinatesEditorView extends VerticalLayout {

    private Span coordinatesSpan = new Span("Coordinates: N/A");

    private final MemoryBuffer buffer = new MemoryBuffer();
    private final Upload upload = new Upload(buffer);

    private TextField latInput = new TextField("Latitude");
    private TextField lonInput = new TextField("Longitude");
    private Button updateButton = new Button("Add Marker");

    private Button addMarkersButton = new Button("Add Markers");
    private Set<Marker> markers = new HashSet<>();

    private Button clearMarkersButton = new Button("Clear Markers");

    private ComboBox<String> inputType = new ComboBox<>("Coordinate Type");

    private final CoordinateConverter converter;


    public CoordinatesEditorView(CoordinateConverter converter) throws URISyntaxException {
        this.converter = converter;
        H1 logo = new H1("Interactive map");
        addClassName("coordinates-editor-view");
        setDefaultHorizontalComponentAlignment(Alignment.AUTO);
        upload.setAcceptedFileTypes("text/csv");
        upload.setMaxFiles(1);

        MapLibre map = new MapLibre(new URI("https://api.maptiler.com/maps/streets/style.json?key=klhvW36HyHrDRHEaCYeH"));
        map.setHeight("800px");
        map.setWidth("100%");
        map.setZoomLevel(3);
        map.setCenter( 25.295217911869514,  54.684368985374704);
        map.addMapClickListener(listener -> {
            Coordinate point = listener.getPoint();
            updateCoordinatesSpan(point);
        });

        HorizontalLayout fileAndMarker = new HorizontalLayout();
        latInput.setPlaceholder("Example: 25.282911" );
        lonInput.setPlaceholder("Example: 54.687046");
        HorizontalLayout customMarkerPanel = getUploadFileLayout();
        updateButton.getStyle().set("margin-top", "36px");
        updateButton.addClickListener(listener -> {
            addMapMarker(map);
        });
        customMarkerPanel.add(latInput, lonInput, updateButton);
        HorizontalLayout uploadLayout = getUploadFileLayout();

        inputType.setItems(CoordinateType.getCoordinateSystemNames());
        addMarkersButton.getStyle().set("margin-top", "36px");
        addMarkersButtonListener(map);
        uploadLayout.add(upload, inputType, addMarkersButton);

        HorizontalLayout clearButtonLayout = getClearMarkersButtonLayout();
        fileAndMarker.add(uploadLayout, customMarkerPanel, clearButtonLayout);

        clearMarkersButton.getStyle().set("margin-top", "44px");
        clearMarkersButton.addClickListener(listener -> clearMarkers(map));
        Paragraph text = new Paragraph("Supported file formats: CSV");
        text.getStyle().set("font-size", "13px");
        add(logo, fileAndMarker, text,  coordinatesSpan, map);
    }

    private void addMarkersButtonListener(MapLibre map) {
        addMarkersButton.addClickListener(listener -> {
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
            if (CoordinateType.WGS.equals(inputTypeValue)) {
                CoordinateFileReader reader = FileReaderFactory.getReader(fileName);
                List<com.geosystem.transform.file.Coordinate> coordinates = reader.read(fileStream);
                addMapMarkers(coordinates, map);
            } else {
                CoordinateFileReader reader = FileReaderFactory.getReader(fileName);
                List<com.geosystem.transform.file.Coordinate> coordinates = reader.read(fileStream);
                List<CoordinateWrapper> transformedCoordinates = converter.convert(coordinates, inputTypeValue, CoordinateType.WGS);
                List<com.geosystem.transform.file.Coordinate> wgsCoordinates = mapCoordinates(transformedCoordinates);
                addMapMarkers(wgsCoordinates, map);
            }
        });
    }

    private List<com.geosystem.transform.file.Coordinate> mapCoordinates(List<CoordinateWrapper> transformedCoordinates) {
        return transformedCoordinates.stream()
                .map(tc -> com.geosystem.transform.file.Coordinate.of(Double.parseDouble(tc.getAval()), Double.parseDouble(tc.getBval())))
                .toList();
    }

    private static HorizontalLayout getUploadFileLayout() {
        HorizontalLayout uploadLayout = new HorizontalLayout();
        uploadLayout.setDefaultVerticalComponentAlignment(Alignment.AUTO);
        uploadLayout.setAlignItems(Alignment.AUTO);
        uploadLayout.getStyle().set("border-radius", "10px");
        uploadLayout.getStyle().set("border", "2px solid #CCCCCC");
        uploadLayout.getStyle().set("padding", "5px");
        uploadLayout.addClassName("custom-border");
        return uploadLayout;
    }

    private HorizontalLayout getClearMarkersButtonLayout() {
        HorizontalLayout clearButtonLayout = new HorizontalLayout();
        clearButtonLayout.setDefaultVerticalComponentAlignment(Alignment.AUTO);
        clearButtonLayout.setAlignItems(Alignment.AUTO);
        clearButtonLayout.add(clearMarkersButton);
        return clearButtonLayout;
    }

    private void updateCoordinatesSpan(Coordinate coordinate) {
        String coordinates = "Coordinates: " + coordinate.getX() + ", " + coordinate.getY();
        coordinatesSpan.setText(coordinates);
        coordinatesSpan.getStyle().set("font-weight", "bold");
        coordinatesSpan.getStyle().set("font-family", "Arial");
    }

    private void addMapMarker(MapLibre map) {
        if (latInput.isEmpty() || lonInput.isEmpty()) {
            markEmptyComponentsSingleMarker();
            Notification notification = new Notification("Please fill mandatory fields (marked red)", 5000, Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
            return;
        }
        clearEmptyComponentsSingleMarker();
        try {
            double latitude = Double.parseDouble(latInput.getValue());
            double longitude = Double.parseDouble(lonInput.getValue());

            Marker marker = map.addMarker(latitude, longitude);
            marker.withPopup("Lat: " + latitude + " Lon: " + longitude);
            markers.add(marker);
        } catch (NumberFormatException e) {
            Notification.show("Invalid numeric format for latitude or longitude", 3000, Notification.Position.MIDDLE);
        }
    }

    private void clearEmptyComponentsSingleMarker() {
        latInput.setInvalid(false);
        lonInput.setInvalid(false);
    }

    private void addMapMarkers(List<com.geosystem.transform.file.Coordinate> coordinates, MapLibre map) {
        for (com.geosystem.transform.file.Coordinate coordinate : coordinates) {
            Marker marker = map.addMarker(coordinate.getLatitude(), coordinate.getLongitude());
            marker.withPopup("Lat: " + coordinate.getLatitude() + " Lon: " + coordinate.getLongitude());
            markers.add(marker);
        }
    }

    private void clearMarkers(MapLibre map) {
        markers.forEach(map::removeLayer);
        markers.clear();
    }

    private void markEmptyComponentsSingleMarker() {
        if (latInput.isEmpty()) {
            latInput.setInvalid(true);
        }
        if (lonInput.isEmpty()) {
            lonInput.setInvalid(true);
        }
    }

    private void markEmptyComponents() {
        if (Objects.isNull(buffer.getFileData())) {
            upload.getElement().executeJs("this.classList.add('invalid-upload')");
        }
        if (Objects.isNull(inputType.getValue())) {
            inputType.setInvalid(true);
        }
    }

    private boolean isAnyInputEmpty() {
        return  Objects.isNull(inputType.getValue()) ||
                Objects.isNull(buffer.getFileData());
    }

    private void clearInputErrors() {
        upload.getElement().executeJs("this.classList.remove('invalid-upload')");
        inputType.setInvalid(false);
    }
}
