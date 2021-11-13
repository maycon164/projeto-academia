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
	public int idPlano;
	public double precoPlano;
	public int duracaoPlano;
	public String nomeInstrutor;

	public StringProperty dataInicioProps = new SimpleStringProperty();
	public StringProperty dataFimProps = new SimpleStringProperty();
	public StringProperty observacaoProps = new SimpleStringProperty();

	// ISSO AQUI É INTERESSANTE
	// public ObjectProperty<Pessoa>

	public void cadastrar() {

		System.out.println("CPF: " + cpfProps.get());
		System.out.println("Nome: " + nomeProps.get());
		System.out.println("Nascimento: " + nascimentoProps.get());
		System.out.println("Email: " + emailProps.get());
		System.out.println("Telefone: " + telefoneProps.get());
		System.out.println("Sexo: " + sexoProps.get());
		System.out.println("cep: " + cepProps.get());
		System.out.println("bairro: " + bairroProps.get());
		System.out.println("rua: " + ruaProps.get());
		System.out.println("num: " + numProps.get());

		System.out.println("INSTRUTOR: " + nomeInstrutor);
		System.out.println("id PLANO: " + idPlano);
		System.out.println("Preco plano: " + precoPlano);
		System.out.println("duracao Plano: " + duracaoPlano);
		
		System.out.println("DATA INICIO: " + dataInicioProps);
		System.out.println("DATA FIM: " + dataFimProps);
		System.out.println("OBSERVAÇÕES: " + observacaoProps);
		
	}

	public void teste() {
		System.out.println(sexoProps.get());
	}

}
