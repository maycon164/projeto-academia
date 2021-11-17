package com.academia.control;

import com.academia.dao.PlanoDao;
import com.academia.entities.Plano;
import com.academia.exception.EmptyFieldException;
import com.academia.factory.DaoFactory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

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
	private FilteredList<Plano> filteredPlano = new FilteredList<>(planos, p -> true);

	public ObservableList<Plano> getPlanos() {
		return filteredPlano;
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

	public boolean atualizar(Plano plano) {

		try {

			validarCampos();

			Plano aux = planoFromBoundary();
			plano.setNome(aux.getNome());
			plano.setDescricao(aux.getDescricao());
			plano.setPreco(aux.getPreco());
			plano.setDuracao(aux.getDuracao());

			atualizarListaPlanos();

			return planoConn.update(plano);

		} catch (EmptyFieldException e) {
			messageError.set(e.getMessage());
		}

		return false;

	}

	public void atualizarListaPlanos() {

		filteredPlano.setPredicate(p -> false);
		filteredPlano.setPredicate(p -> true);

	}

	private void validarCampos() throws EmptyFieldException {

		if (nomeProps.get().isEmpty() || duracaoProps.get().isEmpty() || precoProps.get().isEmpty()
				|| descricaoProps.get().isEmpty()) {
			throw new EmptyFieldException("PREENCHA TODOS OS CAMPOS");
		}

	}

	public void limparCampos() {

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

	public void linhaParaForm(Plano plano) {

		nomeProps.set(plano.getNome());
		precoProps.set(String.valueOf(plano.getPreco()));
		duracaoProps.set(String.valueOf(plano.getDuracao()));
		descricaoProps.set(plano.getDescricao());

	}

}
