package com.academia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.academia.db.DB;
import com.academia.entities.Pagamento;
import com.academia.entities.StatusPagamento;
import com.academia.entities.TipoPagamento;

public class PagamentoDaoImpl implements PagamentoDao {

	private Connection conn = null;

	public PagamentoDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Pagamento> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Pagamento> pagamentos = new ArrayList<>();

		try {
			String sql = "SELECT * FROM pagamento";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				pagamentos.add(instantiatePagamento(rs));
			}

			return pagamentos;
		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}

		return null;
	}

	@Override
	public boolean insert(Pagamento pagamento) {
		PreparedStatement ps = null;

		try {
			String sql = "INSERT INTO pagamento "
					+ "(cpf_aluno, nome_aluno, id_plano, plano, valor_total, data_pagamento, tipo_pagamento, status) VALUES  "
					+ "(?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pagamento.getCpfAluno());
			ps.setString(2, pagamento.getNomeAluno());
			ps.setInt(3, pagamento.getIdPlano());
			ps.setString(4, pagamento.getNomePlano());
			ps.setDouble(5, pagamento.getValorTotal());
			ps.setDate(6, new java.sql.Date(pagamento.getDataPagamento().getTime()));
			ps.setString(7, (pagamento.getTipoPagamento() != null) ? pagamento.getTipoPagamento().toString() : "");
			ps.setString(8, pagamento.getStatus().toString());

			int rows = ps.executeUpdate();

			if (rows > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				while (rs.next()) {
					pagamento.setIdPagamento(rs.getInt(1));
				}
				DB.closeResultSet(rs);
			}

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean makePayment(int idPagamento, String formaPagamento) {
		PreparedStatement ps = null;

		try {
			String sql = "UPDATE pagamento SET tipo_pagamento = ?, STATUS = ? WHERE id_pagamento = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, formaPagamento);
			ps.setString(2, "PAGO");
			ps.setInt(3, idPagamento);

			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
		}
		return false;
	}

	private static Pagamento instantiatePagamento(ResultSet rs) throws SQLException {
		Pagamento pagamento = new Pagamento();

		pagamento.setIdPagamento(rs.getInt("id_pagamento"));
		pagamento.setCpfAluno(rs.getString("cpf_aluno"));
		pagamento.setNomeAluno(rs.getString("nome_aluno"));
		pagamento.setIdPlano(rs.getInt("id_plano"));
		pagamento.setNomePlano(rs.getString("plano"));
		pagamento.setValorTotal(rs.getDouble("valor_total"));
		pagamento.setDataPagamento(rs.getDate("data_pagamento"));

		String tipoPagamento = rs.getString("tipo_pagamento");
		pagamento.setTipoPagamento((tipoPagamento.equals("")) ? null : TipoPagamento.valueOf(tipoPagamento));

		pagamento.setStatus(StatusPagamento.valueOf(rs.getString("status")));

		return pagamento;
	}

}
