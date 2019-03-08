package br.com.prova.usuario.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.prova.usuario.enumeration.TipoUsuario;
import br.com.prova.usuario.integration.dto.CampanhaDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "usuario")
public class Usuario {

	@Id
	private String id;

	@NotEmpty
	private String nome;

	@NotEmpty
	@Email
	private String email;

	private String timeCoracaoId;

	@NotNull
	private LocalDateTime dataNascimento;

	@NotNull
	private TipoUsuario tipo;

	private List<CampanhaDTO> campanhas;
}
