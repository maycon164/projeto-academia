package com.academia.entities;

import java.util.Date;

public class Aluno extends Pessoa {

	private boolean ativo = true;
	private String observacao = "";

	private Assinatura assinatura;

	public Aluno(String cpf, String nome, Date nascimento, String email, String telefone, Character sexo, String cep,
			String bairro, String rua, String num, Date data_matricula, boolean ativo, String observacao) {
		super(cpf, nome, nascimento, email, telefone, sexo, cep, bairro, rua, num, data_matricula);
		this.ativo = ativo;
		this.observacao = observacao;

	}

	public Aluno() {

	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Assinatura getAssinatura() {
		return assinatura;
	}

	public void setAssinatura(Assinatura assinatura) {
		this.assinatura = assinatura;
	}

	@Override
	public String toString() {
		return super.toString() + "\nAluno [ativo=" + ativo + ", observacao=" + observacao + ", assinaturas="
				+ assinatura + "];";
	}

}
