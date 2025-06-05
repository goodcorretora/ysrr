package com.example.backend.services;

public interface SmsService {

  /**
   * Envia um código de verificação via SMS para o número de telefone especificado.
   *
   * @param para O número de telefone do destinatário.
   * @param codigoVerificacao O código de verificação a ser enviado.
   */
  void enviarSmsVerificacao(String para, String codigoVerificacao);
}
