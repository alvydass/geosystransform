package com.geosystem.transform.views.single;

import com.geosystem.transform.converter.model.CoordinateWrapper;
import com.geosystem.transform.converter.CoordinateConverter;
import com.geosystem.transform.enums.CoordinateType;
import com.geosystem.transform.views.main.MainLayoutView;
import com.geosystem.transform.views.utils.model.InputDataHelper;
import com.geosystem.transform.views.utils.model.InputDataHelperFactory;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayoutView.class)
@PageTitle("Coordinate Converter")
public class SingleCoordinateTransformView extends VerticalLayout {

    private final CoordinateConverter converter;

    private TextField latCoordinateInput = new TextField("Enter coordinate:");
    private TextField lonCoordinateInput = new TextField("Enter coordinate:");

    private ComboBox<String> inputType = new ComboBox<>("Convert from:");

    private ComboBox<String> destinationType = new ComboBox<>("Convert to:");

    private Button convertButton = new Button("Convert");

    private Text output =  new Text("");

    public SingleCoordinateTransformView() {
        converter = new CoordinateConverter();
        H1 logo = new H1("Coordinate Converter");
        addClassName("single-coordinate-view");
        setDefaultHorizontalComponentAlignment(Alignment.AUTO);

        HorizontalLayout inputLayout = new HorizontalLayout();
        inputLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        convertButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        inputType.setItems(CoordinateType.WGS.name(), CoordinateType.LKS.name());
        inputType.addValueChangeListener(event -> updatePlaceholders());
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


        if (isAnyInputEmpty(latInput, lonInput)) {
            markEmptyComponents(latInput, lonInput);
            Notification notification = new Notification("Coordinates and input - output coordinate systems are mandatory", 5000, Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
            return;
        }
        clearInputErrors();
        CoordinateType inputTypeValue = CoordinateType.valueOf(inputType.getValue());
        CoordinateType destinationTypeValue = CoordinateType.valueOf(destinationType.getValue());

        try {
            double latitude = Double.parseDouble(latInput);
            double longitude = Double.parseDouble(lonInput);

            CoordinateWrapper coordinateWrapper = converter.convert(latitude, longitude, inputTypeValue, destinationTypeValue);
            output.setText(coordinateWrapper.getText());

        } catch (NumberFormatException e) {
            Notification notification = new Notification("Invalid numeric format for coordinates", 3000, Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        } catch (Exception e) {
            Notification notification = new Notification("Error converting coordinates: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        }
    }

    private void clearInputErrors() {

        latCoordinateInput.setInvalid(false);

        lonCoordinateInput.setInvalid(false);

        inputType.setInvalid(false);

        destinationType.setInvalid(false);

    }

    private void markEmptyComponents(String latInput, String lonInput) {
        if (latInput.isEmpty()) {
            latCoordinateInput.setInvalid(true);
        }
        if (lonInput.isEmpty()) {
            lonCoordinateInput.setInvalid(true);
        }
        if (inputType.getValue().isEmpty()) {
            inputType.setInvalid(true);
        }
        if (destinationType.getValue().isEmpty()) {
            destinationType.setInvalid(true);
        }
    }

    private boolean isAnyInputEmpty(String latInput, String lonInput) {
        return latInput.isEmpty() || lonInput.isEmpty() || inputType.getValue().isEmpty() || destinationType.getValue().isEmpty();
    }

    private void updatePlaceholders() {
        String inputTypeValue = inputType.getValue();
        InputDataHelper helper = InputDataHelperFactory.getHelper(inputTypeValue);


        latCoordinateInput.setPlaceholder("Example: " + helper.getFirstCoordinate());
        lonCoordinateInput.setPlaceholder("Example: " + helper.getSecondCoordinate());
        latCoordinateInput.setLabel(helper.getFirstLabel());
        lonCoordinateInput.setLabel(helper.getSecondLabel());
    }
}
