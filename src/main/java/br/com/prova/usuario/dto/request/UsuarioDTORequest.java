package br.com.prova.usuario.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTORequest {

	@NotEmpty
	private String nome;

	@Email
	private String email;

	@NotNull
	private LocalDateTime dataNascimento;

	private String timeCoracaoId;

	private List<String> campanhasId;

}
