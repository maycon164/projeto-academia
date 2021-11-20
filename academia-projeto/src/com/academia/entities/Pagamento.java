package com.academia.entities;

import java.util.Date;

public class Pagamento {

	private int idPagamento;
	private String cpfAluno;
	private String nomeAluno;
	private int idPlano;
	private String nomePlano;
	private double valorTotal;
	private Date dataPagamento;
	private TipoPagamento tipoPagamento;
	private StatusPagamento status;

	public Pagamento() {

	}

	public int getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(int idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getCpfAluno() {
		return cpfAluno;
	}

	public void setCpfAluno(String cpfAluno) {
		this.cpfAluno = cpfAluno;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public int getIdPlano() {
		return idPlano;
	}

	public void setIdPlano(int idPlano) {
		this.idPlano = idPlano;
	}

	public String getNomePlano() {
		return nomePlano;
	}

	public void setNomePlano(String nomePlano) {
		this.nomePlano = nomePlano;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public StatusPagamento getStatus() {
		return status;
	}

	public void setStatus(StatusPagamento status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Pagamento [idPagamento=" + idPagamento + ", cpfAluno=" + cpfAluno + ", nomeAluno=" + nomeAluno
				+ ", idPlano=" + idPlano + ", nomePlano=" + nomePlano + ", valorTotal=" + valorTotal
				+ ", dataPagamento=" + dataPagamento + ", tipoPagamento=" + tipoPagamento + ", status=" + status + "]";
	}

	public static Pagamento fromAluno(Aluno aluno) {

		Pagamento p = new Pagamento();
		p.setCpfAluno(aluno.getCpf());
		p.setNomeAluno(aluno.getNome());
		p.setNomePlano(aluno.getAssinatura().getPlano().getNome());
		p.setValorTotal(aluno.getAssinatura().getPlano().getPreco());
		p.setDataPagamento(new Date());
		p.setTipoPagamento(null);
		p.setStatus(StatusPagamento.ABERTO);
		return p;

	}

}
