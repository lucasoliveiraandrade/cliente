package br.com.prova.cliente.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.prova.cliente.integration.CampanhaIntegration;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

@Configuration
public class FeignConfig {

	@Bean
	public CampanhaIntegration getCampanhaIntegration() {
		return Feign.builder().decoder(new GsonDecoder()).encoder(new GsonEncoder()).target(CampanhaIntegration.class,
				"http://localhost:8081");
	}
}
