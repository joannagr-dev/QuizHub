package dev.joannagr.quizhub.repository;

import dev.joannagr.quizhub.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para la gestión de la entidad Test.
 *
 * Proporciona las operaciones CRUD heredadas de JpaRepository
 * y métodos personalizados para la búsqueda y ordenación de tests.
 */
public interface TestRepository extends JpaRepository<Test, Long> {

    /**
     * Busca tests cuyo título contenga el texto indicado,
     * sin distinguir entre mayúsculas y minúsculas.
     */
    List<Test> findByTituloContainingIgnoreCase(String titulo);

    /**
     * Devuelve todos los tests ordenados alfabéticamente por título.
     */
    List<Test> findAllByOrderByTituloAsc();

}