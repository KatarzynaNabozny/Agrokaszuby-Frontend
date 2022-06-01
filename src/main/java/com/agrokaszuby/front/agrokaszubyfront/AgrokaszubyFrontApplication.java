package com.agrokaszuby.front.agrokaszubyfront;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class AgrokaszubyFrontApplication extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(AgrokaszubyFrontApplication.class, args);
    }

}