package com.academia.boundary;

import java.util.List;
import java.util.stream.Collectors;

import com.academia.control.PlanoControl;
import com.academia.entities.Plano;
import com.academia.factory.ControllerMediator;
import com.academia.util.UtilsGui;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class GerenciarPlanoBoundary {

	// CONTROL
	PlanoControl planoControl = ControllerMediator.getPlanoControl();

	// GRIDPANE
	private GridPane gridCadastroPlano = new GridPane();

	// LABELS
	private Label lblGerenciamentoPlanos = new Label("GERENCIAMENTO DE PLANOS");
	private Label lblTodosPlanos = new Label("TODOS OS PLANOS CADASTRADOS");
	private Label lblNome = new Label("Nome:");
	private Label lblDescricao = new Label("Descrição:");
	private Label lblPreco = new Label("Preço:");
	private Label lblDuracao = new Label("Duração:");
	private Label messageError = new Label("");

	// TEXTFIELDS
	private TextField txtNome = new TextField();
	private TextField txtPreco = new TextField();
	private TextField txtDuracao = new TextField();
	private TextArea txtDescricao = new TextArea();

	// BUTTONS
	private Button btnCadastrar = new Button("CADASTRAR");
	private Button btnAlterar = new Button("ALTERAR");
	private Button btnExcluir = new Button("EXCLUIR");
	private Button btnCancelar = new Button("CANCELAR");

	// TABLEVIEW
	private TableView<Plano> tblPlanos = new TableView<>();

	public GerenciarPlanoBoundary() {
		iniciarTela();

	}

	public GridPane render() {
		return gridCadastroPlano;
	}

	public void iniciarParaCadastro() {

		planoControl.limparCampos();
		btnCadastrar.setDisable(false);
		btnAlterar.setDisable(true);

	}

	public void iniciarParaAlterar() {

		btnCadastrar.setDisable(true);
		btnAlterar.setDisable(false);

	}

	private void iniciarTela() {
		iniciarCss();
		iniciarEventos();
		iniciarTabelaPlano();
		iniciarGridGerenciamentoPlano();
		iniciarControler();
		ajustarTamanhoTextFields();
	}

	private void ajustarTamanhoTextFields() {
		List<TextField> textFields = gridCadastroPlano.getChildren().stream().filter(node -> node instanceof TextField)
				.map(textField -> (TextField) textField).collect(Collectors.toList());

		textFields.forEach(txt -> {
			txt.getStyleClass().add("txtField");
		});
	}

	private void iniciarEventos() {

		// CADASTRAR UM NOVO PLANO
		btnCadastrar.setOnAction(event -> {

			if (planoControl.cadastrar()) {
				UtilsGui.showAlert("AVISO", "INSERÇÃO DE PLANO", "Plano inserido com sucesso", AlertType.INFORMATION);
			}

		});

		// SELECIONANDO ITEM DA TABELA

		tblPlanos.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

			if (event.getClickCount() > 1) {
				Plano plano = tblPlanos.getSelectionModel().getSelectedItem();

				planoControl.linhaParaForm(plano);
				iniciarParaAlterar();

			}

		});

		// EXCLUIR UM PLANO
		btnExcluir.setOnAction(event -> {

			Plano plano = tblPlanos.getSelectionModel().getSelectedItem();

			// CONFIRMAR EXCLUSÃO DE PLANO
			if (UtilsGui.showConfirmation("Excluir Registro", "Deseja Excluir o Plano " + plano.getNome())) {
				if (planoControl.excluir(plano)) {
					UtilsGui.showAlert("EXCLUSÃO DE PLANO", "AVISO",
							"O PLANO COM ID " + plano.getIdPlano() + " FOI EXCLUÍDO", AlertType.INFORMATION);
				}

			}

		});

		// CONSTRAINTS PARA OS txts
		UtilsGui.setTextFieldDouble(txtPreco);
		UtilsGui.setTextFieldInteger(txtDuracao);

		// BOTÃO PARA ALTERAR UM PLANO
		btnAlterar.setOnAction(event ->

		{

			if (planoControl.alterar(tblPlanos.getSelectionModel().getSelectedItem())) {

				UtilsGui.showAlert("AVISO", "ALTERAR PLANO", "PLANO ALTERADO COM SUCESSO", AlertType.INFORMATION);

				iniciarParaCadastro();
			}

		});

		// BOTÃO DE CANCELAR E VOLTAR A TELA INICIAL
		btnCancelar.setOnAction(event -> {

			iniciarParaCadastro();

		});

	}

	private void iniciarControler() {

		Bindings.bindBidirectional(txtNome.textProperty(), planoControl.nomeProps);
		Bindings.bindBidirectional(txtPreco.textProperty(), planoControl.precoProps);
		Bindings.bindBidirectional(txtDuracao.textProperty(), planoControl.duracaoProps);
		Bindings.bindBidirectional(txtDescricao.textProperty(), planoControl.descricaoProps);
		Bindings.bindBidirectional(messageError.textProperty(), planoControl.messageError);

	}

	private void iniciarGridGerenciamentoPlano() {

		gridCadastroPlano.addRow(0, lblGerenciamentoPlanos);

		gridCadastroPlano.addRow(3, lblNome, txtNome, lblDuracao, txtDuracao);
		gridCadastroPlano.addRow(4, lblPreco, txtPreco);
		gridCadastroPlano.addRow(5, lblDescricao);
		gridCadastroPlano.addRow(6, txtDescricao);

		gridCadastroPlano.addRow(7, new Label(""));

		gridCadastroPlano.addRow(8, messageError);
		FlowPane fpBotoes = new FlowPane();
		fpBotoes.setAlignment(Pos.CENTER);
		fpBotoes.getChildren().addAll(btnCadastrar, btnAlterar, btnCancelar);
		FlowPane.setMargin(btnAlterar, new Insets(0d, 20d, 0d, 20d));
		gridCadastroPlano.addRow(9, fpBotoes);

		tblPlanos.setPrefWidth(500d);
		tblPlanos.setMaxWidth(500d);
		tblPlanos.setMinWidth(500d);
		tblPlanos.setPrefHeight(200);
		tblPlanos.setMaxHeight(200);
		tblPlanos.setMinHeight(200);

		tblPlanos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		gridCadastroPlano.add(lblTodosPlanos, 7, 0);
		FlowPane fpTabela = new FlowPane();
		fpTabela.getChildren().add(tblPlanos);
		fpTabela.setAlignment(Pos.CENTER);
		gridCadastroPlano.add(fpTabela, 7, 3);

		FlowPane fpBtnExcluir = new FlowPane();
		fpBtnExcluir.getChildren().add(btnExcluir);
		fpBtnExcluir.setAlignment(Pos.CENTER);
		gridCadastroPlano.add(fpBtnExcluir, 7, 9);

		GridPane.setColumnSpan(messageError, 4);
		GridPane.setColumnSpan(lblGerenciamentoPlanos, 4);
		GridPane.setColumnSpan(txtDescricao, 4);
		GridPane.setColumnSpan(lblDescricao, 4);
		GridPane.setColumnSpan(fpBotoes, 4);
		GridPane.setRowSpan(fpTabela, 5);
	}

	@SuppressWarnings("unchecked")
	private void iniciarTabelaPlano() {

		TableColumn<Plano, String> nomeCol = new TableColumn<>("PLANO");
		nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));

		TableColumn<Plano, Integer> duracaoCol = new TableColumn<>("DURAÇÃO");
		duracaoCol.setCellValueFactory(new PropertyValueFactory<>("duracao"));

		TableColumn<Plano, Double> precoCol = new TableColumn<>("PREÇO");
		precoCol.setCellValueFactory(new PropertyValueFactory<>("preco"));

		tblPlanos.getColumns().setAll(nomeCol, duracaoCol, precoCol);
		tblPlanos.setItems(planoControl.getPlanos());

	}

	private void iniciarCss() {

		gridCadastroPlano.getStyleClass().add("grid");

		lblGerenciamentoPlanos.getStyleClass().add("textCentralizado");
		lblTodosPlanos.getStyleClass().add("textCentralizado");

		txtDescricao.getStyleClass().add("textArea");

		messageError.getStyleClass().add("messageError");

	}
}
