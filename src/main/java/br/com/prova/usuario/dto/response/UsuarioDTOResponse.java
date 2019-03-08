package br.com.prova.usuario.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import br.com.prova.usuario.integration.dto.CampanhaDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTOResponse {

	private String id;
	private String nome;
	private String email;
	private String timeCoracaoId;
	private LocalDateTime dataNascimento;
	private String tipo;
	private List<CampanhaDTO> campanhas;
}
