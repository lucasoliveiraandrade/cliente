package br.com.prova.usuario.controller;

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

import br.com.prova.usuario.dto.request.UsuarioDTORequest;
import br.com.prova.usuario.dto.response.UsuarioDTOResponse;
import br.com.prova.usuario.integration.dto.CampanhaDTO;
import br.com.prova.usuario.mapper.UsuarioMapper;
import br.com.prova.usuario.model.Usuario;
import br.com.prova.usuario.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private UsuarioMapper mapper;

	@PostMapping
	public List<CampanhaDTO> criaUsuario(@RequestBody @Valid UsuarioDTORequest usuarioDTORequest) {
		Usuario usuario = mapper.toObject(usuarioDTORequest);
		return service.salvaUsuario(usuario);
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
