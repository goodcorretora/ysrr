package com.example.backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name; // Novo campo para o nome do usuário

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String senha;

  @Column(unique = true)
  private String telefone;

  @Column(nullable = false)
  private LocalDateTime dataCadastro;

  private LocalDateTime dataUltimoLogin;

  private Boolean emailVerificado = false;

  private String codigoVerificacaoEmail;

  private LocalDateTime dataExpiracaoCodigoEmail;

  private Boolean telefoneVerificado = false;

  private String codigoVerificacaoTelefone;

  private LocalDateTime dataExpiracaoCodigoTelefone;

  private String tokenResetSenha;

  private LocalDateTime dataExpiracaoTokenSenha;


  // --- Construtores ---

  /**
   * Construtor padrão vazio.
   * Define a data de cadastro automaticamente para o momento da criação.
   */
  public Usuario() {
    this.dataCadastro = LocalDateTime.now();
  }

  /**
   * Construtor com campos essenciais para cadastro.
   *
   * @param name O nome do usuário.
   * @param email O email único do usuário.
   * @param senha A senha do usuário (já criptografada, idealmente).
   * @param telefone O telefone único do usuário (opcional, pode ser null).
   */
  public Usuario(String name, String email, String senha, String telefone) {
    this.name = name;
    this.email = email;
    this.senha = senha;
    this.telefone = telefone;
    this.dataCadastro = LocalDateTime.now(); // Define a data de cadastro automaticamente
  }


  // --- Getters e Setters para cada Field ---

  // Getters
  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getSenha() {
    return senha;
  }

  public String getTelefone() {
    return telefone;
  }

  public LocalDateTime getDataCadastro() {
    return dataCadastro;
  }

  public LocalDateTime getDataUltimoLogin() {
    return dataUltimoLogin;
  }

  public Boolean getEmailVerificado() {
    return emailVerificado;
  }

  public String getCodigoVerificacaoEmail() {
    return codigoVerificacaoEmail;
  }

  public LocalDateTime getDataExpiracaoCodigoEmail() {
    return dataExpiracaoCodigoEmail;
  }

  public Boolean getTelefoneVerificado() {
    return telefoneVerificado;
  }

  public String getCodigoVerificacaoTelefone() {
    return codigoVerificacaoTelefone;
  }

  public LocalDateTime getDataExpiracaoCodigoTelefone() {
    return dataExpiracaoCodigoTelefone;
  }

  public String getTokenResetSenha() {
    return tokenResetSenha;
  }

  public LocalDateTime getDataExpiracaoTokenSenha() {
    return dataExpiracaoTokenSenha;
  }

  // Setters
  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public void setDataCadastro(LocalDateTime dataCadastro) {
    this.dataCadastro = dataCadastro;
  }

  public void setDataUltimoLogin(LocalDateTime dataUltimoLogin) {
    this.dataUltimoLogin = dataUltimoLogin;
  }

  public void setEmailVerificado(Boolean emailVerificado) {
    this.emailVerificado = emailVerificado;
  }

  public void setCodigoVerificacaoEmail(String codigoVerificacaoEmail) {
    this.codigoVerificacaoEmail = codigoVerificacaoEmail;
  }

  public void setDataExpiracaoCodigoEmail(LocalDateTime dataExpiracaoCodigoEmail) {
    this.dataExpiracaoCodigoEmail = dataExpiracaoCodigoEmail;
  }

  public void setTelefoneVerificado(Boolean telefoneVerificado) {
    this.telefoneVerificado = telefoneVerificado;
  }

  public void setCodigoVerificacaoTelefone(String codigoVerificacaoTelefone) {
    this.codigoVerificacaoTelefone = codigoVerificacaoTelefone;
  }

  public void setDataExpiracaoCodigoTelefone(LocalDateTime dataExpiracaoCodigoTelefone) {
    this.dataExpiracaoCodigoTelefone = dataExpiracaoCodigoTelefone;
  }

  public void setTokenResetSenha(String tokenResetSenha) {
    this.tokenResetSenha = tokenResetSenha;
  }

  public void setDataExpiracaoTokenSenha(LocalDateTime dataExpiracaoTokenSenha) {
    this.dataExpiracaoTokenSenha = dataExpiracaoTokenSenha;
  }

  // --- Outros Métodos Úteis (ex: toString, equals, hashCode) ---
  // Você pode considerar adicionar estes métodos para melhor depuração e comparação de objetos.
  // Muitos IDEs (como IntelliJ IDEA, Eclipse) podem gerá-los automaticamente.
  // Exemplo básico de toString():
  @Override
  public String toString() {
    return "Usuario{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", email='" + email + '\'' +
      ", telefone='" + telefone + '\'' +
      ", emailVerificado=" + emailVerificado +
      ", telefoneVerificado=" + telefoneVerificado +
      '}';
  }
}
