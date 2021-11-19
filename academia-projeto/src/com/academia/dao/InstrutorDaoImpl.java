package com.academia.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.academia.db.DB;
import com.academia.dto.InstrutorDTO;
import com.academia.entities.Instrutor;
import com.academia.entities.Plano;
import com.academia.factory.DaoFactory;

public class InstrutorDaoImpl implements InstrutorDao {

	private Connection conn;
	private PlanoDao planoConn = DaoFactory.getPlanoDao();

	private static Map<String, Instrutor> instrutores = new LinkedHashMap<>();

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

				instrutoresDTO.add(instantiateInstrutorDTO(rs));

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

	private InstrutorDTO instantiateInstrutorDTO(ResultSet rs) throws SQLException {

		return new InstrutorDTO(rs.getString("cpf"), rs.getString("instrutor"));

	}

	@SuppressWarnings("resource")
	@Override
	public boolean insert(Instrutor instrutor) {
		PreparedStatement ps = null;

		try {
			String sql = "INSERT INTO pessoa (cpf, nome, email, sexo, nascimento) VALUES (?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, instrutor.getCpf());
			ps.setString(2, instrutor.getNome());
			ps.setString(3, instrutor.getEmail());
			ps.setString(4, instrutor.getSexo().toString());
			ps.setDate(5, new Date(System.currentTimeMillis()));

			ps.execute();

			// inserindo na tabela de instrutor agora

			sql = "INSERT INTO instrutor (cpf, ativo, especializacao) VALUES (?, ?, ?) ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, instrutor.getCpf());
			ps.setBoolean(2, instrutor.isAtivo());
			ps.setString(3, instrutor.getEspecialidade());

			ps.execute();

			boolean insercao = vincularPlano(instrutor.getCpf(), instrutor.getPlanos());

			return insercao;
		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			DB.closeStatement(ps);
		}
		return false;
	}

	public boolean vincularPlano(String cpf, List<Plano> planos) {

		PreparedStatement ps = null;

		for (Plano plano : planos) {
			String sql = "INSERT INTO instrutor_plano (cpf_instrutor, id_plano) VALUES (?, ?)";

			try {

				ps = conn.prepareStatement(sql);
				ps.setString(1, cpf);
				ps.setInt(2, plano.getIdPlano());
				ps.execute();

			} catch (SQLException e) {

				e.printStackTrace();
				return false;

			} finally {

				DB.closeStatement(ps);

			}

		}

		return true;
	}

	@Override
	public List<Instrutor> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM vw_instrutor";
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			List<Instrutor> auxiliar = new ArrayList<>();

			while (rs.next()) {
				if (!instrutores.containsKey(rs.getString("cpf"))) {
					Instrutor instrutor = instantiateInstrutor(rs);
					instrutores.put(instrutor.getCpf(), instrutor);
				}
			}

			for (Instrutor a : instrutores.values()) {
				auxiliar.add(a);
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

	private Instrutor instantiateInstrutor(ResultSet rs) throws SQLException {
		Instrutor instrutor = new Instrutor();
		instrutor.setCpf(rs.getString("cpf"));
		instrutor.setNome(rs.getString("instrutor"));
		instrutor.setSexo(rs.getString("sexo").charAt(0));
		instrutor.setEmail(rs.getString("email"));

		List<Plano> planos = planoConn.findAllPlanosByInstrutor(instrutor.getCpf());

		for (Plano plano : planos) {
			instrutor.getPlanos().add(plano);
		}

		return instrutor;
	}

	@Override
	public boolean deleteByCpf(String cpf) {
		PreparedStatement ps = null;

		try {
			String sql = "EXECUTE deletar_instrutor @cpf = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cpf);

			ps.execute();

			return true;
		} catch (SQLException e) {

			return false;

		} finally {
			DB.closeStatement(ps);
		}
	}

}
