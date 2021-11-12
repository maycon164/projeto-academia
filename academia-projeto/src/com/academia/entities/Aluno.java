package com.academia.entities;

import java.util.Date;
import java.util.List;

public class Aluno extends Pessoa {

	private boolean ativo;
	private String observacao;

	private List<Plano> assinaturas;
	private List<Pagamento> pagamento;
	private List<Avaliacao> avaliacoes;

	public Aluno(String cpf, String nome, Date nascimento, String email, String telefone, Character sexo, String bairro,
			String rua, String num) {

		super(cpf, nome, nascimento, email, telefone, sexo, bairro, rua, num);

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

	public List<Plano> getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(List<Plano> assinaturas) {
		this.assinaturas = assinaturas;
	}

	public List<Pagamento> getPagamento() {
		return pagamento;
	}

	public void setPagamento(List<Pagamento> pagamento) {
		this.pagamento = pagamento;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}
}
