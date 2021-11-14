package com.academia.boundary;

import java.util.List;
import java.util.stream.Collectors;

import com.academia.control.AlunoControl;
import com.academia.control.InstrutorControl;
import com.academia.control.PlanoControl;
import com.academia.dto.InstrutorDTO;
import com.academia.entities.Plano;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AlunoCadastroBoundary extends Application {

	// ALUNO CONTROL
	private AlunoControl alunoControl = new AlunoControl();

	// PLANO CONTROL
	private PlanoControl planoControl = new PlanoControl();

	// INSTRUTOR CONTROL
	private InstrutorControl instrutorControl = new InstrutorControl();

	// TEXTOS
	private Label lblDadosCadastrais = new Label("DADOS PARA CADASTRO");
	private Label lblDadosEndereco = new Label("ENDEREÇO DO ALUNO");
	private Label lblDadosPlano = new Label("SELECIONE UM PLANO");

	// TEXTOS E TEXTFIEDLS DADOS CADASTRAIS
	private Label lblCpf = new Label("CPF: ");
	private Label lblNome = new Label("Nome: ");
	private Label lblNascimento = new Label("Nascimento: ");
	private Label lblEmail = new Label("Email: ");
	private Label lblTelefone = new Label("Telefone");
	private Label lblSexo = new Label("Sexo: ");

	// TEXTFIELDS DADOS CADASTRAIS
	private TextField txtCpf = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtNascimento = new TextField();
	private TextField txtEmail = new TextField();
	private TextField txtTelefone = new TextField();
	private ChoiceBox<String> cbSexo = new ChoiceBox<>();

	// LABELS DE ENEDERECO
	private Label lblCep = new Label("CEP: ");
	private Label lblBairro = new Label("Bairro: ");
	private Label lblRua = new Label("Rua: ");
	private Label lblNum = new Label("Num: ");

	// TEXTFIELDS DADOS DE ENDEREÇO
	private TextField txtCep = new TextField();
	private TextField txtBairro = new TextField();
	private TextField txtRua = new TextField();
	private TextField txtNum = new TextField();

	// LABEL DE SELEÇÃO DO PLANO
	private Label lblPlano = new Label("PLANO: ");
	private Label lblPreco = new Label("Preço : ");
	private Label lblDuracao = new Label("Duração (dias): ");
	private Label lblInstrutores = new Label("Instrutor: ");
	private Label lblInicio = new Label("Data Inicio: ");
	private Label lblFim = new Label("Data Expiração: ");
	private Label lblObservacoes = new Label("OBSERVAÇÕES DO ALUNO");

	// TEXTFIELDS
	private ChoiceBox<Plano> cbPlano = new ChoiceBox<>();
	private TextField txtPreco = new TextField();
	private TextField txtDuracao = new TextField();
	private ChoiceBox<InstrutorDTO> cbInstrutores = new ChoiceBox<>();
	private TextField txtDataInicio = new TextField();
	private TextField txtDataFim = new TextField();
	private TextArea txtaObservacao = new TextArea();

	// LABEL DE ERROR MESSAGE
	private Label messageError = new Label("ERROR MESSAGE");

	// BUTTONS
	private Button btnCadastrar = new Button("CADASTRAR");
	private Button btnAlterar = new Button("ALTERAR");
	private Button btnCancelar = new Button("CANCELAR");

	// GRID PRINCIPAL
	private GridPane gridCadastroAluno = new GridPane();

	public static void main(String[] args) {
		Application.launch(AlunoCadastroBoundary.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(gridCadastroAluno, 1020, 600);

		scene.getStylesheets().add("style.css");

		this.configurarGridCadastroAluno();
		this.ajustarTamanhoTextFields();

		this.iniciarChoiceBoxs();
		this.iniciarControll();
		this.iniciarEventos();

		this.configurandoCSS();

		primaryStage.setScene(scene);
		primaryStage.setTitle("GERENCIAMENTO DE ALUNOS");
		primaryStage.show();
	}

	private void configurarGridCadastroAluno() {
		// DADOS CADASTRAIS
		gridCadastroAluno.addRow(0, lblDadosCadastrais);
		gridCadastroAluno.addRow(2, lblCpf, txtCpf, lblEmail, txtEmail);
		gridCadastroAluno.addRow(3, lblNome, txtNome, lblTelefone, txtTelefone);
		cbSexo.getItems().addAll("Masculino", "Feminino");
		cbSexo.setValue("Masculino");
		gridCadastroAluno.addRow(4, lblNascimento, txtNascimento, lblSexo, cbSexo);

		// DADOS ENDEREÇO DO ALUNO
		gridCadastroAluno.addRow(5, lblDadosEndereco);
		gridCadastroAluno.addRow(6, lblCep, txtCep, lblBairro, txtBairro);
		gridCadastroAluno.addRow(7, lblRua, txtRua, lblNum, txtNum);

		// DADOS DE SELEÇÃO DO PLANO
		gridCadastroAluno.addRow(8, lblDadosPlano);
		gridCadastroAluno.addRow(9, lblPlano, cbPlano, lblPreco, txtPreco);
		txtPreco.setDisable(true);

		gridCadastroAluno.addRow(10, lblInstrutores, cbInstrutores, lblDuracao, txtDuracao);
		txtDuracao.setDisable(true);

		gridCadastroAluno.addRow(11, lblInicio, txtDataInicio, lblFim, txtDataFim);
		// SETANDO UMA DATA DE INICIO (DATA ATUAL)
		txtDataFim.setDisable(true);

		gridCadastroAluno.addRow(12, lblObservacoes);
		gridCadastroAluno.addRow(13, txtaObservacao);
		gridCadastroAluno.addRow(15, messageError);

		// BUTTONS
		gridCadastroAluno.addRow(16, btnCadastrar, btnCancelar);

		// SETANDO COLUMN SPANS
		GridPane.setColumnSpan(lblDadosCadastrais, 4);
		GridPane.setColumnSpan(lblDadosEndereco, 4);
		GridPane.setColumnSpan(lblDadosPlano, 4);
		GridPane.setColumnSpan(lblObservacoes, 4);
		GridPane.setColumnSpan(txtaObservacao, 4);
		GridPane.setColumnSpan(messageError, 4);
		// AJEITANDO O TEXTAREA

	}

	private void ajustarTamanhoTextFields() {
		List<TextField> textFields = gridCadastroAluno.getChildren().stream().filter(node -> node instanceof TextField)
				.map(textField -> (TextField) textField).collect(Collectors.toList());

		textFields.forEach(txt -> {
			txt.getStyleClass().add("txtField");
		});

	}

	private void configurandoCSS() {
		gridCadastroAluno.getStyleClass().add("grid");

		// LABEL CENTRALIZADA
		lblDadosCadastrais.getStyleClass().add("textCentralizado");
		lblDadosEndereco.getStyleClass().add("textCentralizado");
		lblDadosPlano.getStyleClass().add("textCentralizado");
		lblObservacoes.getStyleClass().add("textCentralizado");

		// MESSAGE ERROR
		messageError.getStyleClass().add("messageError");

		// TEXTAREA
		txtaObservacao.getStyleClass().add("textArea");

	}

	private void iniciarEventos() {

		// CADASTRAR UM NOVO CLIENTE E VINCULALO A UM PLANO
		btnCadastrar.setOnAction(e -> {

			// ENVIAR O ID DO PLANO
			if(cbPlano.getValue() != null) {
				alunoControl.idPlano = cbPlano.getValue().getIdPlano();
				// alunoControl.instrutorNome = cbInstrutores.getValue().getNome();

				alunoControl.cadastrar();
			}else {
				messageError.setText("SELECIONE UM PLANO");
			}

			System.out.println("BOTÃO CADASTRAR CLICK");

		});
		
		btnCancelar.setOnAction(event -> {
			System.out.println("CANCELAR..... VOLTANDO A TELA INICIAL");
		});

		// ATUALIZAR O A LISTA DE INSTRUTORES DE ACORDO COM O PLANO
		cbPlano.getSelectionModel().selectedItemProperty().addListener((event, oldValue, newValue) -> {

			Plano plano = cbPlano.getValue();

			this.atualizarComboBoxInstrutores(plano.getIdPlano());
			this.preencherCamposPlano(plano);
			alunoControl.atualizarDataFim(txtDataInicio.getText());
		});

		/*
		 * cbPlano.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
		 * 
		 * });
		 */

	}

	private void preencherCamposPlano(Plano plano) {
		txtPreco.setText(String.valueOf("R$" + plano.getPreco()));
		txtDuracao.setText(String.valueOf(plano.getDuracao()));
	}

	private void atualizarComboBoxInstrutores(int idPlano) {

		cbInstrutores.setItems(FXCollections.observableArrayList(instrutorControl.getInstrutoresByPlano(idPlano)));
	}

	private void iniciarControll() {

		// DADOS CADASTRAIS
		Bindings.bindBidirectional(txtCpf.textProperty(), alunoControl.cpfProps);
		Bindings.bindBidirectional(txtNome.textProperty(), alunoControl.nomeProps);
		Bindings.bindBidirectional(txtEmail.textProperty(), alunoControl.emailProps);
		Bindings.bindBidirectional(txtTelefone.textProperty(), alunoControl.telefoneProps);
		Bindings.bindBidirectional(txtNascimento.textProperty(), alunoControl.nascimentoProps);
		Bindings.bindBidirectional(cbSexo.valueProperty(), alunoControl.sexoProps);

		// DADOS ENDERECO
		Bindings.bindBidirectional(txtCep.textProperty(), alunoControl.cepProps);
		Bindings.bindBidirectional(txtBairro.textProperty(), alunoControl.bairroProps);
		Bindings.bindBidirectional(txtRua.textProperty(), alunoControl.ruaProps);
		Bindings.bindBidirectional(txtNum.textProperty(), alunoControl.numProps);

		// DADOS PLANO, ALUNO PLANO
		Bindings.bindBidirectional(txtDuracao.textProperty(), alunoControl.duracaoProps);
		Bindings.bindBidirectional(txtPreco.textProperty(), alunoControl.precoProps);

		Bindings.bindBidirectional(txtDataInicio.textProperty(), alunoControl.dataInicioProps);
		Bindings.bindBidirectional(txtDataFim.textProperty(), alunoControl.dataFimProps);
		Bindings.bindBidirectional(txtaObservacao.textProperty(), alunoControl.observacaoProps);

		// MESSAGE ERROR
		Bindings.bindBidirectional(messageError.textProperty(), alunoControl.messageErrorProps);

	}

	private void iniciarChoiceBoxs() {

		cbPlano.getItems().addAll(FXCollections.observableArrayList(planoControl.getPlanos()));
	}

}
