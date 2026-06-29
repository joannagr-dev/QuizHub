package dev.joannagr.quizhub.service;

import dev.joannagr.quizhub.model.Test;
import dev.joannagr.quizhub.repository.TestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de la lógica de negocio relacionada con los tests.
 *
 * Actúa como intermediario entre los controladores y el repositorio,
 * gestionando las operaciones sobre la entidad Test.
 */
@Service
public class TestService {

    // ====================
    // Dependencias
    // ====================

    private final TestRepository testRepository;

    // ====================
    // Constructor
    // ====================

    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    // ====================
    // Operaciones CRUD
    // ====================

    /**
     * Guarda un nuevo test o actualiza uno existente.
     *
     * @param test Test a guardar.
     * @return Test almacenado.
     */
    @Transactional
    public Test save(Test test) {
        return testRepository.save(test);
    }

    /**
     * Obtiene todos los tests ordenados alfabéticamente por título.
     *
     * @return Lista de tests.
     */
    public List<Test> findAll() {
        return testRepository.findAllByOrderByTituloAsc();
    }

    /**
     * Busca tests cuyo título contenga el texto indicado.
     *
     * @param titulo Texto a buscar.
     * @return Lista de tests encontrados.
     */
    public List<Test> buscarPorTitulo(String titulo) {
        return testRepository.findByTituloContainingIgnoreCase(titulo);
    }

    /**
     * Busca un test por su identificador.
     *
     * @param id Identificador del test.
     * @return Test encontrado o null si no existe.
     */
    public Test findById(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    /**
     * Elimina un test por su identificador si existe.
     *
     * @param id Identificador del test.
     */
    @Transactional
    public void deleteById(Long id) {
        if (testRepository.existsById(id)) {
            testRepository.deleteById(id);
        }
    }
}