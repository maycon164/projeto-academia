package com.academia.dao;

import java.util.List;

import com.academia.dto.AlunoDTO;
import com.academia.entities.Aluno;
import com.academia.entities.Assinatura;

public interface AlunoDao {

	void insert(Aluno aluno);
	Aluno findById(String cpf);

	List<AlunoDTO> findAll();
	List<AlunoDTO> findAllByParameter(String parameter);
	
	Assinatura assinarPlano(Assinatura assinatura);
	
}
