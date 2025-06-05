package com.example.backend.services;


import com.example.backend.domain.Usuario;
import com.example.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  // ... (Outros métodos de serviço, ex: para cadastro) ...

  /**
   * Verifica e-mail do usuário.
   * @param email O e-mail do usuário.
   * @param codigo O código de verificação.
   * @return true se a verificação for bem-sucedida, false caso contrário.
   */
  public boolean verificarEmail(String email, String codigo) {
    Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailAndCodigoVerificacaoEmail(email, codigo);

    if (usuarioOpt.isEmpty()) {
      // Usuário não encontrado com esse e-mail e código, ou código incorreto.
      return false;
    }

    Usuario usuario = usuarioOpt.get();

    if (usuario.getEmailVerificado()) {
      // E-mail já verificado.
      return true; // Ou false, dependendo da sua regra de negócio para "já verificado"
    }

    if (usuario.getDataExpiracaoCodigoEmail() != null && usuario.getDataExpiracaoCodigoEmail().isBefore(LocalDateTime.now())) {
      // Código expirado.
      return false;
    }

    usuario.setEmailVerificado(true);
    // Limpar o código e a data de expiração após a verificação para segurança
    usuario.setCodigoVerificacaoEmail(null);
    usuario.setDataExpiracaoCodigoEmail(null);
    usuarioRepository.save(usuario); // Salva as alterações no banco de dados
    return true;
  }

  /**
   * Verifica telefone do usuário.
   * @param telefone O telefone do usuário.
   * @param codigo O código de verificação.
   * @return true se a verificação for bem-sucedida, false caso contrário.
   */
  public boolean verificarTelefone(String telefone, String codigo) {
    Optional<Usuario> usuarioOpt = usuarioRepository.findByTelefoneAndCodigoVerificacaoTelefone(telefone, codigo);

    if (usuarioOpt.isEmpty()) {
      return false;
    }

    Usuario usuario = usuarioOpt.get();

    if (usuario.getTelefoneVerificado()) {
      return true;
    }

    if (usuario.getDataExpiracaoCodigoTelefone() != null && usuario.getDataExpiracaoCodigoTelefone().isBefore(LocalDateTime.now())) {
      return false;
    }

    usuario.setTelefoneVerificado(true);
    usuario.setCodigoVerificacaoTelefone(null);
    usuario.setDataExpiracaoCodigoTelefone(null);
    usuarioRepository.save(usuario);
    return true;
  }
}
