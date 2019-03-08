package br.com.prova.usuario.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.prova.usuario.model.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

	boolean existsByEmail(String email);
}
