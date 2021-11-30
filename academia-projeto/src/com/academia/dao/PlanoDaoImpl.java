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

	// GARANTINDO QUE NÃO HAJA MULTIPLAS INSTANCIAS
	public static Map<Integer, Plano> planos = new LinkedHashMap<>();

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

				if (!planos.containsKey(id)) {
					plano = instantiatePlano(rs);
					planos.put(plano.getIdPlano(), plano);
					return plano;
				}

			}

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
	public Plano insert(Plano plano) {

		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "INSERT INTO plano(nome, descricao, preco, duracao) VALUES (?, ?, ?, ?)";
			ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, plano.getNome());
			ps.setString(2, plano.getDescricao());
			ps.setDouble(3, plano.getPreco());
			ps.setInt(4, plano.getDuracao());

			int rows = ps.executeUpdate();

			if (rows > 0) {
				rs = ps.getGeneratedKeys();

				while (rs.next()) {
					plano.setIdPlano(rs.getInt(1));
				}

				planos.put(plano.getIdPlano(), plano);
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
	public boolean deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			String sql = "DELETE plano WHERE id_plano = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			int rows = ps.executeUpdate();

			if (rows > 0) {
				return true;
			}

			return false;
		} catch (SQLException e) {
			System.out.println("HOUVE UM ERRO AQUI");
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}
		return false;
	}

	@Override
	public boolean update(Plano plano) {
		PreparedStatement ps = null;

		try {

			String sql = "UPDATE plano SET nome = ?, descricao = ?, preco = ?, duracao = ? WHERE id_plano = ? ";

			ps = conn.prepareStatement(sql);

			ps.setString(1, plano.getNome());
			ps.setString(2, plano.getDescricao());
			ps.setDouble(3, plano.getPreco());
			ps.setInt(4, plano.getDuracao());
			ps.setInt(5, plano.getIdPlano());

			int rows = ps.executeUpdate();

			if (rows > 0) {

				System.out.println("LINHAS " + rows + " AFETADAS");
				return true;

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return false;
	}

	@Override
	public List<Plano> findAllPlanosByInstrutor(String cpf) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM instrutor_plano WHERE cpf_instrutor = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cpf);
			rs = ps.executeQuery();

			List<Plano> planos = new ArrayList<>();

			while (rs.next()) {
				planos.add(findById(rs.getInt("id_plano")));
			}

			return planos;
		} catch (Exception e) {

		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}

		return null;
	}

}
