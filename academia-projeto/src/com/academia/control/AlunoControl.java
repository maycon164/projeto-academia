package com.academia.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.academia.dao.AlunoDao;
import com.academia.dto.AlunoPlanoDTO;
import com.academia.entities.Aluno;
import com.academia.entities.Assinatura;
import com.academia.entities.Endereco;
import com.academia.entities.Plano;
import com.academia.exception.CepNotFound;
import com.academia.exception.CpfRegisteredException;
import com.academia.exception.EmptyFieldException;
import com.academia.factory.DaoFactory;
import com.academia.util.Utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class AlunoControl {

	// ALUNO CONNEXÃO
	private AlunoDao alunoConn = DaoFactory.getAlunoDao();

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
	public String nomePlano;

	public StringProperty precoProps = new SimpleStringProperty("");
	public StringProperty duracaoProps = new SimpleStringProperty("");
	public StringProperty dataInicioProps = new SimpleStringProperty(sdf.format(new Date()));
	public StringProperty dataFimProps = new SimpleStringProperty("");
	public StringProperty observacaoProps = new SimpleStringProperty("");

	// LISTAS

	private ObservableList<AlunoPlanoDTO> alunosPlanos = FXCollections
			.observableArrayList(alunoConn.findAllAlunoPlano());

	private FilteredList<AlunoPlanoDTO> filteredAlunosPlanos = new FilteredList<>(alunosPlanos);

	private SortedList<AlunoPlanoDTO> sortedAlunosPlanos = new SortedList<>(filteredAlunosPlanos);

	// PROPERTY MESSAGE ERROR
	public StringProperty messageErrorProps = new SimpleStringProperty();

	// CAMPO DE PESQUISA
	public StringProperty pesquisaProps = new SimpleStringProperty("");

	// ALUNO PARA ALTERAÇÃO
	private Aluno aluno = null;

	public AlunoControl(Aluno aluno) {
		this.iniciarEventos();
		this.aluno = aluno;

	}

	public AlunoControl() {
		this.iniciarEventos();
	}

	public void cadastrar() {

		try {

			this.validarCampos();
			Assinatura assinatura = null;

			try {

				aluno = this.alunoFromBoundary();

				if (aluno != null) {
					alunoConn.insert(aluno);
					System.out.println(aluno);
				}

				assinatura = this.assinaturaFromBoundary();

				if (assinatura != null) {

					assinatura.setAluno(aluno);
					aluno.setAssinatura(assinatura);
					alunoConn.assinarPlano(assinatura);

				}

				alunosPlanos.add(AlunoPlanoDTO.from(aluno));

				messageErrorProps.set("Cadastrado Com Sucesso");
				this.limparCampos();

			} catch (ParseException e) {

				messageErrorProps.set("INFORME A DATA(dd/MM/yyyy)");
			} catch (CpfRegisteredException e) {

				messageErrorProps.set(e.getMessage());
			}

		} catch (EmptyFieldException e) {

			messageErrorProps.set(e.getMessage());

		}

	}

	public void alterar() {
		System.out.println("AGORA AQUI VEM A LÓGICA PARA ALTERAR.....");
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

		// CONSUMINDO DADOS API VIA CEP
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

		// ORDENAR LISTA CAMPO PESQUISAR
		pesquisaProps.addListener((obs, oldValue, newValue) -> {

			filteredAlunosPlanos.setPredicate(p -> {
				if (newValue.isEmpty() || newValue == null) {
					return true;
				}

				String parametro = newValue.toLowerCase();

				if (p.getAluno().toLowerCase().contains(parametro)) {
					return true;
				}

				return false;

			});

		});

	}

	public void atualizarDataFim(String data) {
		if (data.length() == 10) {

			try {
				Date dataFim = Utils.somarData(sdf.parse(dataInicioProps.get()), Integer.parseInt(duracaoProps.get()));
				dataFimProps.set(sdf.format(dataFim));
			} catch (ParseException e) {
				messageErrorProps.set("Informe a data no formato dd/MM/yyyy");
			}
		}
	}

	private Aluno alunoFromBoundary() throws ParseException {

		Aluno aluno = new Aluno();

		aluno.setCpf(cpfProps.get());
		aluno.setNome(nomeProps.get());
		aluno.setNascimento(sdf.parse(nascimentoProps.get()));
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

		// NÃO SEI SE ESSA É A MELHOR FORMA
		Assinatura assinatura = new Assinatura();

		Plano plano = new Plano();
		plano.setIdPlano(idPlano);
		plano.setNome(nomePlano);
		plano.setDuracao(Integer.parseInt(duracaoProps.get()));
		assinatura.setPlano(plano);
		assinatura.setDataInicio(sdf.parse(dataInicioProps.get()));
		assinatura.setDataExpiracao(sdf.parse(dataFimProps.get()));

		return assinatura;

	}

	public ObservableList<AlunoPlanoDTO> getAlunosPlanosDTO() {
		return sortedAlunosPlanos;
	}

	public void setAluno(String cpf) {

		this.aluno = alunoConn.findById(cpf);
		System.out.println(aluno);
		this.setarDados();

	}

	private void setarDados() {

		this.cpfProps.set(aluno.getCpf());
		this.nomeProps.set(aluno.getNome());
		this.emailProps.set(aluno.getEmail());
		this.telefoneProps.set(aluno.getTelefone());
		this.nascimentoProps.set(sdf.format(aluno.getNascimento()));
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

	}
}
