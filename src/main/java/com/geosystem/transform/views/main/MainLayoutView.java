package com.geosystem.transform.views.main;

import com.geosystem.transform.views.editor.CoordinatesEditorView;
import com.geosystem.transform.views.files.MultipleCoordinateConverterView;
import com.geosystem.transform.views.single.SingleCoordinateTransformView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayoutView extends AppLayout {

    public MainLayoutView() {
        addClassName("main-layout-view-app-layout");
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        Image logo = new Image("images/globe.png", "Globe");
        logo.setHeight("40px");
        logo.addClassName("globe-image");

        H1 h1 = new H1("GeoTransform");
        h1.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM,
                "logo-text");
        H5 h5 = new H5("To add support for new coordinate system contact geotransform@gmail.com");
        h5.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);


        var header = new HorizontalLayout(new DrawerToggle(), logo, h1, h5);
        header.getStyle().set("background-color", "#a6ffd4");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.expand(h1, h5);
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);
    }

    private void createDrawer() {
        VerticalLayout drawerLayout = new VerticalLayout(
                createRouterLink("Coordinate Converter", SingleCoordinateTransformView.class),
                createRouterLink("File Converter", MultipleCoordinateConverterView.class),
                createRouterLink("Interactive Map", CoordinatesEditorView.class)
        );

        drawerLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);

        drawerLayout.addClassName("drawer");
        drawerLayout.setHeight("100%");

        drawerLayout.getChildren().forEach(component -> {
            if (component instanceof RouterLink routerLink) {
                routerLink.getElement().getStyle().set("border", "1px solid black");
                routerLink.getElement().getStyle().set("border-radius", "5px");
                routerLink.getElement().getStyle().set("padding", "10px");
                routerLink.getElement().getStyle().set("background-color", "#a3ffa3");
                routerLink.getElement().getStyle().set("color", "#0a3d08");
                routerLink.getStyle().set("font-family", "arial");
            }
        });

        addToDrawer(drawerLayout);
    }

    private RouterLink createRouterLink(String text, Class<? extends Component> target) {
        RouterLink routerLink = new RouterLink(text, target);
        routerLink.addClassName("menu-link");
        return routerLink;
    }
}
