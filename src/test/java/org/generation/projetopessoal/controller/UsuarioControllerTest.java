package org.generation.projetopessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.generation.projetopessoal.model.Usuario;
import org.generation.projetopessoal.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	//usado apenas no controller
	@Autowired
	private TestRestTemplate testRestTemplate;//usa metodos e verbos http get post put delete

	@Autowired
	private UsuarioService usuarioService;

	@Test
	@Order(1)
	@DisplayName("Cadastrar Um Usuario")
	public void deveCriarUmUsuario() {

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(
				new Usuario(0L, "Maria Santos", "maria@maria.com", "senhamaria", "linkdefoto"));

		ResponseEntity<Usuario> resposta = testRestTemplate
		.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao,Usuario.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}

	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do Usuario")
	public void naoDeveDuplicarUsuario() {

		usuarioService.cadastrarUsuario(new Usuario(0L, "Joao Silva", "joao@joao.com", "senhadojoao", "linkdefoto"));
//cria uma nova requisição http
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>( new Usuario(0L, "Joao Silva", "joao@joao.com", "senhadojoao", "linkdefoto"));

		ResponseEntity<Usuario> resposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao,
				Usuario.class); //passar a rota que sera usada

		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());

	}

	@Test
	@Order(3)
	@DisplayName("Alterar um Usuario")
	public void deveAtualizarUmUsuario() {

		Optional<Usuario> usuarioCreate = usuarioService
				.cadastrarUsuario(new Usuario(0L, "Jose Santos", "jose@jose.com", "senhadojose", "linkdefoto"));

		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(), "Jose Santos", "jose@jose.com", "senhadojose", "linkdefoto");

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
	}

	@Test
	@Order(4)
	@DisplayName("Listar todos os Usuarios")
	public void deveMostrarTodosUsuarios() {

		usuarioService.cadastrarUsuario(new Usuario(0L, "Mariana Costa", "mariana@mariana.com", "senhamariana", "linkdefoto"));

		usuarioService.cadastrarUsuario(new Usuario(0L, "Joao Silva", "joao@joao.com", "senhadojoao", "linkdefoto"));

		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/all",
				HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

}
