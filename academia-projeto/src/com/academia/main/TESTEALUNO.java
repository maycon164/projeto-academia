package com.academia.main;

import com.academia.dao.AlunoDao;
import com.academia.dao.AlunoDaoImpl;
import com.academia.db.DB;
import com.academia.entities.Aluno;

public class TESTEALUNO {

	public static void main(String[] args) {
		AlunoDao alunoConn = new AlunoDaoImpl(DB.getConnection());
		
		Aluno aluno = alunoConn.findById("10866672044");
		
		System.out.println(aluno);
		
		/*
		 * List<AlunoDTO> alunos = alunoConn.findAll();
		 * alunos.forEach(System.out::println);
		 * Aluno aluno = new Aluno("50409978846", "RONALDINHO GAUCHO", new
		 * Date(System.currentTimeMillis()), "mayconfelipe164@gmail.com", "11943166799",
		 * 'M', "IGUATEMI", "Anecy Rocha", "158", new Date(System.currentTimeMillis()));
		 * alunoConn.insert(aluno);
		 */

	}

}
