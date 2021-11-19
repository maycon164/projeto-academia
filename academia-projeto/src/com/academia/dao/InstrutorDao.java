package com.academia.dao;

import java.util.List;

import com.academia.dto.InstrutorDTO;
import com.academia.entities.Instrutor;

public interface InstrutorDao {

	List<InstrutorDTO> findAllByPlano(int idPlano);

	boolean insert(Instrutor instrutor);

	List<Instrutor> findAll();
	
	boolean deleteByCpf(String cpf);

	boolean update(Instrutor i);
}
