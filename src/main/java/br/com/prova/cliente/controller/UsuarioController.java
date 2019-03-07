package br.com.prova.cliente.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.prova.campanha.collection.Campanha;
import br.com.prova.campanha.dto.response.CampanhaDTOResponse;
import br.com.prova.campanha.mapper.CampanhaMapper;
import br.com.prova.cliente.collection.Usuario;
import br.com.prova.cliente.mapper.UsuarioMapper;
import br.com.prova.cliente.service.UsuarioService;
import br.com.prova.usuario.dto.request.UsuarioDTORequest;
import br.com.prova.usuario.dto.response.UsuarioDTOResponse;

@RestController
@RequestMapping("/prova/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private UsuarioMapper mapper;

	@Autowired
	private CampanhaMapper campanhaMapper;

	@PostMapping
	public List<CampanhaDTOResponse> criaUsuario(@RequestBody @Valid UsuarioDTORequest dtoRequest) {
		Usuario usuario = mapper.toObject(dtoRequest);
		List<Campanha> campanhas = service.salvaUsuario(usuario);
		return campanhaMapper.toDTOs(campanhas);
	}

	@PostMapping(value = "/{usuarioId}/associacao")
	public void associaUsuarioCampanha(@PathVariable @NotEmpty String usuarioId) {
		service.associaUsuarioCampanhas(usuarioId);
	}

	@GetMapping(value = "/{usuarioId}")
	public UsuarioDTOResponse buscaUsuario(@PathVariable @NotEmpty String usuarioId) {
		Usuario usuario = service.buscaPorId(usuarioId);
		return mapper.toDTO(usuario);
	}
}
