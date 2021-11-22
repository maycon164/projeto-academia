package com.academia.boundary;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AlunoAlterarBoundary extends Application {

	// LABELS
	private Label lblAluno = new Label("ALTERAR ALUNO");
	private Label lblDadosCadastrais = new Label("DADOS PARA CADASTRO");
	private Label lblDadosEndereco = new Label("ENDEREÇO DO ALUNO");

	// LABEL
	private Label lblCpf = new Label("CPF: ");
	private Label lblNome = new Label("Nome: ");
	private Label lblNascimento = new Label("Nascimento: ");
	private Label lblEmail = new Label("Email: ");
	private Label lblTelefone = new Label("Telefone");
	private Label lblSexo = new Label("Sexo: ");

	// TEXTFIELD
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

	private GridPane gridAlterarAluno = new GridPane();

	public static void main(String[] args) {
		Application.launch(AlunoAlterarBoundary.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		iniciarTela();
		Scene scene = new Scene(gridAlterarAluno, 600, 500);
		scene.getStylesheets().add("style.css");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void iniciarTela() {
		iniciarGridAlterarAluno();
		iniciarCss();
	}

	private void iniciarGridAlterarAluno() {
		gridAlterarAluno.addRow(0, lblDadosCadastrais);
		gridAlterarAluno.addRow(2, lblCpf, txtCpf, lblEmail, txtEmail);
		gridAlterarAluno.addRow(3, lblNome, txtNome, lblTelefone, txtTelefone);
		cbSexo.getItems().addAll("Masculino", "Feminino");
		gridAlterarAluno.addRow(4, lblNascimento, txtNascimento, lblSexo, cbSexo);

		// DADOS ENDEREÇO DO ALUNO
		gridAlterarAluno.addRow(5, lblDadosEndereco);
		gridAlterarAluno.addRow(6, lblCep, txtCep, lblBairro, txtBairro);
		gridAlterarAluno.addRow(7, lblRua, txtRua, lblNum, txtNum);

		GridPane.setColumnSpan(lblDadosCadastrais, 4);
		GridPane.setColumnSpan(lblDadosEndereco, 4);

	}

	private void iniciarCss() {
		gridAlterarAluno.getStyleClass().add("grid");
		lblDadosCadastrais.getStyleClass().add("textCentralizado");
		lblDadosEndereco.getStyleClass().add("textCentralizado");
	}

}
