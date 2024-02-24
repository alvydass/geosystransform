package com.geosystem.transform.views.editor;

import com.geosystem.transform.views.main.MainLayoutView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.server.VaadinSession;
import org.locationtech.jts.geom.Coordinate;
import org.vaadin.addons.maplibre.MapLibre;
import org.vaadin.addons.maplibre.Marker;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
    private List<Marker> markers = new ArrayList<>();

    private Button clearMarkersButton = new Button("Clear Markers");


    public CoordinatesEditorView() throws URISyntaxException {
        H1 logo = new H1("Coordinates editor");
        addClassName("coordinates-editor-view");
        setDefaultHorizontalComponentAlignment(Alignment.AUTO);
        upload.setAcceptedFileTypes("text/csv", "application/json");
        upload.setMaxFiles(1);

        MapLibre map = new MapLibre(new URI("https://demotiles.maplibre.org/style.json"));
        map.setHeight("800px");
        map.setWidth("100%");
        map.setCenter(25.282911, 54.687046);
        map.setZoomLevel(1);
        map.addMapClickListener(listener -> {
            Coordinate point = listener.getPoint();
            copyCoordinatesToClipboard(point);
            updateCoordinatesSpan(point);
        });

        HorizontalLayout fileAndMarker = new HorizontalLayout();
        latInput.setPlaceholder("Example: 25.282911" );
        lonInput.setPlaceholder("Example: 54.687046");
        HorizontalLayout customMarkerPanel = new HorizontalLayout();
        customMarkerPanel.setDefaultVerticalComponentAlignment(Alignment.AUTO);
        customMarkerPanel.setAlignItems(Alignment.AUTO);
        customMarkerPanel.getStyle().set("border-radius", "10px");
        customMarkerPanel.getStyle().set("border", "2px solid #CCCCCC");
        customMarkerPanel.getStyle().set("padding", "5px");
        customMarkerPanel.addClassName("custom-border");
        updateButton.getStyle().set("margin-top", "36px");
        updateButton.addClickListener(listener -> {
            updateMapMarker(map);
        });
        customMarkerPanel.add(latInput, lonInput, updateButton);
        HorizontalLayout uploadLayout = new HorizontalLayout();
        uploadLayout.setDefaultVerticalComponentAlignment(Alignment.AUTO);
        uploadLayout.setAlignItems(Alignment.AUTO);
        uploadLayout.getStyle().set("border-radius", "10px");
        uploadLayout.getStyle().set("border", "2px solid #CCCCCC");
        uploadLayout.getStyle().set("padding", "5px");
        uploadLayout.addClassName("custom-border");

        addMarkersButton.getStyle().set("margin-top", "36px");
        uploadLayout.add(upload, addMarkersButton);

        HorizontalLayout clearButtonLayout = new HorizontalLayout();
        clearButtonLayout.setDefaultVerticalComponentAlignment(Alignment.AUTO);
        clearButtonLayout.setAlignItems(Alignment.AUTO);
        clearMarkersButton.getStyle().set("margin-top", "44px");
        clearButtonLayout.add(clearMarkersButton);
        fileAndMarker.add(uploadLayout, customMarkerPanel, clearButtonLayout);

        clearMarkersButton.addClickListener(listener -> clearMarkers(map));
        add(logo, fileAndMarker, coordinatesSpan, map);
    }

    private void copyCoordinatesToClipboard(Coordinate coordinate) {
        VaadinSession.getCurrent().getUIs().forEach(ui ->
                ui.access((Command) () -> {
                    String coordinates = coordinate.getX() + ", " + coordinate.getY();
                    ui.getPage().executeJs("navigator.clipboard.writeText($0)", coordinates);
                    Notification.show("Coordinates copied", 1000, Notification.Position.MIDDLE);
                }));
    }

    private void updateCoordinatesSpan(Coordinate coordinate) {
        String coordinates = "Coordinates: " + coordinate.getX() + ", " + coordinate.getY();
        coordinatesSpan.setText(coordinates);
        coordinatesSpan.getStyle().set("font-weight", "bold");
        coordinatesSpan.getStyle().set("font-family", "Arial");
    }

    private void updateMapMarker(MapLibre map) {
        try {
            double latitude = Double.parseDouble(latInput.getValue());
            double longitude = Double.parseDouble(lonInput.getValue());

            Marker marker = map.addMarker(latitude, longitude);
            markers.add(marker);
        } catch (NumberFormatException e) {
            Notification.show("Invalid numeric format for latitude or longitude", 3000, Notification.Position.MIDDLE);
        }
    }

    private void clearMarkers(MapLibre map) {
        markers.forEach(map::removeLayer);
        markers.clear();
    }
}
