package com.geosystem.transform.views.editor;

import com.geosystem.transform.views.main.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "editor", layout = MainLayout.class)
@PageTitle("Coordinates editor")
public class CoordinatesEditorView extends VerticalLayout {

    public CoordinatesEditorView() {
        H1 logo = new H1("Coordinates editor");
        addClassName("coordinates-editor-view");
        setDefaultHorizontalComponentAlignment(Alignment.AUTO);

        Upload upload = new Upload();
        upload.setUploadButton(new Button("Upload a File"));
        upload.setAcceptedFileTypes("*/*");

        add(logo, upload);
    }
}
