package com.example.backend.dto;

import java.io.Serializable;

public class VerificacaoRequest implements Serializable {

  private String contato;
  private String codigo;

  public VerificacaoRequest() {
  }

  public VerificacaoRequest(String contato, String codigo) {
    this.contato = contato;
    this.codigo = codigo;
  }

  // Certifique-se que estes getters e setters estão corretos e bem escritos
  public String getContato() {
    return contato;
  }

  public void setContato(String contato) {
    this.contato = contato;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) { // <-- Verifique novamente se não tem 'void void' aqui
    this.codigo = codigo;
  }

  @Override
  public String toString() {
    return "VerificacaoRequest{" +
      "contato='" + contato + '\'' +
      ", codigo='" + codigo + '\'' +
      '}';
  }
}
