package com.academia.boundary;

import com.academia.control.InstrutorControl;
import com.academia.entities.Instrutor;
import com.academia.entities.Plano;
import com.academia.factory.ControllerMediator;
import com.academia.util.Listener;
import com.academia.util.UtilsGui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GerenciarInstrutorBoundary implements Listener {

	// CONTROL
	private InstrutorControl instrutorControl = ControllerMediator.getInstrutorControl();

	// LABELS
	private Label lblGerenciarInstrutores = new Label("GERENCIAR INSTRUTORES");
	private Label lblCpf = new Label("CPF: ");
	private Label lblEmail = new Label("Email: ");
	private Label lblNome = new Label("Nome: ");
	private Label lblSexo = new Label("Sexo: ");

	// TEXTFIELD
	private TextField txtCpf = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtEmail = new TextField();
	private ChoiceBox<String> cbSexo = new ChoiceBox<>();

	private Label lblVincularPlano = new Label("VINCULAR PLANO AO INSTRUTOR: ");
	private Label lblTodosInstrutores = new Label("TODOS OS INSTRUTORES");

	// List view
	private ListView<Plano> lvPlanos = new ListView<>();

	// TABLE VIEW
	private TableView<Instrutor> tblInstrutores = new TableView<>();

	// BUTTONS
	private Button btnAdicionar = new Button("Adicionar Plano");
	private Button btnRemover = new Button("Remover Plano");
	private Button btnCadastrar = new Button("Cadastrar");
	private Button btnExcluir = new Button("Excluir");
	private Button btnAlterar = new Button("Alterar");
	private Button btnCancelar = new Button("Cancelar");

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
		iniciarParaCadastro();
		iniciarModalAdicionarPlanos();
		iniciarGridGerenciarInstrutores();
		iniciarChoiceBox();
		iniciarCss();
		iniciarEventos();
		iniciarControl();
		iniciarTableViewInstrutores();
	}

	private void iniciarModalAdicionarPlanos() {

		adicionarPlanoBoundary.addListener(this);

		Scene scene = new Scene(adicionarPlanoBoundary.render(), 500, 400);
		scene.getStylesheets().add("style.css");

		s1.initOwner(MenuBarBoundary.getRoot());
		s1.initModality(Modality.APPLICATION_MODAL);
		s1.setTitle("SELECIONE OS PLANOS");
		s1.setScene(scene);

	}

	private void iniciarGridGerenciarInstrutores() {
		tblInstrutores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tblInstrutores.setPrefWidth(500d);
		tblInstrutores.setPrefHeight(300d);
		tblInstrutores.setMaxHeight(300d);
		tblInstrutores.setMinHeight(300d);
		
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

		FlowPane fpBotoes = new FlowPane();
		fpBotoes.getChildren().addAll(btnCadastrar, btnAlterar, btnCancelar);
		FlowPane.setMargin(btnAlterar, new Insets(0d, 20d, 0d, 20d));
		gridGerenciarInstrutores.addRow(7, fpBotoes);
		fpBotoes.setAlignment(Pos.CENTER);

		// COLUMN SPAN
		GridPane.setColumnSpan(lblGerenciarInstrutores, 4);
		GridPane.setColumnSpan(lblVincularPlano, 4);
		GridPane.setColumnSpan(fpListaPlanos, 4);
		GridPane.setColumnSpan(fpBotoesPlano, 4);
		GridPane.setColumnSpan(fpBotoes, 4);

		// GAMBIARRA
		gridGerenciarInstrutores.addColumn(5, new Label("    "));
		gridGerenciarInstrutores.addColumn(6, new Label("    "));

		// PARTE DE INSTRUTORES
		gridGerenciarInstrutores.add(lblTodosInstrutores, 7, 0);
		gridGerenciarInstrutores.add(tblInstrutores, 7, 1);

		FlowPane fpBtnExcluir = new FlowPane();
		fpBtnExcluir.getChildren().add(btnExcluir);

		gridGerenciarInstrutores.add(fpBtnExcluir, 7, 7);
		fpBtnExcluir.setAlignment(Pos.CENTER);

		GridPane.setColumnSpan(lblTodosInstrutores, 2);
		GridPane.setColumnSpan(tblInstrutores, 2);
		GridPane.setColumnSpan(fpBtnExcluir, 2);
		GridPane.setRowSpan(tblInstrutores, 6);

	}

	private void iniciarEventos() {

		// Cadastrar novo Instrutor
		btnCadastrar.setOnAction(event -> {
			instrutorControl.planosBoundary = lvPlanos.getItems();

			if (instrutorControl.cadastrar()) {

				UtilsGui.showAlert("Inserção de Instruto", "Instrutor",
						"Instrutor " + txtNome.getText() + " Inserido Com sucesso", AlertType.INFORMATION);

				limparCampos();

			}

		});

		// alterar instrutor
		btnAlterar.setOnAction(event -> {

			instrutorControl.planosBoundary = lvPlanos.getItems();

			if (instrutorControl.alterar(tblInstrutores.getSelectionModel().getSelectedItem())) {

				UtilsGui.showAlert("AVISO", "ALTERAR INSTRUTOR", "INSTRUTOR ALTERADO COM SUCESSO",
						AlertType.INFORMATION);
				iniciarParaCadastro();

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

		// SELECIONAR UM INSTRUTOR DA TABELA
		tblInstrutores.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

			if (event.getClickCount() > 1) {
				Instrutor i = tblInstrutores.getSelectionModel().getSelectedItem();
				lvPlanos.setItems(FXCollections.observableArrayList(i.getPlanos()));
				instrutorControl.setInstrutor(i);
				iniciarParaAlterar();
			}

		});

		// BTN EXCLUIR
		btnExcluir.setOnAction(event -> {

			Instrutor instrutor = tblInstrutores.getSelectionModel().getSelectedItem();
			if (UtilsGui.showConfirmation("Excluir Instrutor", "Deseja Excluir o Insturtor " + instrutor.getNome())) {
				if (instrutorControl.excluir(instrutor)) {

					UtilsGui.showAlert("EXCLUSÃO DE INSTRUTOR", "AVISO",
							"O INSTRUTOR COM O CPF " + instrutor.getCpf() + " FOI EXCLUÍDO", AlertType.INFORMATION);
					limparCampos();
				} else {
					System.out.println("ALGO DEU MUITO ERRADO KKKKKKK");
				}
			}

		});

		// BTN CANCELAR
		btnCancelar.setOnAction(event -> {
			iniciarParaCadastro();
		});

	}

	public void iniciarParaCadastro() {

		btnCadastrar.setDisable(false);
		btnAlterar.setDisable(true);
		limparCampos();

	}

	public void limparCampos() {
		instrutorControl.limparCampos();
		lvPlanos.getItems().clear();
	}

	private void iniciarParaAlterar() {

		btnCadastrar.setDisable(true);
		btnAlterar.setDisable(false);
	}

	private void iniciarChoiceBox() {
		cbSexo.getItems().add("Masculino");
		cbSexo.getItems().add("Feminino");
	}

	private void iniciarCss() {

		lblVincularPlano.getStyleClass().add("textCentralizado");
		lblGerenciarInstrutores.getStyleClass().add("textCentralizado");
		lblTodosInstrutores.getStyleClass().add("textCentralizado");
		gridGerenciarInstrutores.getStyleClass().add("grid");

	}

	@SuppressWarnings("unchecked")
	private void iniciarTableViewInstrutores() {
		TableColumn<Instrutor, String> instrutorCol = new TableColumn<>("INSTRUTOR");
		instrutorCol.setCellValueFactory(new PropertyValueFactory<>("nome"));

		TableColumn<Instrutor, String> cpfCol = new TableColumn<>("CPF");
		cpfCol.setCellValueFactory(new PropertyValueFactory<>("cpf"));

		TableColumn<Instrutor, String> emailCol = new TableColumn<>("EMAIL");
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

		TableColumn<Instrutor, String> sexoCol = new TableColumn<>("SEXO");
		sexoCol.setCellValueFactory(item -> {
			return new ReadOnlyStringWrapper((item.getValue().getSexo() == 'M') ? "Masculino" : "Feminino");
		});

		tblInstrutores.getColumns().addAll(instrutorCol, cpfCol, emailCol, sexoCol);
		tblInstrutores.setItems(instrutorControl.getInstrutores());
	}

	private void iniciarControl() {

		Bindings.bindBidirectional(txtCpf.textProperty(), instrutorControl.cpfProps);
		Bindings.bindBidirectional(txtNome.textProperty(), instrutorControl.nomeProps);
		Bindings.bindBidirectional(txtEmail.textProperty(), instrutorControl.emailProps);
		Bindings.bindBidirectional(cbSexo.valueProperty(), instrutorControl.sexoProps);

	}

	@Override
	public void update(Plano plano) {
		lvPlanos.getItems().add(plano);
	}

}
