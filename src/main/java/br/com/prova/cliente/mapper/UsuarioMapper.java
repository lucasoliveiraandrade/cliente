package br.com.prova.cliente.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.prova.cliente.collection.Usuario;
import br.com.prova.cliente.dto.request.UsuarioDTORequest;
import br.com.prova.cliente.dto.response.UsuarioDTOResponse;
import br.com.prova.cliente.util.DataUtil;

@Component
public class UsuarioMapper {

	@Autowired
	private DataUtil dataUtil;

	public Usuario toObject(UsuarioDTORequest dtoRequest) {
		LocalDate dataNascimento = dataUtil.converteStringParaLocalDate(dtoRequest.getDataNascimento());

		return Usuario.builder().nome(dtoRequest.getNome()).email(dtoRequest.getEmail())
				.timeCoracaoId(dtoRequest.getTimeCoracaoId()).dataNascimento(dataNascimento)
				.campanhasId(dtoRequest.getCampanhasId()).build();
	}

	public UsuarioDTOResponse toDTO(Usuario usuario) {
		String dataNascimento = dataUtil.converteLocalDateParaString(usuario.getDataNascimento());

		return UsuarioDTOResponse.builder().id(usuario.getId()).nome(usuario.getNome()).dataNascimento(dataNascimento)
				.campanhasId(usuario.getCampanhasId()).tipo(usuario.getTipo().name()).email(usuario.getEmail())
				.timeCoracaoId(usuario.getTimeCoracaoId()).build();
	}

	public List<UsuarioDTOResponse> toDTOs(List<Usuario> usuarios) {
		return usuarios.stream().map(this::toDTO).collect(Collectors.toList());
	}
}
