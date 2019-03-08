package br.com.prova.usuario.integration.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@Builder
public class CampanhaDTO {
	private String id;
	private String nome;
	private String timeCoracaoId;
	private String dataInicio;
	private String dataTermino;
	private String status;
}
