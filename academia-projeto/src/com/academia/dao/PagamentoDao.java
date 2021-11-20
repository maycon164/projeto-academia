package com.academia.dao;

import java.util.List;

import com.academia.entities.Pagamento;

public interface PagamentoDao {

	List<Pagamento> findAll();

	boolean insert(Pagamento pagamento);

	boolean makePayment(int idPagamento, String formaPagamento);

}
