package com.geosystem.transform.views.single;

import com.geosystem.transform.views.main.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Single Coordinate Converter")
public class SingleCoordinateTransformView extends VerticalLayout {

    TextField coordinateInput = new TextField("Enter coordinate:");

    ComboBox<String> inputType = new ComboBox<>("Convert from:");

    ComboBox<String> destinationType = new ComboBox<>("Convert to:");

    Button convertButton = new Button("Convert");

    public SingleCoordinateTransformView() {
        H1 logo = new H1("Single Coordinate Converter");
        addClassName("single-coordinate-view");
        setDefaultHorizontalComponentAlignment(Alignment.AUTO);

        HorizontalLayout inputLayout = new HorizontalLayout();
        inputLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        inputLayout.add(inputType, coordinateInput, destinationType, convertButton);

        HorizontalLayout outputLayout = new HorizontalLayout();
        outputLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        outputLayout.add(new H5("Result: "), new Text("GeneratedResult"));

        add(logo, inputLayout, outputLayout);
    }
}
