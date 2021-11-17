package com.academia.boundary;

import com.academia.control.InstrutorControl;
import com.academia.entities.Plano;
import com.academia.factory.ControllerMediator;
import com.academia.util.Listener;
import com.academia.util.UtilsGui;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GerenciarInstrutorBoundary implements Listener {

	// CONTROL
	private InstrutorControl instrutorControl = ControllerMediator.getInstrutorControl();

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

	// Modal para vincular o plano
	private AdicionarPlanoBoundary adicionarPlanoBoundary = new AdicionarPlanoBoundary();
	private Stage s1 = new Stage();

	public GerenciarInstrutorBoundary() {

		iniciarTela();

	}

	public GridPane render() {
		return gridGerenciarInstrutores;
	}

	private void iniciarTela() {

		iniciarModalAdicionarPlanos();
		iniciarGridGerenciarInstrutores();
		iniciarChoiceBox();
		iniciarCss();
		iniciarEventos();
		iniciarControl();

	}

	private void iniciarModalAdicionarPlanos() {

		adicionarPlanoBoundary.addListener(this);

		Scene sceneTeste = new Scene(adicionarPlanoBoundary.render(), 500, 400);
		sceneTeste.getStylesheets().add("style.css");

		s1.initOwner(MenuBarExemplo.getRoot());
		s1.initModality(Modality.APPLICATION_MODAL);
		s1.setTitle("SELECIONE OS PLANOS");
		s1.setScene(sceneTeste);

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

		// Cadastrar novo Instrutor
		btnCadastrar.setOnAction(event -> {
			System.out.println("CLICKOU");

			boolean insercao = instrutorControl.cadastrar(lvPlanos.getItems());

			if (insercao) {

				UtilsGui.showAlert("Inserção de Instruto", "Instrutor",
						"Instrutor " + txtNome.getText() + " Inserido Com sucesso", AlertType.INFORMATION);

				instrutorControl.limparCampos();
			} else {
				System.out.println("DEU RUÍM");
			}

		});

		// ADICIONAR PLANOS
		btnAdicionar.setOnAction(event -> {
			s1.show();

		});

		// REMOVER DA TABLE VIEW
		btnRemover.setOnAction(event -> {
			lvPlanos.getItems().remove(lvPlanos.getSelectionModel().getSelectedItem());
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

	private void iniciarControl() {

		Bindings.bindBidirectional(txtCpf.textProperty(), instrutorControl.cpfProps);
		Bindings.bindBidirectional(txtNome.textProperty(), instrutorControl.nomeProps);
		Bindings.bindBidirectional(txtEmail.textProperty(), instrutorControl.emailProps);
		Bindings.bindBidirectional(cbSexo.valueProperty(), instrutorControl.sexoProps);

	}

	@Override
	public void update(Plano plano) {

		System.out.println(plano);
		lvPlanos.getItems().add(plano);

	}

}
