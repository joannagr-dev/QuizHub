package dev.joannagr.quizhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador encargado de mostrar las páginas
 * de error personalizadas de la aplicación.
 */
@Controller
public class ErrorController {

    /**
     * Muestra la página de error 403 (Acceso denegado).
     *
     * @return Vista de error 403.
     */
    @GetMapping("/error/403")
    public String accesoDenegado() {
        return "error/403";
    }

    /**
     * Muestra la página de error 404 (Página no encontrada).
     *
     * @return Vista de error 404.
     */
    @GetMapping("/error/404")
    public String paginaNoEncontrada() {
        return "error/404";
    }
}