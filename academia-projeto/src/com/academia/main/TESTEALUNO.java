package com.academia.main;

import java.io.IOException;

import com.academia.entities.Endereco;
import com.academia.exception.CepNotFound;
import com.academia.util.Utils;

public class TESTEALUNO {

	public static void main(String[] args){
		
		Endereco end = null;
		if(end == null) {
			try {
				 end = Utils.pegarCep("asdasas");
			} catch (CepNotFound | IOException e) {
				System.out.println("PEGOU");
				System.out.println(e.getMessage());
			}	
		}
		
		System.out.println("teste: " + end);
		
		/*
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
