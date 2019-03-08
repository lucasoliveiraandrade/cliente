package br.com.prova.usuario.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.prova.usuario.integration.CampanhaIntegration;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

@Configuration
public class FeignConfig {

	@Value("${integration.campanha.url}")
	private String campanhaUrl;

	@Bean
	public CampanhaIntegration getCampanhaIntegration() {
		return Feign.builder().decoder(new GsonDecoder()).encoder(new GsonEncoder()).target(CampanhaIntegration.class,
				campanhaUrl);
	}
}
