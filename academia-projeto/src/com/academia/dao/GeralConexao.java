package com.academia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.academia.db.DB;
import com.academia.dto.InfoDTO;

public class GeralConexao {
	private Connection conn;

	public GeralConexao(Connection conn) {
		this.conn = conn;
	}

	public int[] infoGeral() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		int[] nums = { 0, 0, 0 };

		try {
			String sql = "SELECT * FROM vw_info_geral";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next()) {
				nums[0] = rs.getInt("qtdAluno");
				nums[1] = rs.getInt("qtdInstrutor");
				nums[2] = rs.getInt("qtdPlano");
				return nums;
			}

			return nums;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DB.closeStatement(ps);
			DB.closeResultSet(rs);

		}
		return nums;
	}

	public InfoDTO planoMaisAssinado() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT TOP 1 * FROM vw_plano_aluno ORDER BY qtdAluno DESC";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next()) {
				InfoDTO info = new InfoDTO();
				info.setNomePlano(rs.getString("nome"));
				info.setQtd(rs.getInt("qtdAluno"));
				return info;
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

	public InfoDTO planoComMaisInstrutores() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT TOP 1  * FROM vw_plano_instrutor ORDER BY qtdInstrutor DESC";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs.next()) {
				InfoDTO info = new InfoDTO();
				info.setNomePlano(rs.getString("nome"));
				info.setQtd(rs.getInt("qtdInstrutor"));
				return info;
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

	public int infoPendencias() {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT COUNT(cpf_aluno) AS status FROM pagamento WHERE status = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "ABERTO");
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("status");
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		return 0;
	}
}
