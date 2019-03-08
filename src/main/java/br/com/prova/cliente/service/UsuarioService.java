package br.com.prova.cliente.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.prova.cliente.collection.Usuario;
import br.com.prova.cliente.enumeration.TipoUsuario;
import br.com.prova.cliente.integration.CampanhaIntegration;
import br.com.prova.cliente.integration.dto.CampanhaDTO;
import br.com.prova.cliente.repository.UsuarioRepository;
import br.com.prova.cliente.validacao.UsuarioValidacao;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private UsuarioValidacao validador;
	
	@Autowired
	private CampanhaIntegration campanhaIntegration;

	public List<String> salvaUsuario(Usuario usuario) {
		validador.validaUsuarioJaExistente(repository.existsByEmail(usuario.getEmail()));

		usuario.setTipo(TipoUsuario.CLIENTE);
		repository.insert(usuario);

		List<String> campanhasValidas = new ArrayList<>();

		if (CollectionUtils.isEmpty(usuario.getCampanhasId())) {
			campanhasValidas = campanhaIntegration.buscaCampanhas(usuario.getTimeCoracaoId())
				.stream()
				.map(CampanhaDTO::getId)
				.collect(Collectors.toList());
		}

		return campanhasValidas;
	}

	public void associaUsuarioCampanhas(String usuarioId) {
		validador.validaUsuarioId(usuarioId);

		Usuario usuario = repository.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

		List<String> campanhasTimeDoCoracao = null;
//				campanhaService.buscaCampanhasValidasPorTimeCoracao(usuario.getTimeCoracaoId());

		Set<String> campanhasSet = new HashSet<>();
		campanhasSet.addAll(usuario.getCampanhasId());
		campanhasSet.addAll(campanhasTimeDoCoracao);

		usuario.setCampanhasId(new ArrayList<String>(campanhasSet));

		repository.save(usuario);
	}

	public Usuario buscaPorId(String usuarioId) {
		validador.validaUsuarioId(usuarioId);

		return repository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
	}
}
