package br.com.prova.cliente.dto.request;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTORequest {

	@NotEmpty
	private String nome;

	@Email
	private String email;

	@NotEmpty
	private String dataNascimento;

	private String timeCoracaoId;

	private List<String> campanhasId;

}
