package com.academia.dao;

import java.util.List;

import com.academia.entities.Aluno;
import com.academia.entities.Assinatura;
import com.academia.exception.CpfException;

public interface AlunoDao {

	void insert(Aluno aluno) throws CpfException;

	Aluno findByCpf(String cpf);

	boolean update(Aluno aluno);

	List<Aluno> findAll();

	boolean deleteByCpf(String cpf);

	Assinatura assinarPlano(Assinatura assinatura);

	void gerarPagamento(Aluno aluno);

}
