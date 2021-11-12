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
	public Aluno findById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Aluno aluno) {
		
		
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

	private AlunoDTO instantiateAlunoDTO(ResultSet rs) throws SQLException {

		return new AlunoDTO(rs.getString("cpf"), rs.getString("aluno"), rs.getInt("idade"),
				rs.getDate("data_matricula"), rs.getBoolean("ativo"));
	}

	@Override
	public List<AlunoDTO> findAllByPlano() {
		// TODO Auto-generated method stub
		return null;
	}

}
