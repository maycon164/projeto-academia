package com.academia.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.academia.entities.Aluno;
import com.academia.entities.Endereco;
import com.academia.exception.CepNotFound;
import com.academia.exception.EmptyFieldException;
import com.academia.util.Utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AlunoControl {

	// FORMATAR DATAS
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	// PROPERTYS ALUNO
	public StringProperty cpfProps = new SimpleStringProperty("");
	public StringProperty nomeProps = new SimpleStringProperty("");
	public StringProperty nascimentoProps = new SimpleStringProperty("");
	public StringProperty emailProps = new SimpleStringProperty("");
	public StringProperty telefoneProps = new SimpleStringProperty("");
	public StringProperty sexoProps = new SimpleStringProperty("");

	// PROPERTYS ENDERECO
	public StringProperty cepProps = new SimpleStringProperty("");
	public StringProperty bairroProps = new SimpleStringProperty("");
	public StringProperty ruaProps = new SimpleStringProperty("");
	public StringProperty numProps = new SimpleStringProperty("");

	// PROPERTYS PLANO ESCOLHIDO
	public Integer idPlano;
	public String instrutorNome;

	public StringProperty precoProps = new SimpleStringProperty("");
	public StringProperty duracaoProps = new SimpleStringProperty("");
	public StringProperty dataInicioProps = new SimpleStringProperty(sdf.format(new Date()));
	public StringProperty dataFimProps = new SimpleStringProperty("");
	public StringProperty observacaoProps = new SimpleStringProperty("");

	// PROPERTY MESSAGE ERROR
	public StringProperty messageErrorProps = new SimpleStringProperty();

	// ISSO AQUI É INTERESSANTE
	// public ObjectProperty<Pessoa>

	public AlunoControl() {
		this.iniciarEventos();
	}

	public void cadastrar() {

		try {
			this.validarCampos();
			this.alunoFromBoundary();

			messageErrorProps.set("Cadastrado Com Sucesso");
			this.limparCampos();
		} catch (EmptyFieldException e) {

			messageErrorProps.set(e.getMessage());

		}

	}

	private void validarCampos() throws EmptyFieldException {

		if (cpfProps.get().isEmpty() || nomeProps.get().isEmpty() || nascimentoProps.get().isEmpty()
				|| emailProps.get().isEmpty() || telefoneProps.get().isEmpty() || sexoProps.get().isEmpty()) {
			System.out.println("");
			throw new EmptyFieldException("DADOS DO ALUNO: ALGUNS CAMPOS VAZIOS");
		}

		if (cepProps.get().isEmpty() || bairroProps.get().isEmpty() || ruaProps.get().isEmpty()
				|| numProps.get().isEmpty()) {
			throw new EmptyFieldException("DADOS DE ENDEREÇO: ALGUNS CAMPOS VAZIOS");
		}

		if ((idPlano == null) || duracaoProps.get().isEmpty() || dataInicioProps.get().isEmpty()
				|| dataFimProps.get().isEmpty() || precoProps.get().isEmpty()) {
			throw new EmptyFieldException("DADOS DE PLANO: ALGUNS CAMPOS VAZIOS");
		}

	}

	private void limparCampos() {
		cpfProps.set("");
		nomeProps.set("");
		emailProps.set("");
		telefoneProps.set("");
		nascimentoProps.set("");
		sexoProps.set("");
		
		cepProps.set("");
		bairroProps.set("");
		ruaProps.set("");
		cepProps.set("");
		numProps.set("");
	
		idPlano = null;
		duracaoProps.set("");
		dataInicioProps.set(sdf.format(new Date()));
		dataFimProps.set("");
		precoProps.set("");
		
		observacaoProps.set("");
	}

	public void iniciarEventos() {

		// CALCULANDO DATA DE FIM DE ACORDO COM O PLANO SELECIONADO
		dataInicioProps.addListener((obs, oldValue, newValue) -> {
			if (newValue.length() == 10) {
				atualizarDataFim(newValue);
			}
		});

		// CONSUMINDO DADOS DE UMA API
		cepProps.addListener((obs, oldValue, newValue) -> {
			if (newValue.length() == 8) {

				try {

					Endereco endereco = Utils.pegarCep(newValue);
					bairroProps.set(endereco.getBairro());
					ruaProps.set(endereco.getLogradouro());

				} catch (CepNotFound e) {
					messageErrorProps.set(e.getMessage());
				} catch (Exception e) {
					messageErrorProps.set(e.getMessage());
				}

			}
		});

	}

	public void atualizarDataFim(String data) {
		if (data.length() == 10) {

			try {
				Date dataFim = sdf.parse(dataInicioProps.get());
				int duracao = Integer.parseInt(duracaoProps.get());

				Calendar cal = Calendar.getInstance();
				cal.setTime(dataFim);
				cal.add(Calendar.DATE, duracao);
				dataFim = cal.getTime();

				dataFimProps.set(sdf.format(dataFim));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	private Aluno alunoFromBoundary() {
		System.out.println("------------- DADOS CADASTRAIS ALUNO -------------");
		System.out.println("CPF: " + cpfProps.get());
		System.out.println("Nome: " + nomeProps.get());
		System.out.println("Nascimento: " + nascimentoProps.get());
		System.out.println("Email: " + emailProps.get());
		System.out.println("Telefone: " + telefoneProps.get());
		System.out.println("Sexo: " + sexoProps.get());

		System.out.println("------------- DADOS DE ENDERECO -------------");
		System.out.println("cep: " + cepProps.get());
		System.out.println("bairro: " + bairroProps.get());
		System.out.println("rua: " + ruaProps.get());
		System.out.println("num: " + numProps.get());

		System.out.println("------------- INSTRUTOR E PLANO -------------");
		System.out.println("INSTRUTOR: " + instrutorNome);
		System.out.println("id PLANO: " + idPlano);
		System.out.println("Preco plano: " + precoProps.get());
		System.out.println("duracao Plano: " + duracaoProps.get());

		System.out.println("DATA INICIO: " + dataInicioProps.get());
		System.out.println("DATA FIM: " + dataFimProps.get());
		System.out.println("OBSERVAÇÕES: " + observacaoProps.get());

		return null;
	}

}
