package com.geosystem.transform.views.editor;

import com.geosystem.transform.views.main.MainLayoutView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.locationtech.jts.geom.Coordinate;
import org.vaadin.addons.maplibre.MapLibre;

import java.net.URI;
import java.net.URISyntaxException;

@Route(value = "editor", layout = MainLayoutView.class)
@PageTitle("Interactive Map")
public class CoordinatesEditorView extends VerticalLayout {

    public CoordinatesEditorView() throws URISyntaxException {
        H1 logo = new H1("Coordinates editor");
        addClassName("coordinates-editor-view");
        setDefaultHorizontalComponentAlignment(Alignment.AUTO);

        MapLibre map = new MapLibre(new URI("https://demotiles.maplibre.org/style.json"));
        map.setHeight("800px");
        map.setWidth("100%");
        map.setCenter(25.282911, 54.687046);
        map.setZoomLevel(3);
        map.addMapClickListener(listener -> {
            Coordinate point = listener.getPoint();
            Notification.show("Coordfinate point pressed: " + point.getX() + ", " + point.getY(), 3000, Notification.Position.MIDDLE);
        });

        map.addMarker(25.282911, 54.687046);
        add(logo, map);
    }


}
