package com.academia.main;

import com.academia.dao.PlanoDao;
import com.academia.entities.Plano;
import com.academia.factory.DaoFactory;

public class TESTEALUNO {

	public static void main(String[] args) {

		PlanoDao planoConn = DaoFactory.getPlanoDao();
		Plano plano = new Plano();
		plano.setNome("RONALDO");
		plano.setDescricao("RONALDINHO GAUCHO");
		plano.setPreco(100d);
		plano.setDuracao(10);

		plano = planoConn.insert(plano);
		System.out.println("Id Plano: " + plano.getIdPlano());
		System.out.println("Nome: " + plano.getNome());
		System.out.println("Descrição: " + plano.getDescricao());
		System.out.println("Duração: " + plano.getDuracao());
		System.out.println("Preço: " + plano.getPreco());

		/*
		 * List<Plano> planos1 = planoConn.findAll(); List<Plano> planos2 =
		 * planoConn.findAll();
		 * 
		 * // TESTE 1 planos1.add(new Plano(101, "CICLISMO", "TESTE", 99.99, 100));
		 * 
		 * System.out.println(planos1); System.out.println(planos2);
		 * 
		 * // System.out.println(a.getAssinatura()); //
		 * System.out.println(a.getAssinatura().getPlano());
		 * 
		 * /* AlunoDao alunoConn = DaoFactory.getAlunoDao(); boolean excluir =
		 * alunoConn.deleteByCpf("9111111"); System.out.println(excluir);
		 * 
		 * PlanoDao planoConn = DaoFactory.getPlanoDao(); List<Plano> planos =
		 * planoConn.findAll();
		 * 
		 * Plano plano = planoConn.findById(1);
		 * 
		 * System.out.println("Nome: " + plano.getNome());
		 * System.out.println("Descrição: " + plano.getDescricao());
		 * System.out.println("Duração: " + plano.getDuracao());
		 * System.out.println("Preço: " + plano.getPreco());
		 */
		/*
		 * AlunoDao alunoConn = DaoFactory.getAlunoDao();
		 * 
		 * List<AlunoPlanoDTO> alunosPlanos = alunoConn.findAllAlunoPlano();
		 * System.out.println(alunosPlanos);
		 * 
		 * Aluno aluno = alunoConn.findById("12345"); // System.out.println(aluno); /*
		 * Endereco end = null; if(end == null) { try { end = Utils.pegarCep("asdasas");
		 * } catch (CepNotFound | IOException e) { System.out.println("PEGOU");
		 * System.out.println(e.getMessage()); } }
		 * 
		 * System.out.println("teste: " + end);
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
