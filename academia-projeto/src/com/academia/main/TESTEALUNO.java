package com.academia.main;

import java.util.Calendar;
import java.util.Date;

public class TESTEALUNO {

	public static void main(String[] args) {
		Date data = new Date();
		System.out.println(data);
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.DATE, 24);
		data = cal.getTime();
		System.out.println(data);
		
		/*
		 * InstrutorDao instrutorConn = DaoFactory.getInstrutorDao(); List<InstrutorDTO>
		 * instrutores = instrutorConn.findAllByPlano(1);
		 * 
		 * instrutores.forEach(System.out::println);
		 * 
		 * 
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
