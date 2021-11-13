package com.academia.dao;

import java.util.List;

import com.academia.dto.InstrutorDTO;

public interface InstrutorDao {
	
	List<InstrutorDTO> findAllByPlano(int idPlano);
	
}
