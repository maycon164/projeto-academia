package com.academia.boundary;

import com.academia.control.PlanoControl;
import com.academia.entities.Plano;
import com.academia.factory.ControllerMediator;
import com.academia.util.UtilsGui;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GerenciarPlanoBoundary {

	// CONTROL
	PlanoControl planoControl = ControllerMediator.getPlanoControl();

	// GRIDPANE
	private SplitPane splitGerenciamentoPlano = new SplitPane();
	private GridPane gridCadastroPlano = new GridPane();
	private VBox vboxTableViewPlanos = new VBox();

	// LABELS
	private Label lblGerenciamentoPlanos = new Label("GERENCIAMENTO DE PLANOS DE ACADEMIA");
	private Label lblTodosPlanos = new Label("TODOS OS PLANOS CADASTRADOS");
	private Label lblNome = new Label("Nome: ");
	private Label lblDescricao = new Label("Descrição: ");
	private Label lblPreco = new Label("Preço: ");
	private Label lblDuracao = new Label("Duração(DIAS): ");
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

	public SplitPane render() {
		return splitGerenciamentoPlano;
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
		iniciarSplitPane();
		iniciarGridGerenciamentoPlano();
		iniciarVboxTableViewPlanos();
		iniciarControler();
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
			UtilsGui.showConfirmation("Excluir Registro", "Deseja Excluir o Plano " + plano.getNome()).ifPresent(b -> {

				if (b.getText().equalsIgnoreCase("sim")) {

					boolean excluir = planoControl.excluir(plano);

					if (excluir) {
						UtilsGui.showAlert("EXCLUSÃO DE PLANO", "AVISO",
								"O PLANO COM ID " + plano.getIdPlano() + " FOI EXCLUÍDO", AlertType.INFORMATION);
					}
				}

			});

		});

		// CONSTRAINTS PARA OS txts
		UtilsGui.setTextFieldDouble(txtPreco);
		UtilsGui.setTextFieldInteger(txtDuracao);

		// BOTÃO PARA ALTERAR UM PLANO
		btnAlterar.setOnAction(event -> {

			if (planoControl.atualizar(tblPlanos.getSelectionModel().getSelectedItem())) {

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

	private void iniciarSplitPane() {

		tblPlanos.setMaxWidth(500d);
		tblPlanos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		vboxTableViewPlanos.setAlignment(Pos.CENTER);

		splitGerenciamentoPlano.setOrientation(Orientation.HORIZONTAL);
		splitGerenciamentoPlano.getItems().addAll(gridCadastroPlano, vboxTableViewPlanos);
	}

	private void iniciarVboxTableViewPlanos() {

		vboxTableViewPlanos.getChildren().addAll(lblTodosPlanos, tblPlanos, btnExcluir);
		VBox.setMargin(tblPlanos, new Insets(40d, 0d, 40d, 0d));

	}

	private void iniciarGridGerenciamentoPlano() {

		gridCadastroPlano.addRow(0, lblGerenciamentoPlanos);

		gridCadastroPlano.addRow(1, new Label(""));
		gridCadastroPlano.addRow(2, new Label(""));

		gridCadastroPlano.addRow(3, lblNome, txtNome, lblDuracao, txtDuracao);
		gridCadastroPlano.addRow(4, lblPreco, txtPreco);
		gridCadastroPlano.addRow(5, lblDescricao);
		gridCadastroPlano.addRow(6, txtDescricao);

		gridCadastroPlano.addRow(7, new Label(""));

		gridCadastroPlano.addRow(8, messageError);

		gridCadastroPlano.addRow(9, btnCadastrar, btnAlterar, btnCancelar);

		GridPane.setColumnSpan(messageError, 4);
		GridPane.setColumnSpan(lblGerenciamentoPlanos, 4);
		GridPane.setColumnSpan(txtDescricao, 4);
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
