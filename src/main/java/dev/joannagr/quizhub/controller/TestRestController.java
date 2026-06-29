package dev.joannagr.quizhub.controller;

import dev.joannagr.quizhub.model.Test;
import dev.joannagr.quizhub.service.TestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de exponer la API para la gestión de tests.
 *
 * Permite realizar operaciones CRUD mediante peticiones HTTP y devuelve
 * respuestas en formato JSON.
 */
@RestController
@RequestMapping("/api/tests")
public class TestRestController {

    // ====================
    // Dependencias
    // ====================

    private final TestService testService;

    // ====================
    // Constructor
    // ====================

    public TestRestController(TestService testService) {
        this.testService = testService;
    }

    // ====================
    // Consultas (GET)
    // ====================

    /**
     * Obtiene todos los tests.
     *
     * @return Lista de tests.
     */
    @GetMapping
    public ResponseEntity<List<Test>> obtenerTests() {

        List<Test> tests = testService.findAll();

        return ResponseEntity.ok(tests);
    }

    /**
     * Obtiene un test por su identificador.
     *
     * @param id Identificador del test.
     * @return Test encontrado o 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Test> obtenerTest(
            @PathVariable Long id
    ) {

        Test test = testService.findById(id);

        if (test == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(test);
    }

    // ====================
    // Creación (POST)
    // ====================

    /**
     * Crea un nuevo test.
     *
     * @param test Datos del test.
     * @return Test creado.
     */
    @PostMapping
    public ResponseEntity<Test> crearTest(
            @Valid @RequestBody Test test
    ) {

        Test nuevoTest = testService.save(test);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoTest);
    }

    // ====================
    // Actualización (PUT)
    // ====================

    /**
     * Actualiza un test existente.
     *
     * @param id Identificador del test.
     * @param testActualizado Datos actualizados.
     * @return Test actualizado o 404 si no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Test> actualizarTest(
            @PathVariable Long id,
            @Valid @RequestBody Test testActualizado
    ) {

        Test testExistente = testService.findById(id);

        if (testExistente == null) {
            return ResponseEntity.notFound().build();
        }

        testExistente.setTitulo(testActualizado.getTitulo());
        testExistente.setDescripcion(testActualizado.getDescripcion());
        testExistente.setAutor(testActualizado.getAutor());
        testExistente.setPregunta(testActualizado.getPregunta());
        testExistente.setRespuestaCorrecta(testActualizado.getRespuestaCorrecta());

        Test testGuardado = testService.save(testExistente);

        return ResponseEntity.ok(testGuardado);
    }

    // ====================
    // Eliminación (DELETE)
    // ====================

    /**
     * Elimina un test por su identificador.
     *
     * @param id Identificador del test.
     * @return 204 si se elimina correctamente o 404 si no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTest(
            @PathVariable Long id
    ) {

        Test test = testService.findById(id);

        if (test == null) {
            return ResponseEntity.notFound().build();
        }

        testService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}