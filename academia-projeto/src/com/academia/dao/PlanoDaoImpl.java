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
import com.academia.entities.Plano;

public class PlanoDaoImpl implements PlanoDao {

	public Connection conn;

	// GARANTINDO QUE NÃO HAJA MULTIPLAS INSTANCIAS DESNECESSARIAS
	public Map<Integer, Plano> planos = new LinkedHashMap<>();

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

			while (rs.next()) {

				if (!planos.containsKey(rs.getInt("id_plano"))) {
					Plano plano = instantiatePlano(rs);
					planos.put(plano.getIdPlano(), plano);
				}

			}

			List<Plano> auxiliar = new ArrayList<>();

			for (Plano plano : planos.values()) {
				auxiliar.add(plano);
			}

			return auxiliar;

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

		if (planos.containsKey(id)) {
			return planos.get(id);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM plano WHERE id_plano = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			rs = ps.executeQuery();
			Plano plano = null;

			if (rs.next()) {
				plano = instantiatePlano(rs);
			}

			return plano;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}

		return null;
	}

	@Override
	public Plano insert(Plano plano) {

		PreparedStatement ps = null;

		try {
			String sql = "INSERT INTO plano(nome, descricao, preco, duracao) VALUES (?, ?, ?, ?)";
			ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, plano.getNome());
			ps.setString(2, plano.getDescricao());
			ps.setDouble(3, plano.getPreco());
			ps.setInt(4, plano.getDuracao());

			int rows = ps.executeUpdate();

			if (rows > 0) {
				ResultSet rs = ps.getGeneratedKeys();

				while (rs.next()) {
					plano.setIdPlano(rs.getInt(1));
				}

				DB.closeResultSet(rs);

			}

			return plano;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}

		return null;
	}

}
