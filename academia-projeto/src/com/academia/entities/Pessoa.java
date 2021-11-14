package com.academia.entities;

import java.util.Date;

public class Pessoa {

	private String cpf;
	private String nome;
	private Date nascimento;
	private String email;
	private String telefone;
	private Character sexo;

	private String cep;
	private String bairro;
	private String rua;
	private String num;
	private Date data_matricula;

	public Pessoa(String cpf, String nome, Date nascimento, String email, String telefone, Character sexo, String cep,
			String bairro, String rua, String num, Date data_matricula) {
		this.cpf = cpf;
		this.nome = nome;
		this.nascimento = nascimento;
		this.email = email;
		this.telefone = telefone;
		this.sexo = sexo;
		this.cep = cep;
		this.bairro = bairro;
		this.rua = rua;
		this.num = num;
		this.data_matricula = data_matricula;
	}

	public Pessoa() {

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

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Character getSexo() {
		return sexo;
	}

	public void setSexo(Character sexo) {
		this.sexo = sexo;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Date getData_matricula() {
		return data_matricula;
	}

	public void setData_matricula(Date data_matricula) {
		this.data_matricula = data_matricula;
	}

	@Override
	public String toString() {
		return "Pessoa [cpf=" + cpf + ", nome=" + nome + ", nascimento=" + nascimento + ", email=" + email
				+ ", telefone=" + telefone + ", sexo=" + sexo + ", cep=" + cep + ", bairro=" + bairro + ", rua=" + rua
				+ ", num=" + num + ", data_matricula=" + data_matricula + "]";
	}

}
