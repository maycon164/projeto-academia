package com.academia.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AlunoControl {

	// PROPERTYS ALUNO
	public StringProperty cpfProps = new SimpleStringProperty();
	public StringProperty nomeProps = new SimpleStringProperty();
	public StringProperty nascimentoProps = new SimpleStringProperty();
	public StringProperty emailProps = new SimpleStringProperty();
	public StringProperty telefoneProps = new SimpleStringProperty();
	public StringProperty sexoProps = new SimpleStringProperty();

	// PROPERTYS ENDERECO
	public StringProperty cepProps = new SimpleStringProperty();
	public StringProperty bairroProps = new SimpleStringProperty();
	public StringProperty ruaProps = new SimpleStringProperty();
	public StringProperty numProps = new SimpleStringProperty();

	// PROPERTYS PLANO ESCOLHIDO
	public StringProperty planoProps = new SimpleStringProperty();
	public StringProperty precoProps = new SimpleStringProperty();
	public StringProperty instrutorProps = new SimpleStringProperty();
	public StringProperty dataInicioProps = new SimpleStringProperty();
	public StringProperty dataFimProps = new SimpleStringProperty();
	public StringProperty observacaoProps = new SimpleStringProperty();

	// ISSO AQUI É INTERESSANTE
	// public ObjectProperty<Pessoa>

	public void teste() {
		System.out.println(sexoProps.get());
	}

}
