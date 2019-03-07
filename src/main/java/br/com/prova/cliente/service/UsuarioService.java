package br.com.prova.cliente.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.prova.campanha.collection.Campanha;
import br.com.prova.campanha.validacao.UsuarioValidacao;
import br.com.prova.cliente.collection.Usuario;
import br.com.prova.cliente.enumeration.TipoUsuario;
import br.com.prova.cliente.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private CampanhaService campanhaService;

	@Autowired
	private UsuarioValidacao validador;

	public List<Campanha> salvaUsuario(Usuario usuario) {
		validador.validaUsuarioJaExistente(repository.existsByEmail(usuario.getEmail()));

		usuario.setTipo(TipoUsuario.CLIENTE);
		repository.insert(usuario);

		List<Campanha> campanhasValidas = new ArrayList<>();

		if (CollectionUtils.isEmpty(usuario.getCampanhas())) {
			campanhasValidas = campanhaService.buscaCampanhasValidasPorTimeCoracao(usuario.getTimeCoracaoId());
		}

		return campanhasValidas;
	}

	public void associaUsuarioCampanhas(String usuarioId) {
		validador.validaUsuarioId(usuarioId);

		Usuario usuario = repository.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

		List<Campanha> campanhasTimeDoCoracao = campanhaService
				.buscaCampanhasValidasPorTimeCoracao(usuario.getTimeCoracaoId());

		Set<Campanha> campanhasSet = new HashSet<>();
		campanhasSet.addAll(usuario.getCampanhas());
		campanhasSet.addAll(campanhasTimeDoCoracao);

		usuario.setCampanhas(new ArrayList<Campanha>(campanhasSet));

		repository.save(usuario);
	}

	public Usuario buscaPorId(String usuarioId) {
		validador.validaUsuarioId(usuarioId);

		return repository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
	}
}
