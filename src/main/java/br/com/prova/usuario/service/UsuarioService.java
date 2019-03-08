package br.com.prova.usuario.service;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.prova.usuario.enumeration.TipoUsuario;
import br.com.prova.usuario.integration.CampanhaIntegration;
import br.com.prova.usuario.integration.dto.CampanhaDTO;
import br.com.prova.usuario.model.Usuario;
import br.com.prova.usuario.repository.UsuarioRepository;
import br.com.prova.usuario.validacao.UsuarioValidacao;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private UsuarioValidacao validador;

	@Autowired
	private CampanhaIntegration campanhaIntegration;

	public List<CampanhaDTO> salvaUsuario(Usuario usuario) {
		validador.validaUsuarioJaExistente(repository.existsByEmail(usuario.getEmail()));

		usuario.setTipo(TipoUsuario.CLIENTE);
		repository.insert(usuario);

		List<CampanhaDTO> campanhasValidas = new ArrayList<>();

		if (isEmpty(usuario.getCampanhas()) && isNotBlank(usuario.getTimeCoracaoId())) {
			campanhasValidas = campanhaIntegration.buscaCampanhasPorTimeCoracaoId(usuario.getTimeCoracaoId());
		}

		return campanhasValidas;
	}

	public void associaUsuarioCampanhas(String usuarioId) {
		validador.validaUsuarioId(usuarioId);

		Usuario usuario = repository.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

		List<CampanhaDTO> campanhasTimeDoCoracao = campanhaIntegration
				.buscaCampanhasPorTimeCoracaoId(usuario.getTimeCoracaoId());

		Set<CampanhaDTO> campanhasSet = new HashSet<>();
		campanhasSet.addAll(usuario.getCampanhas());
		campanhasSet.addAll(campanhasTimeDoCoracao);

		usuario.setCampanhas(new ArrayList<CampanhaDTO>(campanhasSet));

		repository.save(usuario);
	}

	public Usuario buscaPorId(String usuarioId) {
		validador.validaUsuarioId(usuarioId);
		return repository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
	}

	public List<Usuario> buscaUsuarios() {
		return repository.findAll();
	}
}
