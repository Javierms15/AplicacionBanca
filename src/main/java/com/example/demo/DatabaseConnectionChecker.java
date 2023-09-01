package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseConnectionChecker implements ApplicationRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            // La conexión se abrió correctamente, la base de datos está disponible
        } catch (SQLException ex) {
            // Manejo del error de conexión a la base de datos al inicio
            if (ex.getMessage().contains("max_user_connections")) {
                // Redirige a la página HTML personalizada en caso de error "max user connections"
                throw new MaxUserConnectionsException("Error de max user connections al inicio de la aplicación.");
            } else {
                // Otros errores de conexión a la base de datos al inicio
                throw new DatabaseConnectionException("Error de conexión a la base de datos al inicio de la aplicación.");
            }
        }
    }

    @Bean
    public RedirectView redirectToErrorPage() {
        return new RedirectView("/error");
    }

}
