package com.academia.main;

import java.util.List;

import com.academia.dao.AlunoDao;
import com.academia.dao.DaoFactory;
import com.academia.dto.AlunoPlanoDTO;

public class TESTEALUNO {

	public static void main(String[] args) {

		AlunoDao alunoConn = DaoFactory.getAlunoDao();

		List<AlunoPlanoDTO> alunosPlanos = alunoConn.findAllAlunoPlano();
		System.out.println(alunosPlanos);

		// Aluno aluno = alunoConn.findById("12345");
		// System.out.println(aluno);
		/*
		 * Endereco end = null; if(end == null) { try { end = Utils.pegarCep("asdasas");
		 * } catch (CepNotFound | IOException e) { System.out.println("PEGOU");
		 * System.out.println(e.getMessage()); } }
		 * 
		 * System.out.println("teste: " + end);
		 * 
		 * /*
		 * 
		 * Date data = new Date(); System.out.println(data); Calendar cal =
		 * Calendar.getInstance(); cal.setTime(data); cal.add(Calendar.DATE, 24); data =
		 * cal.getTime(); System.out.println(data);
		 * 
		 * AlunoDao alunoConn = DaoFactory.getAlunoDao();
		 * 
		 * 
		 * /* InstrutorDao instrutorConn = DaoFactory.getInstrutorDao();
		 * List<InstrutorDTO> instrutores = instrutorConn.findAllByPlano(1);
		 * 
		 * instrutores.forEach(System.out::println);
		 * 
		 * 
		 * PlanoDao planoConn = DaoFactory.getPlanoDao(); List<Plano> planos =
		 * planoConn.findAll(); System.out.println(planos);
		 * 
		 * /*
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
