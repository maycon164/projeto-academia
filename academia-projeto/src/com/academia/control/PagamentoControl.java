package com.academia.control;

import com.academia.dao.PagamentoDao;
import com.academia.entities.Pagamento;
import com.academia.factory.DaoFactory;
import com.academia.util.Utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

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

	// PESQUISAR
	public StringProperty pesquisarPagamentosProps = new SimpleStringProperty("");
	public StringProperty statusProps = new SimpleStringProperty("");

	// LISTAS
	private ObservableList<Pagamento> pagamentos = FXCollections.observableArrayList();
	private FilteredList<Pagamento> filteredPagamentos = new FilteredList<>(pagamentos, p -> true);

	// GAMBIARRA PARA PEGAR O ID SETADO
	private int idPagamento;

	public ObservableList<Pagamento> getPagamentos() {
		return filteredPagamentos;
	}

	public PagamentoControl() {
		iniciarEventos();
	}

	private void iniciarEventos() {

		// PESQUISAR
		pesquisarPagamentosProps.addListener((obs, oldValue, newValue) -> {

			filteredPagamentos.setPredicate(p -> {

				if (newValue.equals("") || newValue == null) {
					return true;
				}

				if (p.getNomeAluno().toLowerCase().contains(newValue.toLowerCase())) {
					return true;
				}

				if (p.getNomePlano().toLowerCase().contains(newValue.toLowerCase())) {
					return true;
				}

				return false;
			});

		});

		// PESQUISAR POR CHOICE BOX
		statusProps.addListener((obs, oldValue, newValue) -> {
			filteredPagamentos.setPredicate(p -> (p.getStatus().toString().equals(newValue)) ? true : false);
		});
	}

	public void atualizarPagamentos() {
		pagamentos.clear();
		pagamentos.setAll(pagamentoConn.findAll());
	}

	public void setPagamento(Pagamento p) {
		idPagamento = p.getIdPagamento();

		cpfProps.set(p.getCpfAluno());
		nomeProps.set(p.getNomeAluno());
		planoProps.set(p.getNomePlano());
		valorProps.set("R$" + p.getValorTotal());
		dataPagamentoProps.set(Utils.formatarData(p.getDataPagamento()));

		if (p.getTipoPagamento() != null) {
			tipoPagamentoProps.set(p.getTipoPagamento().toString());
		}

	}

	public boolean realizarPagamento(String formaPagamento) {

		if (idPagamento != 0 && formaPagamento != null) {
			return pagamentoConn.makePayment(idPagamento, formaPagamento);
		}

		return false;
	}

}
