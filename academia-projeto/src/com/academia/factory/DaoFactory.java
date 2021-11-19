package com.academia.factory;

import com.academia.dao.AlunoDao;
import com.academia.dao.AlunoDaoImpl;
import com.academia.dao.InstrutorDao;
import com.academia.dao.InstrutorDaoImpl;
import com.academia.dao.PagamentoDao;
import com.academia.dao.PagamentoDaoImpl;
import com.academia.dao.PlanoDao;
import com.academia.dao.PlanoDaoImpl;
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

	public static PagamentoDao getPagamentoDao() {
		return new PagamentoDaoImpl(DB.getConnection());
	}

}
