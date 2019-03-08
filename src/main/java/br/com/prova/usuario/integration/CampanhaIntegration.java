package br.com.prova.usuario.integration;

import java.util.List;

import br.com.prova.usuario.integration.dto.CampanhaDTO;
import feign.Param;
import feign.RequestLine;

public interface CampanhaIntegration {

	@RequestLine("GET /api/v1/campanhas?time-coracao-id={timeCoracaoId}")
	List<CampanhaDTO> buscaCampanhasPorTimeCoracaoId(@Param("timeCoracaoId") String timeCoracaoId);

	@RequestLine("GET /api/v1/campanhas?ids={ids}")
	List<CampanhaDTO> buscaCampanhasPorIds(@Param("ids") List<String> ids);
}
