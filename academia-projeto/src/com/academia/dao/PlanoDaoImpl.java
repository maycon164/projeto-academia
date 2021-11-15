package com.academia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.academia.db.DB;
import com.academia.entities.Plano;

public class PlanoDaoImpl implements PlanoDao {

	public Connection conn;

	public PlanoDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Plano> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM plano";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			List<Plano> planos = new ArrayList<>();

			while (rs.next()) {
				planos.add(instantiatePlano(rs));
			}

			return planos;
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			DB.closeStatement(ps);
			DB.closeResultSet(rs);

		}

		return null;
	}

	private Plano instantiatePlano(ResultSet rs) throws SQLException {
		
		return new Plano(rs.getInt("id_plano"), rs.getString("nome"), rs.getString("Descricao"), rs.getDouble("preco"),
				rs.getInt("duracao"));
		
	}

	@Override
	public Plano findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
