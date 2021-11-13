package com.academia.dao;

import com.academia.db.DB;

public class DaoFactory {

	public static AlunoDao getAlunoDao() {
		return new AlunoDaoImpl(DB.getConnection());
	}

	public static PlanoDao getPlanoDao() {
		return new PlanoDaoImpl(DB.getConnection());
	}

	public static InstrutorDao getInstrutorDao() {
		return new InstrutorDaoImpl(DB.getConnection());
	}

}
