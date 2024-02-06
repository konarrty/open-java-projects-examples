package com.example.client;

import com.example.client.forms.Main;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {

        var ctx = new SpringApplicationBuilder(ClientApplication.class)
                .headless(false).web(WebApplicationType.NONE).run(args);

        EventQueue.invokeLater(() -> {

            var ex = ctx.getBean(Main.class);

            ex.setSize(700, 300);
            ex.setTitle("Application");
            ex.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            ex.setVisible(true);

        });
    }
}
