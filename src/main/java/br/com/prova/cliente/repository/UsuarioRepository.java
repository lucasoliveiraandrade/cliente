package br.com.prova.cliente.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.prova.cliente.collection.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

	boolean existsByEmail(String email);
}
