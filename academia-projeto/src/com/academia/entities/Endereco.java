package com.academia.entities;

public class Endereco {

	private String cep;
	private String bairro;
	private String logradouro;

	public Endereco(String cep, String bairro, String logradouro) {
		super();
		this.cep = cep;
		this.bairro = bairro;
		this.logradouro = logradouro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@Override
	public String toString() {
		return "Endereco [cep=" + cep + ", bairro=" + bairro + ", logradouro=" + logradouro + "]";
	}
	
}
