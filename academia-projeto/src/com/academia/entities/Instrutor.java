package com.academia.entities;

import java.util.ArrayList;
import java.util.List;

public class Instrutor extends Pessoa {

	private boolean ativo;
	private String especialidade;

	private List<Plano> planos = new ArrayList<>();

	public Instrutor() {

	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public List<Plano> getPlanos() {
		return planos;
	}
}
