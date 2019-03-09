package br.com.prova.usuario.unittest.validacao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.prova.usuario.validacao.UsuarioValidacao;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioValidacaoTest {

	@InjectMocks
	UsuarioValidacao validador;

	@Test(expected = Exception.class)
	public void deveLancarExceptionSeUsuarioJaExistente() {
		validador.validaUsuarioJaExistente(Boolean.TRUE);
	}

	@Test
	public void naoDeveLancarExceptionSeUsuarioNaoExistente() {
		validador.validaUsuarioJaExistente(Boolean.FALSE);
	}

	@Test
	public void naoDeveLancarExceptionSeUsuarioIdNaoNulo() {
		validador.validaUsuarioId("123");
	}

	@Test(expected = Exception.class)
	public void deveLancarExceptionSeUsuarioIdNulo() {
		validador.validaUsuarioId(null);
	}

}
