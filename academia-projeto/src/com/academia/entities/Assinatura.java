package com.academia.entities;

import java.util.Date;

public class Assinatura {

	private Integer id;
	private Plano plano;
	private Aluno aluno;
	private Date dataInicio;
	private Date dataExpiracao;

	public Assinatura() {

	}

	public Assinatura(Integer id, Plano plano, Aluno aluno, Date dataInicio, Date dataExpiracao) {
		super();
		this.id = id;
		this.plano = plano;
		this.aluno = aluno;
		this.dataInicio = dataInicio;
		this.dataExpiracao = dataExpiracao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(Date dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

}
