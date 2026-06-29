package dev.joannagr.quizhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador encargado de gestionar la página principal
 * de la aplicación.
 */
@Controller
public class HomeController {

    /**
     * Muestra la página de inicio.
     *
     * @return Vista principal de la aplicación.
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }
}