package com.example.backend.services.Impl;

import com.example.backend.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service // Marca esta classe como um componente de serviço gerenciado pelo Spring
public class EmailServiceImpl implements EmailService {

  private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

  @Autowired
  private JavaMailSender mailSender; // Injeção de dependência para o JavaMailSender

  /**
   * Envia um e-mail com um código de verificação para o endereço especificado.
   * Implementa o método definido na interface EmailService.
   *
   * @param para O endereço de e-mail do destinatário.
   * @param codigoVerificacao O código de verificação a ser enviado.
   */
  @Override
  public void enviarEmailVerificacao(String para, String codigoVerificacao) {
    SimpleMailMessage mensagem = new SimpleMailMessage();
    mensagem.setTo(para);
    mensagem.setSubject("Verificação de E-mail Proto");
    mensagem.setText("Olá!\n\nSeu código de verificação para o aplicativo Proto é: " + codigoVerificacao +
      "\n\nEste código expirará em 15 minutos. Não o compartilhe com ninguém." +
      "\n\nAtenciosamente,\nEquipe Proto");

    try {
      mailSender.send(mensagem);
      logger.info("E-mail de verificação enviado com sucesso para: {}", para);
    } catch (Exception e) {
      logger.error("Erro ao enviar e-mail de verificação para: {}. Detalhes: {}", para, e.getMessage(), e);
      // Aqui você pode adicionar uma lógica para tratamento de erros, como:
      // - Lançar uma exceção customizada.
      // - Registrar o erro em um sistema de monitoramento.
      // - Tentar reenviar o e-mail após um tempo.
      throw new RuntimeException("Falha ao enviar e-mail de verificação para: " + para, e); // Exemplo de relançar
    }
  }

  /**
   * Este método não deve ser implementado nesta classe, pois é responsabilidade do SmsService.
   * Lança uma exceção ou apenas loga um aviso.
   *
   * @param para O número de telefone do destinatário.
   * @param codigoVerificacao O código de verificação a ser enviado.
   */
  @Override
  public void enviarSmsVerificacao(String para, String codigoVerificacao) {
    // Embora a interface EmailService contenha este método, ele é específico para SMS.
    // É uma boa prática lançar uma exceção ou emitir um aviso forte se chamado indevidamente.
    logger.warn("Método enviarSmsVerificacao (da interface EmailService) chamado em EmailServiceImpl. " +
      "Isso não é suportado por este serviço e deve ser tratado pelo SmsService.");
    throw new UnsupportedOperationException("O envio de SMS não é suportado pelo EmailServiceImpl.");
  }
}
