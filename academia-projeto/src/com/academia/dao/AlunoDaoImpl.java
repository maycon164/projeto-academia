package com.academia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.academia.db.DB;
import com.academia.dto.AlunoDTO;
import com.academia.dto.AlunoPlanoDTO;
import com.academia.entities.Aluno;
import com.academia.entities.Assinatura;

public class AlunoDaoImpl implements AlunoDao {

	private Connection conn;

	public AlunoDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Aluno findById(String cpf) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM vw_aluno WHERE cpf = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cpf);
			rs = ps.executeQuery();

			if (rs.next()) {
				Aluno aluno = instantiateAluno(rs);
				return aluno;
			}

			return null;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);

		}

		return null;
	}

	@Override
	// NA VERDADE É O PROCESSO DE INSERIR PESSOA
	public void insert(Aluno aluno) {
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

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}

		return null;
	}

	@Override
	public List<AlunoDTO> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM vw_aluno_dto vad";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			List<AlunoDTO> alunos = new ArrayList<>();

			while (rs.next()) {
				alunos.add(instantiateAlunoDTO(rs));
			}

			return alunos;

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			DB.closeResultSet(rs);
			DB.closeStatement(ps);

		}

		return null;
	}

	@Override
	public List<AlunoDTO> findAllByParameter(String parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AlunoPlanoDTO> findAllAlunoPlano() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM vw_aluno_plano ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			List<AlunoPlanoDTO> alunosPlanos = new ArrayList<>();

			while (rs.next()) {
				alunosPlanos.add(instantiateAlunoPlanoDTO(rs));
			}

			return alunosPlanos;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DB.closeStatement(ps);
			DB.closeResultSet(rs);

		}

		return null;
	}

	private AlunoPlanoDTO instantiateAlunoPlanoDTO(ResultSet rs) throws SQLException {

		return new AlunoPlanoDTO(rs.getString("aluno"), rs.getString("cpf"), rs.getString("plano"),
				rs.getInt("id_plano"), rs.getInt("duracao"), rs.getDate("data_inicio"), rs.getDate("data_expiracao"));

	}

	private AlunoDTO instantiateAlunoDTO(ResultSet rs) throws SQLException {

		return new AlunoDTO(rs.getString("cpf"), rs.getString("aluno"), rs.getInt("idade"),
				rs.getDate("data_matricula"), rs.getBoolean("ativo"));
	}

	private Aluno instantiateAluno(ResultSet rs) throws SQLException {

		Aluno aluno = new Aluno(rs.getString("cpf"), rs.getString("aluno"), rs.getDate("nascimento"),
				rs.getString("email"), rs.getString("telefone"), rs.getString("sexo").charAt(0), rs.getString("cep"),
				rs.getString("bairro"), rs.getString("rua"), rs.getString("num"), rs.getDate("data_matricula"),
				rs.getBoolean("ativo"), rs.getString("observacoes"));

		return aluno;
	}

}
