package com.academia.entities;

public class Plano {

	private int idPlano;
	private String nome;
	private String descricao;
	private Double preco;
	private int duracao;

	public Plano(int idPlano, String nome, String descricao, Double preco, int duracao) {
		super();
		this.idPlano = idPlano;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.duracao = duracao;
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	@Override
	public String toString() {
		return nome;
	}

}
