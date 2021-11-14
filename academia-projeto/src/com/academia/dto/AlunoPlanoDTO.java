package com.academia.dto;

import java.util.Date;

public class AlunoPlanoDTO {

	private String aluno;
	private String cpf;
	private String plano;
	private Integer idPlano;
	private Integer duracao;
	private Date dataInicio;
	private Date dataExpiracao;

	public AlunoPlanoDTO(String aluno, String cpf, String plano, Integer idPlano, Integer duracao, Date dataInicio,
			Date dataExpiracao) {

		this.aluno = aluno;
		this.cpf = cpf;
		this.plano = plano;
		this.idPlano = idPlano;
		this.duracao = duracao;
		this.dataInicio = dataInicio;
		this.dataExpiracao = dataExpiracao;

	}

	public String getAluno() {
		return aluno;
	}

	public void setAluno(String aluno) {
		this.aluno = aluno;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPlano() {
		return plano;
	}

	public void setPlano(String plano) {
		this.plano = plano;
	}

	public Integer getIdPlano() {
		return idPlano;
	}

	public void setIdPlano(Integer idPlano) {
		this.idPlano = idPlano;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
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

	@Override
	public String toString() {
		return "\nAlunoPlanoDTO [aluno=" + aluno + ", cpf=" + cpf + ", plano=" + plano + ", idPlano=" + idPlano
				+ ", duracao=" + duracao + ", dataInicio=" + dataInicio + ", dataExpiracao=" + dataExpiracao + "]";
	}

	
	
}
