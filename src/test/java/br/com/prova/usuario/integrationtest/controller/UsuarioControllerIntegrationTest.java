package br.com.prova.usuario.integrationtest.controller;

import static java.util.Arrays.asList;
import static org.apache.commons.collections4.CollectionUtils.EMPTY_COLLECTION;
import static org.apache.commons.lang3.StringUtils.split;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.prova.usuario.integration.CampanhaIntegration;
import br.com.prova.usuario.integration.dto.CampanhaDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = br.com.prova.usuario.Aplicacao.class, webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.yml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioControllerIntegrationTest {

	private static final String URL_PADRAO = "/api/v1/usuarios/";
	private static final String CONTEUDO_POST = "{ \"nome\": \"usuario teste\",	\"email\": \"teste%s@teste.com\", \"dataNascimento\": \"2019-03-20T01:44:50\", \"timeCoracaoId\": \"1\", \"campanhas\": [	] }";

	@Autowired
	private MockMvc mockMvc;

	private static String usuarioId;

	@MockBean
	private CampanhaIntegration campanhaIntegrationMock;

	@Test
	public void teste001_deveCadastrarUsuarioSemCampanha() throws Exception {
		CampanhaDTO campanhaDTO = CampanhaDTO.builder().id("123").build();

		when(campanhaIntegrationMock.buscaCampanhasPorTimeCoracaoId(Mockito.any())).thenReturn(asList(campanhaDTO));

		String resposta = mockMvc.perform(post(URL_PADRAO).content(getCorpoRequisicao()).contentType(APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

		assertNotNull(resposta);
		assertTrue(resposta.contains(campanhaDTO.getId()));
	}

	@Test
	public void teste002_deveCadastrarUsuarioComCampanha() throws Exception {
		when(campanhaIntegrationMock.buscaCampanhasPorTimeCoracaoId(Mockito.any()))
				.thenReturn(new ArrayList<CampanhaDTO>());

		String resposta = mockMvc.perform(post(URL_PADRAO).content(getCorpoRequisicao()).contentType(APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

		assertNotNull(resposta);
		assertTrue(resposta.equals(EMPTY_COLLECTION.toString()));
	}

	@Test
	public void teste003_deveBuscarTodosUsuarios() throws Exception {
		String resposta = mockMvc.perform(get(URL_PADRAO).contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertNotNull(resposta);
		usuarioId = split(resposta, '"')[3];
	}

	@Test
	public void teste004_deveBuscarUsuarioPorId() throws Exception {
		String resposta = mockMvc.perform(get(URL_PADRAO + "/" + usuarioId).contentType(APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		assertNotNull(resposta);
		assertTrue(resposta.contains(usuarioId));
	}

	@Test
	public void teste005_deveAssociarUsuarioAsCampanhas() throws Exception {
		CampanhaDTO campanhaDTO = CampanhaDTO.builder().id("123").build();

		when(campanhaIntegrationMock.buscaCampanhasPorTimeCoracaoId(Mockito.any())).thenReturn(asList(campanhaDTO));

		mockMvc.perform(post(URL_PADRAO + "/" + usuarioId + "/associacao").contentType(APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	private String getCorpoRequisicao() {
		return String.format(CONTEUDO_POST, new Date().getTime());
	}
}
