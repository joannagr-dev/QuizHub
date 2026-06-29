package dev.joannagr.quizhub.controller;

import dev.joannagr.quizhub.model.Test;
import dev.joannagr.quizhub.service.TestService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador MVC encargado de gestionar las vistas HTML y las
 * operaciones CRUD relacionadas con los tests.
 */
@Controller
@RequestMapping("/tests")
public class TestController {

    // ====================
    // Dependencias
    // ====================

    private final TestService testService;

    // ====================
    // Constructor
    // ====================

    public TestController(TestService testService) {
        this.testService = testService;
    }

    // ====================
    // Listado y búsqueda
    // ====================

    /**
     * Muestra el listado de todos los tests.
     */
    @GetMapping
    public String listarTests(Model model) {

        model.addAttribute("tests", testService.findAll());

        return "tests/lista";
    }

    /**
     * Busca tests por su título.
     */
    @GetMapping("/buscar")
    public String buscarTests(
            @RequestParam(required = false) String titulo,
            Model model
    ) {

        if (titulo == null || titulo.isBlank()) {
            model.addAttribute("tests", testService.findAll());
        } else {
            model.addAttribute("tests", testService.buscarPorTitulo(titulo));
        }

        return "tests/lista";
    }

    // ====================
    // Creación
    // ====================

    /**
     * Muestra el formulario para crear un nuevo test.
     */
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {

        model.addAttribute("test", new Test());

        return "tests/nuevo";
    }

    /**
     * Guarda un nuevo test.
     */
    @PostMapping("/guardar")
    public String guardarTest(
            @Valid @ModelAttribute Test test,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            return "tests/nuevo";
        }

        testService.save(test);

        return "redirect:/tests";
    }

    // ====================
    // Edición
    // ====================

    /**
     * Muestra el formulario de edición.
     */
    @GetMapping("/editar/{id}")
    public String editarTest(
            @PathVariable Long id,
            Model model
    ) {

        Test test = testService.findById(id);

        if (test == null) {
            return "redirect:/tests";
        }

        model.addAttribute("test", test);

        return "tests/editar";
    }

    /**
     * Actualiza un test existente.
     */
    @PostMapping("/actualizar")
    public String actualizarTest(
            @Valid @ModelAttribute Test test,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            return "tests/editar";
        }

        testService.save(test);

        return "redirect:/tests";
    }

    // ====================
    // Eliminación
    // ====================

    /**
     * Elimina un test por su identificador.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarTest(
            @PathVariable Long id
    ) {

        testService.deleteById(id);

        return "redirect:/tests";
    }

    // ====================
    // Realización del test
    // ====================

    /**
     * Muestra el formulario para realizar un test.
     */
    @GetMapping("/realizar/{id}")
    public String realizarTest(
            @PathVariable Long id,
            Model model
    ) {

        Test test = testService.findById(id);

        if (test == null) {
            return "redirect:/tests";
        }

        model.addAttribute("test", test);

        return "tests/realizar";
    }

    /**
     * Comprueba la respuesta enviada por el usuario y muestra el resultado.
     */
    @PostMapping("/resultado")
    public String mostrarResultado(
            @RequestParam Long id,
            @RequestParam String respuestaUsuario,
            Model model
    ) {

        Test test = testService.findById(id);

        if (test == null) {
            return "redirect:/tests";
        }

        boolean correcta = test.getRespuestaCorrecta()
                .trim()
                .equalsIgnoreCase(respuestaUsuario.trim());

        int porcentaje = correcta ? 100 : 0;

        model.addAttribute("test", test);
        model.addAttribute("correcta", correcta);
        model.addAttribute("porcentaje", porcentaje);
        model.addAttribute("respuestaUsuario", respuestaUsuario);

        return "tests/resultado";
    }
}