package com.geosystem.transform.views.files;

import com.geosystem.transform.views.main.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "multiple", layout = MainLayout.class)
@PageTitle("Multiple Coordinates Converter")
public class MultipleCoordinateConverter extends VerticalLayout {


    public MultipleCoordinateConverter() {
        H1 logo = new H1("Multiple Coordinates Converter");
        addClassName("multiple-coordinate-view");
        setDefaultHorizontalComponentAlignment(Alignment.AUTO);

        Upload upload = new Upload();
        upload.setUploadButton(new Button("Upload a File"));
        upload.setAcceptedFileTypes("*/*");

        add(logo, upload);
    }
}
