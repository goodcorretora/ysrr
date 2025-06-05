package com.example.backend.dto;

import java.io.Serializable; // Boa prática para DTOs, especialmente se for serializado

public class RegistroRequest implements Serializable { // Opcional, mas boa prática

  private String name;
  private String email;
  private String senha;
  private String telefone;

  // Construtor Vazio (necessário para serialização e deserialização do Spring/Jackson)
  public RegistroRequest() {
  }

  // Construtor com todos os campos (opcional, mas útil)
  public RegistroRequest(String name, String email, String senha, String telefone) {
    this.name = name;
    this.email = email;
    this.senha = senha;
    this.telefone = telefone;
  }

  // --- Getters ---
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

  // --- Setters ---
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

  // Opcional: Adicionar toString() para facilitar a depuração
  @Override
  public String toString() {
    return "RegistroRequest{" +
      "name='" + name + '\'' +
      ", email='" + email + '\'' +
      ", telefone='" + telefone + '\'' + // Não inclua a senha em toString por segurança!
      '}';
  }
}
