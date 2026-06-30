package dev.joannagr.quizhub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad de la aplicación.
 *
 * Define los usuarios, los permisos de acceso, el proceso de autenticación
 * y las reglas de autorización de la aplicación.
 */
@Configuration
public class SecurityConfig {

    // ====================
    // Usuarios
    // ====================

    /**
     * Configura los usuarios en memoria utilizados por la aplicación.
     *
     * Se definen dos usuarios:
     * - USER: acceso a las funcionalidades básicas.
     * - ADMIN: acceso completo, incluida la eliminación de tests.
     */
    @Bean
    public UserDetailsService users(PasswordEncoder passwordEncoder) {

        var user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("1234"))
                .roles("USER")
                .build();

        var admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    // ====================
    // Password Encoder
    // ====================

    /**
     * Codificador de contraseñas utilizado por Spring Security.
     *
     * Se emplea BCrypt para almacenar las contraseñas cifradas.
     *
     * @return Codificador BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ====================
    // Seguridad
    // ====================

    /**
     * Configura las reglas de seguridad de la aplicación.
     *
     * Define:
     * - permisos de acceso a las rutas
     * - autenticación mediante formulario
     * - cierre de sesión
     * - página de error 403
     * - acceso a la consola H2
     *
     * @param http Configuración HTTP de Spring Security.
     * @return Cadena de filtros de seguridad.
     * @throws Exception si se produce un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

                // ====================
                // Autorización
                // ====================

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**",
                                "/tests",
                                "/tests/buscar",
                                "/tests/realizar/**",
                                "/tests/resultado",
                                "/api/**",
                                "/h2-console/**",
                                "/error/**"
                        ).permitAll()

                        .requestMatchers(
                                "/tests/nuevo",
                                "/tests/guardar",
                                "/tests/editar/**",
                                "/tests/actualizar"
                        ).authenticated()

                        .requestMatchers(
                                "/tests/eliminar/**"
                        ).hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                // ====================
                // Login
                // ====================

                .formLogin(form -> form

                        .loginPage("/login")

                        .defaultSuccessUrl("/tests", true)

                        .permitAll()
                )

                // ====================
                // Logout
                // ====================

                .logout(logout -> logout

                        .logoutSuccessUrl("/")

                        .invalidateHttpSession(true)

                        .deleteCookies("JSESSIONID")
                )

                // ====================
                // Gestión de errores
                // ====================

                .exceptionHandling(exception ->
                        exception.accessDeniedPage("/error/403")
                )

                // ====================
                // Configuración adicional
                // ====================

                // CSRF se deshabilita para simplificar el proyecto.
                // En una aplicación de producción normalmente debería permanecer habilitado.
                .csrf(csrf -> csrf.disable())

                // Permite utilizar la consola H2 desde el navegador.
                .headers(headers ->
                        headers.frameOptions(frame -> frame.disable())
                );

        return http.build();
    }
}