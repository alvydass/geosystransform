package com.geosystem.transform.views.main;

import com.geosystem.transform.views.editor.CoordinatesEditorView;
import com.geosystem.transform.views.files.MultipleCoordinateConverterView;
import com.geosystem.transform.views.single.SingleCoordinateTransformView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayoutView extends AppLayout {

    public MainLayoutView() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        Image logo = new Image("images/globe.png", "Globe");
        logo.setHeight("40px");
        logo.addClassName("globe-image");

        H1 h1 = new H1("GeoSysTransform");
        h1.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM,
                "logo-text");


        var header = new HorizontalLayout(new DrawerToggle(), logo, h1);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.expand(h1);
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);
    }

    private void createDrawer() {
        addToDrawer(new VerticalLayout(
                new RouterLink("Single Coordinate Converter", SingleCoordinateTransformView.class),
                new RouterLink("Multiple Coordinates Converter", MultipleCoordinateConverterView.class),
                new RouterLink("Coordinates editor", CoordinatesEditorView.class)
        ));
    }
}
