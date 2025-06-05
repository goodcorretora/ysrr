package com.example.backend.controllers;

import com.example.backend.domain.Usuario;
import com.example.backend.dto.RegistroRequest;
import com.example.backend.dto.VerificacaoRequest;
import com.example.backend.repositories.UsuarioRepository;
import com.example.backend.services.EmailService;
import com.example.backend.services.SmsService;
import com.example.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;


import org.springframework.web.bind.annotation.*; // Confirme que * está importado





@RestController
@RequestMapping("/usuarios") // Renomeado de /auth para /usuarios
public class UsuarioController {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private EmailService emailService; // Precisaremos criar este serviço

  @Autowired
  private SmsService smsService;     // Precisaremos criar este serviço

  @Autowired
  private UsuarioService usuarioService; // Assumindo que você está injetando o serviço

  /**
   * Endpoint para cadastro de um novo usuário.
   * Requer nome, e-mail (ou telefone) e senha.
   * Envia código de verificação por e-mail ou telefone.
   *
   * @param registroRequest Objeto DTO contendo os dados de registro.
   * @return ResponseEntity com mensagem de sucesso ou erro.
   */
  @PostMapping("/cadastro")
  public ResponseEntity<String> cadastrarUsuario(@RequestBody RegistroRequest registroRequest) {
    // Validações básicas
    if (registroRequest.getName() == null || registroRequest.getName().isEmpty()) {
      return new ResponseEntity<>("O nome é obrigatório.", HttpStatus.BAD_REQUEST);
    }

    if (registroRequest.getEmail() == null || registroRequest.getEmail().isEmpty()) {
      if (registroRequest.getTelefone() == null || registroRequest.getTelefone().isEmpty()) {
        return new ResponseEntity<>("É necessário fornecer um e-mail ou telefone.", HttpStatus.BAD_REQUEST);
      }
    }

    if (registroRequest.getSenha() == null || registroRequest.getSenha().isEmpty()) {
      return new ResponseEntity<>("A senha é obrigatória.", HttpStatus.BAD_REQUEST);
    }

    // Verifica se e-mail já existe
    if (registroRequest.getEmail() != null && !registroRequest.getEmail().isEmpty() &&
      usuarioRepository.findByEmail(registroRequest.getEmail()).isPresent()) {
      return new ResponseEntity<>("Este e-mail já está cadastrado.", HttpStatus.CONFLICT);
    }

    // Verifica se telefone já existe
    if (registroRequest.getTelefone() != null && !registroRequest.getTelefone().isEmpty() &&
      usuarioRepository.findByTelefone(registroRequest.getTelefone()).isPresent()) {
      return new ResponseEntity<>("Este telefone já está cadastrado.", HttpStatus.CONFLICT);
    }

    // Cria o novo usuário com os dados do DTO e criptografa a senha
    Usuario novoUsuario;
    novoUsuario = new Usuario(
      registroRequest.getName(),
      registroRequest.getEmail(),
      passwordEncoder.encode(registroRequest.getSenha()), // Criptografa a senha
      registroRequest.getTelefone()
    );
    // A dataCadastro é definida no construtor da classe Usuario

    // Geração e atribuição de códigos de verificação
    String codigoVerificacaoEmail = null;
    LocalDateTime dataExpiracaoEmail = null;
    String codigoVerificacaoTelefone = null;
    LocalDateTime dataExpiracaoTelefone = null;

    // Se o email foi fornecido, gera o código de verificação para email
    if (novoUsuario.getEmail() != null && !novoUsuario.getEmail().isEmpty()) {
      codigoVerificacaoEmail = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
      dataExpiracaoEmail = LocalDateTime.now().plusMinutes(15); // Expira em 15 minutos
      novoUsuario.setCodigoVerificacaoEmail(codigoVerificacaoEmail);
      novoUsuario.setDataExpiracaoCodigoEmail(dataExpiracaoEmail);
      novoUsuario.setEmailVerificado(false); // Garante que está como não verificado inicialmente
      // emailService.enviarEmailVerificacao(novoUsuario.getEmail(), codigoVerificacaoEmail);
      System.out.println("DEBUG: Código de verificação para e-mail: " + codigoVerificacaoEmail + " (enviar via EmailService)");
    }

    // Se o telefone foi fornecido, gera o código de verificação para telefone
    if (novoUsuario.getTelefone() != null && !novoUsuario.getTelefone().isEmpty()) {
      codigoVerificacaoTelefone = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
      dataExpiracaoTelefone = LocalDateTime.now().plusMinutes(15); // Expira em 15 minutos
      novoUsuario.setCodigoVerificacaoTelefone(codigoVerificacaoTelefone);
      novoUsuario.setDataExpiracaoCodigoTelefone(dataExpiracaoTelefone);
      novoUsuario.setTelefoneVerificado(false); // Garante que está como não verificado inicialmente
      // smsService.enviarSmsVerificacao(novoUsuario.getTelefone(), codigoVerificacaoTelefone);
      System.out.println("DEBUG: Código de verificação para telefone: " + codigoVerificacaoTelefone + " (enviar via SmsService)");
    }

    // Salva o usuário no banco de dados
    usuarioRepository.save(novoUsuario);

    String mensagemRetorno = "Cadastro realizado com sucesso. ";
    if (novoUsuario.getEmail() != null && !novoUsuario.getEmail().isEmpty()) {
      mensagemRetorno += "Verifique seu e-mail para confirmar a conta.";
    }
    if (novoUsuario.getTelefone() != null && !novoUsuario.getTelefone().isEmpty()) {
      if (novoUsuario.getEmail() != null && !novoUsuario.getEmail().isEmpty()) {
        mensagemRetorno += " E/ou";
      }
      mensagemRetorno += " Verifique seu telefone para confirmar a conta.";
    }
    if (novoUsuario.getEmail() == null && novoUsuario.getTelefone() == null) {
      mensagemRetorno += "Nenhuma verificação de contato necessária.";
    }

    return new ResponseEntity<>(mensagemRetorno, HttpStatus.CREATED);
  }

  @PostMapping("/verificar-email")
  public ResponseEntity<String> verificarEmail(@RequestBody VerificacaoRequest request) {
    // ... (lógica) ...
    if (request.getContato() == null || request.getContato().isEmpty() ||
      request.getCodigo() == null || request.getCodigo().isEmpty()) {
      return new ResponseEntity<>("Email e código de verificação são obrigatórios.", HttpStatus.BAD_REQUEST);
    }

    boolean sucesso = usuarioService.verificarEmail(request.getContato(), request.getCodigo());

    if (sucesso) {
      return new ResponseEntity<>("Email verificado com sucesso!", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Falha na verificação do email. Código inválido ou expirado.", HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/verificar-telefone")
  public ResponseEntity<String> verificarTelefone(@RequestBody VerificacaoRequest request) {
    // ... (lógica) ...
    if (request.getContato() == null || request.getContato().isEmpty() ||
      request.getCodigo() == null || request.getCodigo().isEmpty()) {
      return new ResponseEntity<>("Telefone e código de verificação são obrigatórios.", HttpStatus.BAD_REQUEST);
    }

    boolean sucesso = usuarioService.verificarTelefone(request.getContato(), request.getCodigo());

    if (sucesso) {
      return new ResponseEntity<>("Telefone verificado com sucesso!", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Falha na verificação do telefone. Código inválido ou expirado.", HttpStatus.BAD_REQUEST);
    }
  }

}
