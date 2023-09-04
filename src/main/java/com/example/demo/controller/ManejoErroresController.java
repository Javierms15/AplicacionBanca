package com.example.demo.controller;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ManejoErroresController {

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ModelAndView handleDatabaseConnectionError(DataAccessResourceFailureException ex) {
        ModelAndView modelAndView = new ModelAndView("error"); // Nombre del archivo HTML personalizado
        modelAndView.addObject("errorMessage", "Error de conexión a la base de datos. Inténtalo de nuevo más tarde.");
        return modelAndView;
    }

    /*
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleDatabaseConnectionError() {
        ModelAndView modelAndView = new ModelAndView("error"); // Nombre del archivo HTML personalizado
        modelAndView.addObject("errorMessage", "Error de conexión a la base de datos al inicio de la aplicación. Inténtalo de nuevo más tarde.");
        return modelAndView;
    }
*/

}
