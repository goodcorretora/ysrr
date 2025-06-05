package com.example.backend.repositories;


import com.example.backend.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  /**
   * Busca um usuário pelo email.
   * O Spring Data JPA automaticamente implementa este método baseado no nome.
   *
   * @param email O email do usuário.
   * @return Um Optional contendo o usuário, se encontrado.
   */
  Optional<Usuario> findByEmail(String email);

  /**
   * Busca um usuário pelo telefone.
   * O Spring Data JPA automaticamente implementa este método baseado no nome.
   *
   * @param telefone O telefone do usuário.
   * @return Um Optional contendo o usuário, se encontrado.
   */
  Optional<Usuario> findByTelefone(String telefone);

  // Make sure these are also present for verification
  Optional<Usuario> findByCodigoVerificacaoEmail(String codigoVerificacaoEmail);
  Optional<Usuario> findByCodigoVerificacaoTelefone(String codigoVerificacaoTelefone);

  // And these for more secure verification
  Optional<Usuario> findByEmailAndCodigoVerificacaoEmail(String email, String codigoVerificacaoEmail);
  Optional<Usuario> findByTelefoneAndCodigoVerificacaoTelefone(String telefone, String codigoVerificacaoTelefone);

  // Add any other methods you might have in your UsuarioRepository
}








//
//
//  extends JpaRepository<Usuario, Long> {
//
//  /**
//   * Busca um usuário pelo email.
//   * O Spring Data JPA automaticamente implementa este método baseado no nome.
//   *
//   * @param email O email do usuário.
//   * @return Um Optional contendo o usuário, se encontrado.
//   */
//  Optional<Usuario> findByEmail(String email);
//
//  /**
//   * Busca um usuário pelo telefone.
//   * O Spring Data JPA automaticamente implementa este método baseado no nome.
//   *
//   * @param telefone O telefone do usuário.
//   * @return Um Optional contendo o usuário, se encontrado.
//   */
//  Optional<Usuario> findByTelefone(String telefone);
//
//  // Métodos adicionais que você pode precisar:
//
//  /**
//   * Busca um usuário pelo código de verificação de email.
//   *
//   * @param codigoVerificacaoEmail O código de verificação de email.
//   * @return Um Optional contendo o usuário, se encontrado.
//   */
//  Optional<Usuario> findByCodigoVerificacaoEmail(String codigoVerificacaoEmail);
//
//  /**
//   * Busca um usuário pelo código de verificação de telefone.
//   *
//   * @param codigoVerificacaoTelefone O código de verificação de telefone.
//   * @return Um Optional contendo o usuário, se encontrado.
//   */
//  Optional<Usuario> findByCodigoVerificacaoTelefone(String codigoVerificacaoTelefone);
//
//  /**
//   * Busca um usuário pelo token de reset de senha.
//   *
//   * @param tokenResetSenha O token de reset de senha.
//   * @return Um Optional contendo o usuário, se encontrado.
//   */
//  Optional<Usuario> findByTokenResetSenha(String tokenResetSenha);
//
//  // Outros métodos de consulta customizados podem ser adicionados aqui conforme a necessidade.
//  // Ex: List<Usuario> findByEmailVerificadoTrue();
//}
