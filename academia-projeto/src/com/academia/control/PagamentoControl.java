package com.academia.control;

import java.text.SimpleDateFormat;

import com.academia.dao.PagamentoDao;
import com.academia.entities.Pagamento;
import com.academia.factory.DaoFactory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PagamentoControl {

	// CONEXÃO COM BANCO
	private PagamentoDao pagamentoConn = DaoFactory.getPagamentoDao();

	// BINDINGS
	public StringProperty cpfProps = new SimpleStringProperty("");
	public StringProperty nomeProps = new SimpleStringProperty("");
	public StringProperty planoProps = new SimpleStringProperty("");
	public StringProperty valorProps = new SimpleStringProperty("");
	public StringProperty dataPagamentoProps = new SimpleStringProperty("");
	public StringProperty tipoPagamentoProps = new SimpleStringProperty("");

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	// LISTAS
	private ObservableList<Pagamento> pagamentos = FXCollections.observableArrayList();

	public ObservableList<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void atualizarPagamentos() {
		pagamentos.clear();
		pagamentos.setAll(pagamentoConn.findAll());
	}

	public void setPagamento(Pagamento p) {

		cpfProps.set(p.getCpfAluno());
		nomeProps.set(p.getNomeAluno());
		planoProps.set(p.getNomePlano());
		valorProps.set("R$" + p.getValorTotal());
		dataPagamentoProps.set(sdf.format(p.getDataPagamento()));

		if (p.getTipoPagamento() != null) {
			tipoPagamentoProps.set(p.getTipoPagamento().toString());
		}

	}

}
