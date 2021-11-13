package com.academia.boundary;

import java.util.List;
import java.util.stream.Collectors;

import com.academia.entities.Plano;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AlunoBoundary extends Application {
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
	private Label lblInstrutores = new Label("Instrutor: ");
	private Label lblInicio = new Label("Data Inicio: ");
	private Label lblFim = new Label("Data Expiração: ");
	private Label lblObservacoes = new Label("OBSERVAÇÕES DO ALUNO");

	// TEXTFIELDS
	private ChoiceBox<Plano> cbPlano = new ChoiceBox<>();
	private TextField txtPreco = new TextField();
	private ChoiceBox<String> cbInstrutores = new ChoiceBox<>();
	private TextField txtDataInicio = new TextField();
	private TextField txtDataFim = new TextField();
	private TextArea txtaObservacao = new TextArea();

	private GridPane gridCadastroAluno = new GridPane();

	public static void main(String[] args) {
		Application.launch(AlunoBoundary.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.configurarGridCadastroAluno();

		Scene scene = new Scene(gridCadastroAluno, 1020, 600);

		scene.getStylesheets().add("style.css");

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
		gridCadastroAluno.addRow(10, lblInstrutores, cbInstrutores);
		gridCadastroAluno.addRow(11, lblInicio, txtDataInicio, lblFim, txtDataFim);
		gridCadastroAluno.addRow(12, lblObservacoes);
		gridCadastroAluno.addRow(13, txtaObservacao);

		// SETANDO COLUMN SPANS
		GridPane.setColumnSpan(lblDadosCadastrais, 4);
		GridPane.setColumnSpan(lblDadosEndereco, 4);
		GridPane.setColumnSpan(lblDadosPlano, 4);
		GridPane.setColumnSpan(lblObservacoes, 4);
		GridPane.setColumnSpan(txtaObservacao, 4);

		// AJEITANDO O TEXTAREA

		this.ajustarTamanhoTextFields();
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

		// TEXTAREA
		txtaObservacao.getStyleClass().add("textArea");

	}

}
