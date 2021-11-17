package com.academia.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.academia.db.DB;
import com.academia.dto.InstrutorDTO;
import com.academia.entities.Instrutor;
import com.academia.entities.Plano;

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

}
