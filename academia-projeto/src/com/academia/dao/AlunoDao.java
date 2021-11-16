package com.academia.dao;

import java.util.List;

import com.academia.dto.AlunoDTO;
import com.academia.dto.AlunoPlanoDTO;
import com.academia.entities.Aluno;
import com.academia.entities.Assinatura;
import com.academia.exception.CpfRegisteredException;

public interface AlunoDao {

	void insert(Aluno aluno) throws CpfRegisteredException;

	Aluno findByCpf(String cpf);

	void deleteByCpf(String cpf);

	List<AlunoDTO> findAll();

	List<AlunoPlanoDTO> findAllAlunoPlano();

	List<AlunoDTO> findAllByParameter(String parameter);

	Assinatura assinarPlano(Assinatura assinatura);

}
