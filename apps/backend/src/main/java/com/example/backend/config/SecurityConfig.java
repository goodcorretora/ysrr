package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; // Importar esta classe
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      // 1. DESABILITAR CSRF COMPLETAMENTE para APIs REST sem estado.
      // Para produção, considere alternativas se houver necessidade de sessão.
      .csrf(csrf -> csrf.disable())
      // 2. Adicionar o configurador CORS ANTES das regras de autorização,
      // ou garantir que o CORS seja configurado globalmente de forma a não conflitar.
      .cors(cors -> {}) // Usa o CorsConfigurationSource padrão (definido pelo corsConfigurer Bean)
      // 3. Configurar as regras de autorização
      .authorizeHttpRequests(authorize -> authorize
        // Permitir requisições OPTIONS para preflight CORS em qualquer caminho
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        // Permitir acesso público aos endpoints de cadastro e verificação
        .requestMatchers(AntPathRequestMatcher.antMatcher("/usuarios/cadastro")).permitAll()
        .requestMatchers(AntPathRequestMatcher.antMatcher("/usuarios/login")).permitAll()
        .requestMatchers(AntPathRequestMatcher.antMatcher("/usuarios/verificar-email")).permitAll()
        .requestMatchers(AntPathRequestMatcher.antMatcher("/usuarios/verificar-telefone")).permitAll()
        .requestMatchers(AntPathRequestMatcher.antMatcher("/error")).permitAll() // Endpoint de erro padrão
        // Qualquer outra requisição exige autenticação
        .anyRequest().authenticated()
      );

    return http.build();
  }

  // Bean para configuração global de CORS
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica a todos os endpoints
          .allowedOrigins("http://localhost:4200", "http://seu-dominio-frontend.com") // <--- AJUSTE AQUI PARA O SEU FRONTEND
          .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
          .allowedHeaders("*")
          .allowCredentials(true);
      }
    };
  }
}
