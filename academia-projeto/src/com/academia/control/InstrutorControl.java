package com.academia.control;

import java.util.List;

import com.academia.dao.DaoFactory;
import com.academia.dao.InstrutorDao;
import com.academia.dto.InstrutorDTO;

public class InstrutorControl {
	private InstrutorDao instrutorConn = DaoFactory.getInstrutorDao();

	public List<InstrutorDTO> getInstrutoresByPlano(int idPlano) {
		
		return instrutorConn.findAllByPlano(idPlano);
		
	}
}
