package com.example.backend.services.Impl;


import com.example.backend.services.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service // Marca esta classe como um componente de serviço gerenciado pelo Spring
public class SmsServiceImpl implements SmsService {

  private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

  // Variáveis de ambiente ou propriedades do application.properties/yml para as credenciais da Twilio
  @Value("${twilio.account.sid}")
  private String accountSid;

  @Value("${twilio.auth.token}")
  private String authToken;

  @Value("${twilio.phone.number}") // Seu número de telefone Twilio
  private String twilioPhoneNumber;

  /**
   * Envia um código de verificação via SMS para o número de telefone especificado.
   * Implementa o método definido na interface SmsService.
   *
   * @param para O número de telefone do destinatário (formato E.164, ex: "+5511987654321").
   * @param codigoVerificacao O código de verificação a ser enviado.
   */
  @Override
  public void enviarSmsVerificacao(String para, String codigoVerificacao) {
    if (accountSid == null || accountSid.isEmpty() || authToken == null || authToken.isEmpty() || twilioPhoneNumber == null || twilioPhoneNumber.isEmpty()) {
      logger.error("Credenciais Twilio incompletas ou ausentes. Verifique application.properties/yml.");
      throw new IllegalArgumentException("Credenciais Twilio não configuradas corretamente.");
    }

    try {
      Twilio.init(accountSid, authToken); // Inicializa a Twilio com suas credenciais

      Message message = Message.creator(
          new PhoneNumber(para),           // Número de destino
          new PhoneNumber(twilioPhoneNumber), // Seu número Twilio
          "Seu código de verificação Proto é: " + codigoVerificacao +
            " (válido por 15 minutos).")
        .create();

      logger.info("SMS de verificação enviado com SID: {} para: {}", message.getSid(), para);

    } catch (com.twilio.exception.ApiException twilioEx) {
      logger.error("Erro da API Twilio ao enviar SMS para {}: {}. Código de erro: {}", para, twilioEx.getMessage(), twilioEx.getCode(), twilioEx);
      throw new RuntimeException("Falha na API Twilio ao enviar SMS para: " + para, twilioEx);
    } catch (Exception e) {
      logger.error("Erro inesperado ao enviar SMS para: {}. Detalhes: {}", para, e.getMessage(), e);
      throw new RuntimeException("Erro ao enviar SMS para: " + para, e);
    }
  }
}
