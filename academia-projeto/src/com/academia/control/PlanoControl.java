package com.academia.control;

import java.util.List;

import com.academia.dao.PlanoDao;
import com.academia.entities.Plano;
import com.academia.factory.DaoFactory;

public class PlanoControl {

	private PlanoDao planoConn = DaoFactory.getPlanoDao();
	
	public List<Plano> getPlanos() {
		return planoConn.findAll();
	}
	
}
