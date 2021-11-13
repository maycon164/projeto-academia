package com.academia.main;

import java.util.List;

import com.academia.dao.DaoFactory;
import com.academia.dao.InstrutorDao;
import com.academia.dto.InstrutorDTO;

public class TESTEALUNO {

	public static void main(String[] args) {

		InstrutorDao instrutorConn = DaoFactory.getInstrutorDao();
		List<InstrutorDTO> instrutores = instrutorConn.findAllByPlano(1);

		instrutores.forEach(System.out::println);

		/*
		 * PlanoDao planoConn = DaoFactory.getPlanoDao(); List<Plano> planos =
		 * planoConn.findAll(); System.out.println(planos);
		 * 
		 * /* AlunoDao alunoConn = DaoFactory.getAlunoDao();
		 * 
		 * Aluno aluno = alunoConn.findById("10866672044");
		 * 
		 * System.out.println(aluno);
		 * 
		 * 
		 * List<AlunoDTO> alunos = alunoConn.findAll();
		 * alunos.forEach(System.out::println); Aluno aluno = new Aluno("50409978846",
		 * "RONALDINHO GAUCHO", new Date(System.currentTimeMillis()),
		 * "mayconfelipe164@gmail.com", "11943166799", 'M', "IGUATEMI", "Anecy Rocha",
		 * "158", new Date(System.currentTimeMillis())); alunoConn.insert(aluno);
		 */

	}

}
