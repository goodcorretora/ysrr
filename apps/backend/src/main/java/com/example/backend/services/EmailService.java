package com.example.backend.services;

public interface EmailService {

  /**
   * Envia um e-mail com um código de verificação para o endereço especificado.
   *
   * @param para O endereço de e-mail do destinatário.
   * @param codigoVerificacao O código de verificação a ser enviado.
   */
  void enviarEmailVerificacao(String para, String codigoVerificacao);

  void enviarSmsVerificacao(String para, String codigoVerificacao);

  // Futuramente, você pode adicionar outros métodos para envio de e-mails,
  // como e-mails de boas-vindas, recuperação de senha, etc.
  // Exemplo:
  // void enviarEmailBoasVindas(String para, String nomeUsuario);
  // void enviarEmailResetSenha(String para, String tokenReset);
}
