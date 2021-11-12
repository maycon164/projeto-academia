package com.academia.dto;

import java.util.Date;

public class AlunoDTO {

	private String cpf;
	private String nome;
	private int idade;
	private Date data_matricula;
	private boolean ativo;

	public AlunoDTO(String cpf, String nome, int idade, Date data_matricula, boolean ativo) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.idade = idade;
		this.data_matricula = data_matricula;
		this.ativo = ativo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public Date getData_matricula() {
		return data_matricula;
	}

	public void setData_matricula(Date data_matricula) {
		this.data_matricula = data_matricula;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "AlunoDTO [cpf=" + cpf + ", nome=" + nome + ", idade=" + idade + ", data_matricula=" + data_matricula
				+ ", ativo=" + ativo + "]";
	}

}
