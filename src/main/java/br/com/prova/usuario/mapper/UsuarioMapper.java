package br.com.prova.usuario.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.prova.usuario.dto.request.UsuarioDTORequest;
import br.com.prova.usuario.dto.response.UsuarioDTOResponse;
import br.com.prova.usuario.integration.CampanhaIntegration;
import br.com.prova.usuario.integration.dto.CampanhaDTO;
import br.com.prova.usuario.model.Usuario;
import br.com.prova.usuario.util.DataUtil;

@Component
public class UsuarioMapper {

	@Autowired
	private DataUtil dataUtil;

	@Autowired
	private CampanhaIntegration campanhaIntegration;

	public Usuario toObject(UsuarioDTORequest usuarioDTORequest) {
		LocalDate dataNascimento = dataUtil.converteStringParaLocalDate(usuarioDTORequest.getDataNascimento());

		List<CampanhaDTO> campanhas = campanhaIntegration.buscaCampanhasPorIds(usuarioDTORequest.getCampanhasId());

		return Usuario.builder().nome(usuarioDTORequest.getNome()).email(usuarioDTORequest.getEmail())
				.timeCoracaoId(usuarioDTORequest.getTimeCoracaoId()).dataNascimento(dataNascimento).campanhas(campanhas)
				.build();
	}

	public UsuarioDTOResponse toDTO(Usuario usuario) {
		String dataNascimento = dataUtil.converteLocalDateParaString(usuario.getDataNascimento());

		return UsuarioDTOResponse.builder().id(usuario.getId()).nome(usuario.getNome()).dataNascimento(dataNascimento)
				.campanhas(usuario.getCampanhas()).tipo(usuario.getTipo().name()).email(usuario.getEmail())
				.timeCoracaoId(usuario.getTimeCoracaoId()).build();
	}

	public List<UsuarioDTOResponse> toDTOs(List<Usuario> usuarios) {
		return usuarios.stream().map(this::toDTO).collect(Collectors.toList());
	}
}
