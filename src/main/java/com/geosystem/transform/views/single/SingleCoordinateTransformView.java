package com.geosystem.transform.views.single;

import com.geosystem.transform.converter.model.Response;
import com.geosystem.transform.converter.CoordinateConverter;
import com.geosystem.transform.enums.CoordinateType;
import com.geosystem.transform.views.main.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Single Coordinate Converter")
public class SingleCoordinateTransformView extends VerticalLayout {

    private final CoordinateConverter converter;

    private TextField latCoordinateInput = new TextField("Enter lat coordinate:");
    private TextField lonCoordinateInput = new TextField("Enter lon coordinate:");

    private ComboBox<String> inputType = new ComboBox<>("Convert from:");

    private ComboBox<String> destinationType = new ComboBox<>("Convert to:");

    private Button convertButton = new Button("Convert");

    private Text output =  new Text("");

    public SingleCoordinateTransformView() {
        converter = new CoordinateConverter();
        H1 logo = new H1("Single Coordinate Converter");
        addClassName("single-coordinate-view");
        setDefaultHorizontalComponentAlignment(Alignment.AUTO);

        HorizontalLayout inputLayout = new HorizontalLayout();
        inputLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        convertButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        inputType.setItems(CoordinateType.WGS.name(), CoordinateType.LKS.name());
        destinationType.setItems(CoordinateType.WGS.name(), CoordinateType.LKS.name());
        inputLayout.add(inputType, latCoordinateInput, lonCoordinateInput, destinationType, convertButton);

        HorizontalLayout outputLayout = new HorizontalLayout();
        outputLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        outputLayout.add(new H5("Result: "), output);

        add(logo, inputLayout, outputLayout);
        convertButton.addClickListener(event -> convertCoordinates());
    }

    private void convertCoordinates() {
        String latInput = latCoordinateInput.getValue();
        String lonInput = lonCoordinateInput.getValue();
        CoordinateType inputTypeValue = CoordinateType.valueOf(inputType.getValue());
        CoordinateType destinationTypeValue = CoordinateType.valueOf(destinationType.getValue());

        if (latInput.isEmpty() || lonInput.isEmpty()) {
            Notification.show("Please enter both latitude and longitude coordinates");
            return;
        }

        try {
            double latitude = Double.parseDouble(latInput);
            double longitude = Double.parseDouble(lonInput);

            Response response = converter.convert(latitude, longitude, inputTypeValue, destinationTypeValue);
            output.setText(response.getText());

        } catch (NumberFormatException e) {
            Notification.show("Invalid numeric format for coordinates");
        } catch (Exception e) {
            Notification.show("Error converting coordinates: " + e.getMessage());
        }
    }
}
