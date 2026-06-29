package dev.joannagr.quizhub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidad principal de la aplicación.
 * Representa un test almacenado en la base de datos.
 */
@Entity
@Table(name = "tests")
public class Test {

    // ====================
    // Atributos
    // ====================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 5, max = 255, message = "La descripción debe tener entre 5 y 255 caracteres")
    @Column(nullable = false, length = 255)
    private String descripcion;

    @NotBlank(message = "El autor es obligatorio")
    @Size(min = 2, max = 50, message = "El autor debe tener entre 2 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String autor;

    @NotBlank(message = "La pregunta es obligatoria")
    @Size(min = 5, max = 255, message = "La pregunta debe tener entre 5 y 255 caracteres")
    @Column(nullable = false, length = 255)
    private String pregunta;

    @NotBlank(message = "La respuesta correcta es obligatoria")
    @Size(min = 1, max = 255, message = "La respuesta correcta debe tener entre 1 y 255 caracteres")
    @Column(nullable = false, length = 255)
    private String respuestaCorrecta;

    // ====================
    // Constructores
    // ====================

    /**
     * Constructor vacío requerido por JPA.
     */
    public Test() {
    }

    /**
     * Constructor para crear un test.
     */
    public Test(String titulo,
                String descripcion,
                String autor,
                String pregunta,
                String respuestaCorrecta) {

        this.titulo = titulo;
        this.descripcion = descripcion;
        this.autor = autor;
        this.pregunta = pregunta;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    // ====================
    // Getters y Setters
    // ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    // ====================
    // Métodos
    // ====================

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", autor='" + autor + '\'' +
                ", pregunta='" + pregunta + '\'' +
                ", respuestaCorrecta='" + respuestaCorrecta + '\'' +
                '}';
    }
}