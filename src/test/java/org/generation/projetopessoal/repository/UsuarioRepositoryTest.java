package org.generation.projetopessoal.repository;



import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.List;
import java.util.Optional;

import org.generation.projetopessoal.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;



@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class UsuarioRepositoryTest {

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.save(new Usuario(0L,"DJ Bea", "bea@bea.com","garrafa","https://i.imgur.com/FETvs2O.jpg"));
		usuarioRepository.save(new Usuario(0L,"DJ triz", "beatr@triz.com","senha","https://i.imgur.com/FETvs2O.jpg"));
		usuarioRepository.save(new Usuario(0L,"Ednaldo Pereira", "ednaldo@banido.com","senhasenha","https://i.imgur.com/FETvs2O.jpg"));
		usuarioRepository.save(new Usuario(0L,"Naninha", "naninha@nana.com","outra senha","https://i.imgur.com/FETvs2O.jpg"));
	}
	@Test
	@DisplayName("Retorna apenas um usuario")
	public void deveRetornarUmUsuario() {
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("naninha@nana.com");
			assertTrue(usuario.get().getUsuario().equals("naninha@nana.com"));
	}
	
	@Test
	@DisplayName("Retorna dois usuários")
	public void deveRetornarDoisUsuarios() {
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("DJ");
		assertEquals(2, listaDeUsuarios.size());//trazendo o tamanho da requisição 
		
		assertTrue(listaDeUsuarios.get(0).getNome().equals("DJ Bea"));
		assertTrue(listaDeUsuarios.get(0).getNome().equals("DJ Bea"));
	}
	
}
