package com.geosystem.transform;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Theme(value = "geosystransform")
@SpringBootApplication
public class TransformApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(TransformApplication.class, args);
	}

}
