package com.academia.control;

import com.academia.dao.PlanoDao;
import com.academia.entities.Plano;
import com.academia.exception.EmptyFieldException;
import com.academia.factory.DaoFactory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlanoControl {

	// CONEXAO
	private PlanoDao planoConn = DaoFactory.getPlanoDao();

	// PROPERTYS
	public StringProperty nomeProps = new SimpleStringProperty("");
	public StringProperty precoProps = new SimpleStringProperty("");
	public StringProperty duracaoProps = new SimpleStringProperty("");
	public StringProperty descricaoProps = new SimpleStringProperty("");

	// MESSAGE ERROR
	public StringProperty messageError = new SimpleStringProperty("");

	// LISTAS
	private ObservableList<Plano> planos = FXCollections.observableArrayList(planoConn.findAll());

	public ObservableList<Plano> getPlanos() {
		return planos;
	}

	public void cadastrar() {

		try {

			validarCampos();

			Plano plano = planoFromBoundary();

			plano = planoConn.insert(plano);
			planos.add(plano);
			limparCampos();

		} catch (EmptyFieldException e) {
			messageError.set(e.getMessage());
		}

	}

	private void validarCampos() throws EmptyFieldException {

		if (nomeProps.get().isEmpty() || duracaoProps.get().isEmpty() || precoProps.get().isEmpty()
				|| descricaoProps.get().isEmpty()) {
			throw new EmptyFieldException("PREENCHA TODOS OS CAMPOS");
		}

	}

	private void limparCampos() {

		nomeProps.set("");
		duracaoProps.set("");
		precoProps.set("");
		descricaoProps.set("");

	}

	private Plano planoFromBoundary() {
		Plano plano = new Plano();

		plano.setNome(nomeProps.get());
		plano.setDescricao(descricaoProps.get());
		plano.setDuracao(Integer.parseInt(duracaoProps.get()));
		plano.setPreco(Double.parseDouble(precoProps.get()));
		return plano;
	}

	public boolean excluir(Plano plano) {

		if (planoConn.deleteById(plano.getIdPlano())) {

			planos.remove(plano);
			return true;
		}
		return false;
	}

}
