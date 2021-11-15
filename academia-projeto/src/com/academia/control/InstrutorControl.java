package com.academia.control;

import java.util.List;

import com.academia.dao.InstrutorDao;
import com.academia.dto.InstrutorDTO;
import com.academia.factory.DaoFactory;

public class InstrutorControl {
	private InstrutorDao instrutorConn = DaoFactory.getInstrutorDao();

	public List<InstrutorDTO> getInstrutoresByPlano(int idPlano) {
		
		return instrutorConn.findAllByPlano(idPlano);
		
	}
}
