package com.academia.boundary;

import com.academia.entities.Plano;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GerenciarInstrutorBoundary extends Application {

	// LABELS
	private Label lblGerenciarInstrutores = new Label("Gerenciar Instrutores");
	private Label lblCpf = new Label("CPF: ");
	private Label lblEmail = new Label("Email: ");
	private Label lblNome = new Label("Nome: ");
	private Label lblSexo = new Label("Sexo: ");

	// TEXTFIELD
	private TextField txtCpf = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtEmail = new TextField();
	private ChoiceBox<String> cbSexo = new ChoiceBox<>();

	private Label lblVincularPlano = new Label("Vincular Plano ao Instrutor: ");

	// List view
	private ListView<Plano> lvPlanos = new ListView<>();

	// BUTTONS
	private Button btnAdicionar = new Button("Adicionar Plano");
	private Button btnRemover = new Button("Remover Plano");
	private Button btnCadastrar = new Button("Cadastrar");

	private GridPane gridGerenciarInstrutores = new GridPane();

	public static void main(String[] args) {
		Application.launch(GerenciarInstrutorBoundary.class, args);
	}

	Scene scene = null;
	Stage stage = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		scene = new Scene(gridGerenciarInstrutores, 500, 500);
		scene.getStylesheets().add("style.css");
		iniciarTela();
		stage.setScene(scene);
		stage.show();
	}

	/*
	 * public GerenciarInstrutorBoundary() { iniciarTela();
	 * 
	 * }
	 */

	public GridPane render() {
		return gridGerenciarInstrutores;
	}

	private void iniciarTela() {
		iniciarGridGerenciarInstrutores();
		iniciarChoiceBox();
		iniciarCss();
		iniciarEventos();
	}

	private void iniciarGridGerenciarInstrutores() {

		gridGerenciarInstrutores.addRow(0, lblGerenciarInstrutores);
		gridGerenciarInstrutores.addRow(1, lblCpf, txtCpf, lblEmail, txtEmail);
		gridGerenciarInstrutores.addRow(2, lblNome, txtNome, lblSexo, cbSexo);
		gridGerenciarInstrutores.addRow(3, lblVincularPlano);

		FlowPane fpBotoesPlano = new FlowPane();
		fpBotoesPlano.getChildren().addAll(btnAdicionar, btnRemover);
		FlowPane.setMargin(btnRemover, new Insets(0d, 0d, 0d, 30d));
		fpBotoesPlano.setAlignment(Pos.CENTER);

		gridGerenciarInstrutores.addRow(4, fpBotoesPlano);

		FlowPane fpListaPlanos = new FlowPane();
		fpListaPlanos.getChildren().add(lvPlanos);
		lvPlanos.setPrefHeight(200d);
		lvPlanos.setPrefWidth(350d);
		lvPlanos.setMaxWidth(350d);
		lvPlanos.setMinWidth(350d);
		fpListaPlanos.setAlignment(Pos.CENTER);

		gridGerenciarInstrutores.addRow(5, fpListaPlanos);

		gridGerenciarInstrutores.addRow(7, btnCadastrar);

		// COLUMN SPAN
		GridPane.setColumnSpan(lblGerenciarInstrutores, 4);
		GridPane.setColumnSpan(lblVincularPlano, 4);
		GridPane.setColumnSpan(fpListaPlanos, 4);
		GridPane.setColumnSpan(fpBotoesPlano, 4);

	}

	private void iniciarEventos() {
		btnAdicionar.setOnAction(event -> {
			Stage root = stage;

			System.out.println(root);

			Stage stage2 = new Stage();
			Pane pane = new Pane();
			pane.getChildren().add(new Label("MODAL RONALDO"));

			Scene scene2 = new Scene(pane, 500, 300);
			
			stage2.setTitle("TESTANDO MODAL");
			stage2.setScene(scene2);
			
			stage2.initOwner(root);
			stage2.initModality(Modality.APPLICATION_MODAL);
			stage2.show();
		});
	}

	private void iniciarChoiceBox() {
		cbSexo.getItems().add("Masculino");
		cbSexo.getItems().add("Feminino");
	}

	private void iniciarCss() {
		lblVincularPlano.getStyleClass().add("textCentralizado");
		lblGerenciarInstrutores.getStyleClass().add("textCentralizado");
		gridGerenciarInstrutores.getStyleClass().add("grid");
	}

}
