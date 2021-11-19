package com.academia.main;

import java.util.Date;
import java.util.List;

import com.academia.dao.PagamentoDao;
import com.academia.entities.Pagamento;
import com.academia.entities.StatusPagamento;
import com.academia.entities.TipoPagamento;
import com.academia.factory.DaoFactory;

public class TESTEALUNO {

	public static void main(String[] args) {

		PagamentoDao pagamentoConn = DaoFactory.getPagamentoDao();
		Pagamento p1 = new Pagamento();
		p1.setCpfAluno("1");
		p1.setNomeAluno("TESTE 2");
		p1.setIdPlano(1);
		p1.setNomePlano("PLANO PAGAMENTO 2");
		p1.setValorTotal(199d);
		p1.setDataPagamento(new Date());
		p1.setTipoPagamento(TipoPagamento.CARTAO_CREDITO);
		p1.setStatus(StatusPagamento.CANCELADO);

		pagamentoConn.insert(p1);

		List<Pagamento> p2 = pagamentoConn.findAll();

		p2.forEach(System.out::println);

		/*
		 * ] InstrutorDao instrutorConn = DaoFactory.getInstrutorDao(); List<Instrutor>
		 * instrutores = instrutorConn.findAll();
		 * 
		 * for (Instrutor i : instrutores) { System.out.print(i.getNome() + " - ");
		 * System.out.println(i.getPlanos()); }
		 * 
		 * AlunoDao alunoConn = DaoFactory.getAlunoDao();
		 * 
		 * List<Aluno> alunos = alunoConn.findAll(); Aluno a1 =
		 * alunoConn.findByCpf("4"); System.out.println(a1); System.out.println("\n");
		 * System.out.println(alunos); List<Aluno> a1 = alunoConn.findAll(); List<Aluno>
		 * a2 = alunoConn.findAll();
		 * 
		 * System.out.println(a1.get(0).getAssinatura().getPlano() ==
		 * a2.get(0).getAssinatura().getPlano());
		 * 
		 * /* Aluno a1 = alunoConn.findByCpf("1"); Aluno a2 = alunoConn.findByCpf("2");
		 * System.out.println("a1 " + a1.getAssinatura().getPlano());
		 * System.out.println("a2 " + a2.getAssinatura().getPlano()); Plano p1 =
		 * a1.getAssinatura().getPlano(); Plano p2 = a2.getAssinatura().getPlano();
		 * 
		 * PlanoDao planoConn = DaoFactory.getPlanoDao(); Plano plano = new Plano();
		 * plano.setNome("RONALDO"); plano.setDescricao("RONALDINHO GAUCHO");
		 * plano.setPreco(100d); plano.setDuracao(10);
		 * 
		 * plano = planoConn.insert(plano); System.out.println("Id Plano: " +
		 * plano.getIdPlano()); System.out.println("Nome: " + plano.getNome());
		 * System.out.println("Descrição: " + plano.getDescricao());
		 * System.out.println("Duração: " + plano.getDuracao());
		 * System.out.println("Preço: " + plano.getPreco());
		 * 
		 * /* List<Plano> planos1 = planoConn.findAll(); List<Plano> planos2 =
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
