package com.academia.control;

import java.util.List;

import com.academia.dao.DaoFactory;
import com.academia.dao.PlanoDao;
import com.academia.entities.Plano;

public class PlanoControl {

	private PlanoDao planoConn = DaoFactory.getPlanoDao();
	
	public List<Plano> getPlanos() {
		return planoConn.findAll();
	}
	
}
