package com.academia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.academia.db.DB;
import com.academia.dto.InstrutorDTO;

public class InstrutorDaoImpl implements InstrutorDao {

	private Connection conn;

	public InstrutorDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<InstrutorDTO> findAllByPlano(int idPlano) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM [dbo].[func_instrutoresDTO_por_plano](?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, idPlano);
			rs = ps.executeQuery();

			List<InstrutorDTO> instrutoresDTO = new ArrayList<>();

			while (rs.next()) {

				instrutoresDTO.add(instantiateInstrutor(rs));

			}

			return instrutoresDTO;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		return null;
	}

	private InstrutorDTO instantiateInstrutor(ResultSet rs) throws SQLException {

		return new InstrutorDTO(rs.getString("cpf"), rs.getString("instrutor"));

	}

}
