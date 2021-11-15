package com.academia.entities;

import java.util.Date;
import java.util.List;

public class Aluno extends Pessoa {

	private boolean ativo = true;
	private String observacao = "";

	private List<Assinatura> assinaturas;
	
	private List<Pagamento> pagamento;
	private List<Avaliacao> avaliacoes;

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

	public List<Assinatura> getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(List<Assinatura> assinaturas) {
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

	@Override
	public String toString() {
		return super.toString() + "\nAluno [ativo=" + ativo + ", observacao=" + observacao + ", assinaturas="
				+ assinaturas + ", pagamento=" + pagamento + ", avaliacoes=" + avaliacoes + "]";
	}

}
