package org.generation.projetopessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "tb_usuarios")

public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nome;
	
	@Schema (example = "email@email.com.br")
	@NotNull(message = "O atributo Usuario é Obrigatorio!")
	@Email (message = "O atributo usuario deve ser um email valido!")
	private String usuario;

	@NotNull
	private String senha;

	private String foto;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE) // quando um usuário for excluído, exclui postagens
	@JsonIgnoreProperties("usuario") // ignora recursividade dos gets
	private List<Postagem> postagens;

	// Metodos construtores para testes
	//importante seguir a mesma ordem das declarações dos atributos de usuario
	//construtor cheio - obriga a testar 

	public Usuario(Long id, String nome, String usuario, String senha, String foto) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.foto = foto;

	}
	
	//construtor vazio - dar liberdade pra testar o que quiser
	public Usuario() {}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Postagem> getPostagens() {
		return postagens;
	}

	public void setPostagens(List<Postagem> postagens) {
		this.postagens = postagens;
	}

}
