package br.com.prova.cliente.integration;

import java.util.List;

import br.com.prova.cliente.integration.dto.CampanhaDTO;
import feign.Param;
import feign.RequestLine;

public interface CampanhaIntegration {

	@RequestLine("GET /api/v1/campanhas?time-coracao-id={timeCoracaoId}")
	List<CampanhaDTO> buscaCampanhas(@Param("timeCoracaoId") String timeCoracaoId);
}
