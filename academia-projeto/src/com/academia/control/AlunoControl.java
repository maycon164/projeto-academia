package com.academia.control;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.academia.dao.AlunoDao;
import com.academia.entities.Aluno;
import com.academia.entities.Assinatura;
import com.academia.entities.Endereco;
import com.academia.entities.Plano;
import com.academia.exception.CepNotFound;
import com.academia.exception.CpfRegisteredException;
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

	// CBPLANO
	public ObjectProperty<Plano> planoProps = new SimpleObjectProperty<>();

	// LISTAS
	private ObservableList<Aluno> alunos = FXCollections.observableArrayList(alunoConn.findAll());
	private FilteredList<Aluno> filteredAlunos = new FilteredList<>(alunos);
	private SortedList<Aluno> sortedAlunos = new SortedList<>(filteredAlunos);

	// PROPERTY MESSAGE ERROR
	public StringProperty messageErrorProps = new SimpleStringProperty();

	// CAMPO DE PESQUISA
	public StringProperty pesquisaProps = new SimpleStringProperty("");

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
				this.limparCampos();
				return true;

			} catch (ParseException e) {

				messageErrorProps.set("INFORME A DATA(dd/MM/yyyy)");

			} catch (CpfRegisteredException e) {

				messageErrorProps.set(e.getMessage());
			}

		} catch (EmptyFieldException e) {

			messageErrorProps.set(e.getMessage());

		}
		return false;
	}

	public void alterar() {

		System.out.println("AGORA AQUI VEM A LÓGICA PARA ALTERAR.....");

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

		if (idPlano == null) {

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

	}

	public void atualizarDataFim(String data) {
		if (data.length() == 10) {

			try {
				Date dataFim = Utils.somarData(sdf.parse(dataInicioProps.get()), Integer.parseInt(duracaoProps.get()));
				dataFimProps.set(sdf.format(dataFim));
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

	public ObservableList<Aluno> getAlunos() {
		return sortedAlunos;
	}

	public void setAluno(String cpf) {

		this.aluno = alunoConn.findByCpf(cpf);
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

		// ALUNO PRECISA TRAZER A ASSINATURA JUNTO COM O PLANO
		this.planoProps.set(aluno.getAssinatura().getPlano());
		
		this.precoProps.set("R$ " + String.valueOf(aluno.getAssinatura().getPlano().getPreco()));
		this.duracaoProps.set(String.valueOf(aluno.getAssinatura().getPlano().getDuracao()));
		this.dataInicioProps.set(sdf.format(aluno.getAssinatura().getDataInicio()));
		this.dataFimProps.set(sdf.format(aluno.getAssinatura().getDataExpiracao()));
		
	}
}
