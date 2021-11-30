package com.academia.control;

import java.text.ParseException;
import java.util.Date;

import com.academia.dao.AlunoDao;
import com.academia.entities.Aluno;
import com.academia.entities.Assinatura;
import com.academia.entities.Endereco;
import com.academia.entities.Plano;
import com.academia.exception.CepNotFound;
import com.academia.exception.CpfException;
import com.academia.exception.EmptyFieldException;
import com.academia.factory.DaoFactory;
import com.academia.util.Utils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class AlunoControl {

	// ALUNO CONNEXÃO
	private AlunoDao alunoConn = DaoFactory.getAlunoDao();

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

	// ASSINATURA

	public StringProperty precoProps = new SimpleStringProperty("");
	public StringProperty duracaoProps = new SimpleStringProperty("");
	public StringProperty dataInicioProps = new SimpleStringProperty(Utils.formatarData(new Date()));
	public StringProperty dataFimProps = new SimpleStringProperty("");
	public StringProperty observacaoProps = new SimpleStringProperty("");

	// CBPLANO
	public ObjectProperty<Plano> planoProps = new SimpleObjectProperty<>();

	// LISTAS
	private ObservableList<Aluno> alunos = FXCollections.observableArrayList(alunoConn.findAll());
	private FilteredList<Aluno> filteredAlunos = new FilteredList<>(alunos);

	// PROPERTY MESSAGE ERROR
	public StringProperty messageErrorProps = new SimpleStringProperty();

	// CAMPO DE PESQUISA
	public StringProperty pesquisaProps = new SimpleStringProperty("");
	public ObjectProperty<Plano> planoPesquisaProps = new SimpleObjectProperty<>();

	// ALUNO PARA ALTERAÇÃO
	private Aluno aluno = null;

	public AlunoControl() {
		this.iniciarEventos();
	}

	public void atualizarListaAluno() {
		filteredAlunos.setPredicate(a -> false);
		filteredAlunos.setPredicate(a -> true);
	}

	public boolean cadastrar() {

		try {

			this.validarCampos();
			Assinatura assinatura = null;

			try {

				aluno = this.alunoFromBoundary();

				if (aluno != null) {
					alunoConn.insert(aluno);
				}

				assinatura = this.assinaturaFromBoundary();

				if (assinatura != null) {

					assinatura.setAluno(aluno);
					aluno.setAssinatura(assinatura);

					alunoConn.assinarPlano(assinatura);

				}

				alunos.add(aluno);
				alunoConn.gerarPagamento(aluno);

				this.limparCampos();
				return true;

			} catch (ParseException e) {

				messageErrorProps.set("INFORME A DATA(dd/MM/yyyy)");

			} catch (CpfException e) {

				messageErrorProps.set(e.getMessage());

			}

		} catch (EmptyFieldException e) {

			messageErrorProps.set(e.getMessage());

		}
		return false;
	}

	public boolean alterar() {

		if (aluno.getCpf().equals(cpfProps.get())) {

			try {

				Aluno aux = alunoFromBoundary();

				aluno.setNome(aux.getNome());
				aluno.setEmail(aux.getEmail());
				aluno.setSexo(aux.getSexo());
				aluno.setTelefone(aux.getTelefone());
				aluno.setNascimento(aux.getNascimento());
				aluno.setCep(aux.getCep());
				aluno.setBairro(aux.getBairro());
				aluno.setRua(aux.getRua());
				aluno.setNum(aux.getNum());
				aluno.setObservacao(aux.getObservacao());
				boolean exec = alunoConn.update(aluno);
				return exec;
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		return false;
	}

	public boolean excluir(Aluno aluno) {

		if (alunoConn.deleteByCpf(aluno.getCpf())) {
			alunos.remove(aluno);
			return true;
		}

		return false;
	}

	private void validarCampos() throws EmptyFieldException {

		Utils.verificarCampos(cpfProps.get(), nomeProps.get(), nascimentoProps.get(), emailProps.get(),
				telefoneProps.get(), sexoProps.get());

		Utils.verificarCampos(cepProps.get(), bairroProps.get(), ruaProps.get(), numProps.get());

		Utils.verificarCampos(duracaoProps.get(), dataInicioProps.get(), dataFimProps.get(), precoProps.get());

		if (planoProps == null) {

			throw new EmptyFieldException("SELECIONE UM PLANO");

		}
	}

	public void limparCampos() {
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

		planoProps.set(null);
		duracaoProps.set("");
		dataInicioProps.set(Utils.formatarData(new Date()));
		dataFimProps.set("");
		precoProps.set("");

		sexoProps.set("");
		planoProps.set(null);

		observacaoProps.set("");
	}

	public void iniciarEventos() {

		// CALCULANDO DATA DE FIM DE ACORDO COM O PLANO SELECIONADO
		dataInicioProps.addListener((obs, oldValue, newValue) -> {
			if (newValue.length() == 10) {
				atualizarDataFim(newValue);
			}
		});

		// EVENTO DE CEP
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

		// ORDENAR LISTA
		pesquisaProps.addListener((obs, oldValue, newValue) -> {

			filteredAlunos.setPredicate(p -> {
				if (newValue.isEmpty() || newValue == null) {
					return true;
				}

				String parametro = newValue.toLowerCase();

				if (p.getNome().toLowerCase().contains(parametro)) {
					return true;
				}

				return false;

			});

		});

		// PESQUISA POR PLANOS
		planoPesquisaProps.addListener((obs, oldValue, newValue) -> {

			filteredAlunos.setPredicate(a -> {
				if (a.getAssinatura().getPlano() == newValue) {
					return true;
				}
				return false;
			});

		});

	}

	public void atualizarDataFim(String data) {
		if (data.length() == 10) {

			try {
				Date dataFim = Utils.somarData(Utils.converterParaData(dataInicioProps.get()),
						Integer.parseInt(duracaoProps.get()));

				dataFimProps.set(Utils.formatarData(dataFim));

			} catch (ParseException e) {
				messageErrorProps.set("Informe a data no formato dd/MM/yyyy");
			} catch (NumberFormatException e) {
				System.out.println("HUMMMMM.....");
			}
		}
	}

	private Aluno alunoFromBoundary() throws ParseException {

		Aluno aluno = new Aluno();

		aluno.setCpf(cpfProps.get());
		aluno.setNome(nomeProps.get());
		aluno.setNascimento(Utils.converterParaData(nascimentoProps.get()));
		aluno.setEmail(emailProps.get());
		aluno.setTelefone(telefoneProps.get());
		aluno.setSexo(sexoProps.get().charAt(0));

		aluno.setCep(cepProps.get());
		aluno.setBairro(bairroProps.get());
		aluno.setRua(ruaProps.get());
		aluno.setNum(numProps.get());
		aluno.setObservacao(observacaoProps.get());

		return aluno;
	}

	private Assinatura assinaturaFromBoundary() throws ParseException {

		Assinatura assinatura = new Assinatura();
		assinatura.setPlano(planoProps.get());
		assinatura.setDataInicio(Utils.converterParaData(dataInicioProps.get()));
		assinatura.setDataExpiracao(Utils.converterParaData(dataFimProps.get()));

		return assinatura;

	}

	public ObservableList<Aluno> getAlunos() {
		return filteredAlunos;
	}

	public void setAluno(Aluno aluno) {

		this.aluno = aluno;
		this.setarDados();

	}

	private void setarDados() {

		this.cpfProps.set(aluno.getCpf());
		this.nomeProps.set(aluno.getNome());
		this.emailProps.set(aluno.getEmail());
		this.telefoneProps.set(aluno.getTelefone());
		this.nascimentoProps.set(Utils.formatarData(aluno.getNascimento()));
		this.cepProps.set(aluno.getCep());
		this.bairroProps.set(aluno.getBairro());
		this.ruaProps.set(aluno.getRua());
		this.numProps.set(aluno.getNum());

		if (aluno.getSexo() == 'M') {
			sexoProps.set("Masculino");
		} else {
			sexoProps.set("Feminino");
		}
		this.observacaoProps.set(aluno.getObservacao());

		this.planoProps.set(aluno.getAssinatura().getPlano());

		this.precoProps.set("R$ " + String.valueOf(aluno.getAssinatura().getPlano().getPreco()));
		this.duracaoProps.set(String.valueOf(aluno.getAssinatura().getPlano().getDuracao()));
		this.dataInicioProps.set(Utils.formatarData(aluno.getAssinatura().getDataInicio()));
		this.dataFimProps.set(Utils.formatarData(aluno.getAssinatura().getDataExpiracao()));

	}
}
