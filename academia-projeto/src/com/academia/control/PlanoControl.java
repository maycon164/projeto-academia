package com.academia.control;

import java.util.List;

import com.academia.dao.PlanoDao;
import com.academia.entities.Plano;
import com.academia.factory.DaoFactory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlanoControl {

	public StringProperty nomeProps = new SimpleStringProperty("");
	public StringProperty precoProps = new SimpleStringProperty("");
	public StringProperty duracaoProps = new SimpleStringProperty("");
	public StringProperty descricaoProps = new SimpleStringProperty("");

	private PlanoDao planoConn = DaoFactory.getPlanoDao();

	public List<Plano> getPlanos() {
		return planoConn.findAll();
	}

	public void cadastrar() {
		Plano plano = planoFromBoundary();

		//planoConn.insert(plano);
	}

	private Plano planoFromBoundary() {
		Plano plano = new Plano();

		plano.setNome(nomeProps.get());
		plano.setDescricao(descricaoProps.get());
		plano.setDuracao(Integer.parseInt(duracaoProps.get()));
		plano.setPreco(Double.parseDouble(precoProps.get()));
		return plano;
	}

}
