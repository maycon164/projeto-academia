package com.academia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.academia.db.DB;
import com.academia.entities.Aluno;
import com.academia.entities.Assinatura;
import com.academia.entities.Pagamento;
import com.academia.exception.CpfException;
import com.academia.factory.DaoFactory;
import com.microsoft.sqlserver.jdbc.SQLServerException;

public class AlunoDaoImpl implements AlunoDao {

	private Connection conn;
	private PlanoDao planoConn = DaoFactory.getPlanoDao();
	private PagamentoDao pagamentoConn = DaoFactory.getPagamentoDao();
	private static Map<String, Aluno> alunos = new LinkedHashMap<>();

	public AlunoDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Aluno> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM vw_aluno";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {

				if (!alunos.containsKey(rs.getString("cpf"))) {

					Aluno aluno = instantiateAluno(rs);
					alunos.put(aluno.getCpf(), aluno);
				}

			}

			List<Aluno> aux = new ArrayList<>();

			for (Aluno aluno : alunos.values()) {
				aux.add(aluno);
			}

			return aux;
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			DB.closeStatement(ps);
			DB.closeResultSet(rs);

		}

		return null;
	}

	@Override
	public Aluno findByCpf(String cpf) {

		if (alunos.containsKey(cpf)) {
			return alunos.get(cpf);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM vw_aluno WHERE cpf = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cpf);
			rs = ps.executeQuery();

			Aluno aluno = null;

			if (rs.next()) {

				if (!alunos.containsKey(cpf)) {

					aluno = instantiateAluno(rs);
					alunos.put(cpf, aluno);
				}

			}

			return aluno;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);

		}

		return null;
	}

	@Override
	public void insert(Aluno aluno) throws CpfException {
		PreparedStatement ps = null;

		try {
			String sql = "INSERT INTO pessoa "
					+ "(cpf, nome, nascimento, email, telefone, cep, bairro, rua, num, sexo, data_matricula) "
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, aluno.getCpf());
			ps.setString(2, aluno.getNome());
			ps.setDate(3, new java.sql.Date(aluno.getNascimento().getTime()));
			ps.setString(4, aluno.getEmail());
			ps.setString(5, aluno.getTelefone());
			ps.setString(6, aluno.getCep());
			ps.setString(7, aluno.getBairro());
			ps.setString(8, aluno.getRua());
			ps.setString(9, aluno.getNum());
			ps.setString(10, aluno.getSexo().toString());
			ps.setDate(11, (aluno.getData_matricula() == null) ? new java.sql.Date(System.currentTimeMillis())
					: new java.sql.Date(aluno.getData_matricula().getTime()));

			ps.execute();

			// SE CONSEGUIU INSERIR A PESSOA AGORA PRECISSO REGISTRAR COMO ALUNO
			String sqlAluno = "INSERT INTO aluno (cpf, ativo, observacoes) VALUES (?, ?, ?)";
			ps = conn.prepareStatement(sqlAluno);
			ps.setString(1, aluno.getCpf());
			ps.setBoolean(2, aluno.isAtivo());
			ps.setString(3, aluno.getObservacao());

			int rows = ps.executeUpdate();

			if (rows > 0) {
				System.out.println("Aluno Inserido com Sucesso");
			}

		} catch (SQLServerException e) {
			// ERRO AO INSERIR (PRIMARY KEY DUPLICADA);
			throw new CpfException("Cpf já cadastrado");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}

	}

	@Override
	public Assinatura assinarPlano(Assinatura assinatura) {
		PreparedStatement ps = null;

		try {
			String sql = "INSERT INTO aluno_plano (cpf_aluno, id_plano, data_inicio, data_expiracao) VALUES (?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, assinatura.getAluno().getCpf());
			ps.setInt(2, assinatura.getPlano().getIdPlano());
			ps.setDate(3, new java.sql.Date(assinatura.getDataInicio().getTime()));
			ps.setDate(4, new java.sql.Date(assinatura.getDataExpiracao().getTime()));

			int rows = ps.executeUpdate();

			if (rows > 0) {
				System.out.println("ASSINATURA VINCULAD AO CPF DO ALUNO");
			}

			return assinatura;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}

		return null;
	}

	public void gerarPagamento(Aluno aluno) {
		pagamentoConn.insert(Pagamento.fromAluno(aluno));
	}

	private Aluno instantiateAluno(ResultSet rs) throws SQLException {

		Aluno aluno = new Aluno(rs.getString("cpf"), rs.getString("aluno"), rs.getDate("nascimento"),
				rs.getString("email"), rs.getString("telefone"), rs.getString("sexo").charAt(0), rs.getString("cep"),
				rs.getString("bairro"), rs.getString("rua"), rs.getString("num"), rs.getDate("data_matricula"),
				rs.getBoolean("ativo"), rs.getString("observacoes"));

		Assinatura assinatura = findAssinaturaByCpf(aluno.getCpf());
		assinatura.setAluno(aluno);
		aluno.setAssinatura(assinatura);

		return aluno;
	}

	@Override
	public boolean update(Aluno aluno) {

		PreparedStatement ps = null;

		try {
			String sql = "UPDATE pessoa SET cpf = ?, nome = ?, nascimento = ?, email = ?, telefone = ?, cep =?, "
					+ "bairro = ?, rua = ?, num = ?, sexo = ? WHERE cpf = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, aluno.getCpf());
			ps.setString(2, aluno.getNome());
			ps.setDate(3, new java.sql.Date(aluno.getNascimento().getTime()));
			ps.setString(4, aluno.getEmail());
			ps.setString(5, aluno.getTelefone());
			ps.setString(6, aluno.getCep());
			ps.setString(7, aluno.getBairro());
			ps.setString(8, aluno.getRua());
			ps.setString(9, aluno.getNum());
			ps.setString(10, aluno.getSexo().toString());
			ps.setString(11, aluno.getCpf());

			ps.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}

		return false;
	}

	private Assinatura instantiateAssinatura(ResultSet rs) throws SQLException {

		Assinatura assinatura = new Assinatura();
		assinatura.setId(rs.getInt("id_aluno_plano"));
		assinatura.setPlano(planoConn.findById(rs.getInt("id_plano")));
		assinatura.setDataInicio(rs.getDate("data_inicio"));
		assinatura.setDataExpiracao(rs.getDate("data_expiracao"));

		return assinatura;
	}

	private Assinatura findAssinaturaByCpf(String cpf) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM aluno_plano WHERE cpf_aluno = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cpf);
			rs = ps.executeQuery();

			Assinatura assinatura = null;

			if (rs.next()) {
				assinatura = instantiateAssinatura(rs);
			}
			return assinatura;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean deleteByCpf(String cpf) {
		PreparedStatement ps = null;
		boolean excluir = false;
		try {

			String sql = "EXECUTE deletar_aluno @cpf = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cpf);

			int rows = ps.executeUpdate();
			System.out.println(rows);

			if (rows > 0) {
				System.out.println("EXLUI O ALUNO COM CPF " + cpf);
				excluir = true;
			}

			return excluir;

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			DB.closeStatement(ps);
		}
		return excluir;
	}

}
