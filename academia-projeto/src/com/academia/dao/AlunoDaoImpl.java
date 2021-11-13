package com.academia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.academia.db.DB;
import com.academia.dto.AlunoDTO;
import com.academia.entities.Aluno;

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
					+ "(cpf, nome, nascimento, email, telefone, bairro, rua, num, sexo, data_matricula) "
					+ " VALUES (?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, aluno.getCpf());
			ps.setString(2, aluno.getNome());
			ps.setDate(3, new java.sql.Date(aluno.getNascimento().getTime()));
			ps.setString(4, aluno.getEmail());
			ps.setString(5, aluno.getTelefone());
			ps.setString(6, aluno.getBairro());
			ps.setString(7, aluno.getRua());
			ps.setString(8, aluno.getNum());
			ps.setString(9, aluno.getSexo().toString());
			ps.setDate(10, (aluno.getData_matricula() == null) ? new java.sql.Date(System.currentTimeMillis())
					: new java.sql.Date(aluno.getData_matricula().getTime()));
			ps.execute();

			// SE CONSEGUIU INSERIR A PESSOA AGORA PRECISSO REGISTRAR COMO ALUNO
			String sqlAluno = "INSERT INTO aluno (cpf, ativo, observacoes) VALUES (?, ?, ?)";
			ps = conn.prepareStatement(sqlAluno);
			ps.setString(1, aluno.getCpf());
			ps.setBoolean(2, aluno.isAtivo());
			ps.setString(3, aluno.getObservacao());

			ps.execute();
			System.out.println("INSERIDO COM SUCESSO");

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			DB.closeStatement(ps);

		}

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

	private AlunoDTO instantiateAlunoDTO(ResultSet rs) throws SQLException {

		return new AlunoDTO(rs.getString("cpf"), rs.getString("aluno"), rs.getInt("idade"),
				rs.getDate("data_matricula"), rs.getBoolean("ativo"));
	}

	private Aluno instantiateAluno(ResultSet rs) throws SQLException {

		Aluno aluno = new Aluno(rs.getString("cpf"), rs.getString("aluno"), rs.getDate("nascimento"),
				rs.getString("email"), rs.getString("telefone"), rs.getString("sexo").charAt(0), rs.getString("bairro"),
				rs.getString("rua"), rs.getString("num"), rs.getDate("data_matricula"));
		aluno.setAtivo(rs.getBoolean("ativo"));
		aluno.setObservacao(rs.getString("observacoes"));

		return aluno;
	}

}