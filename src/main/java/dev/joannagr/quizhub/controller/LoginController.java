package dev.joannagr.quizhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Controlador encargado de mostrar
 * la página de inicio de sesión.
 */
@Controller
public class LoginController {

    /*
     * Muestra el formulario de login.
     */
    @GetMapping("/login")
    public String login() {

        return "login";

    }

}