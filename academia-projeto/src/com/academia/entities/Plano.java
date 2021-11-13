package com.academia.entities;

public class Plano {

	private int idPlano;
	private String nome;
	private String descricao;

	public Plano(int idPlano, String nome, String descricao) {
		super();
		this.idPlano = idPlano;
		this.nome = nome;
		this.descricao = descricao;
	}

	public int getIdPlano() {
		return idPlano;
	}

	public void setIdPlano(int idPlano) {
		this.idPlano = idPlano;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return nome;
	}

	
}
