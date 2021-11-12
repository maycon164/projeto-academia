package com.academia.main;

import java.util.List;

import com.academia.dao.AlunoDao;
import com.academia.dao.AlunoDaoImpl;
import com.academia.db.DB;
import com.academia.dto.AlunoDTO;

public class TESTEALUNO {

	public static void main(String[] args) {
		AlunoDao alunoConn = new AlunoDaoImpl(DB.getConnection());

		List<AlunoDTO> alunos = alunoConn.findAll();

		alunos.forEach(a -> {
			System.out.println("Nome: " + a.getNome() + ", CPF: " + a.getCpf());
		});

	}

}
