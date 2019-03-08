package br.com.prova.usuario.integration.dto;

import java.time.LocalDateTime;

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
	private LocalDateTime dataInicio;
	private LocalDateTime dataTermino;
	private String status;
}
