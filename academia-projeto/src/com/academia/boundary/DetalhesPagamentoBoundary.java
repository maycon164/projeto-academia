package com.academia.boundary;

import com.academia.control.PagamentoControl;
import com.academia.factory.ControllerMediator;
import com.academia.util.UtilsGui;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class DetalhesPagamentoBoundary {

	// CONTROL
	private PagamentoControl pagamentoControl = ControllerMediator.getPagamentoControl();

	// LABEL
	private Label lblDetalhesPagamento = new Label("DETALHES DO PAGAMENTO");
	private Label lblCpf = new Label("CPF aluno:");
	private Label lblnome = new Label("Nome aluno:");
	private Label lblPlano = new Label("Plano:");
	private Label lblValor = new Label("Valor:");
	private Label lblDataPagamento = new Label("Data:");
	private Label lblSelecioneFormaPagamento = new Label("SELECIONE A FORMA DE PAGAMENTO:");

	// BUTTON
	private Button btnPagar = new Button("PAGAR");
	private Button btnCancelar = new Button("CANCELAR");

	// TEXTFIELD
	private TextField txtCpf = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtPlano = new TextField();
	private TextField txtValor = new TextField();
	private TextField txtDataPagamento = new TextField();

	// ChoiceBox
	private ChoiceBox<String> cbFormaPagamento = new ChoiceBox<>();

	// GRID
	private GridPane gridDetalhesPagamento = new GridPane();

	public DetalhesPagamentoBoundary() {
		iniciarTela();
	}

	public GridPane render() {
		return gridDetalhesPagamento;
	}

	private void iniciarTela() {
		iniciarGridDetalhesPagamento();
		iniciarCss();
		iniciarCbFormaPagamento();
		iniciarEventos();
		iniciarControl();
	}

	private void iniciarControl() {

		Bindings.bindBidirectional(txtCpf.textProperty(), pagamentoControl.cpfProps);
		Bindings.bindBidirectional(txtNome.textProperty(), pagamentoControl.nomeProps);
		Bindings.bindBidirectional(txtPlano.textProperty(), pagamentoControl.planoProps);
		Bindings.bindBidirectional(txtValor.textProperty(), pagamentoControl.valorProps);
		Bindings.bindBidirectional(txtDataPagamento.textProperty(), pagamentoControl.dataPagamentoProps);
		Bindings.bindBidirectional(cbFormaPagamento.valueProperty(), pagamentoControl.tipoPagamentoProps);

	}

	private void iniciarEventos() {

		// PAGAR
		btnPagar.setOnAction(event -> {

			if (cbFormaPagamento != null) {

				if (pagamentoControl.realizarPagamento(cbFormaPagamento.getValue())) {

					UtilsGui.showAlert("PAGAMENTO", " REALIZAR PAGAMENTO", "PAGAMENTO REALIZADO COM SUCESSO",
							AlertType.INFORMATION);

					pagamentoControl.atualizarPagamentos();
				}
				;

			}

		});
	}

	private void iniciarCbFormaPagamento() {

		cbFormaPagamento.getItems().addAll("DINHEIRO", "CARTAO_CREDITO", "CARTAO_DEBITO", "BOLETO_BANCARIO");
	}

	private void iniciarGridDetalhesPagamento() {
		gridDetalhesPagamento.addRow(0, lblDetalhesPagamento);
		GridPane.setColumnSpan(lblDetalhesPagamento, 4);

		gridDetalhesPagamento.addRow(2, lblCpf, txtCpf, lblnome, txtNome);
		gridDetalhesPagamento.addRow(3, lblPlano, txtPlano, lblValor, txtValor);
		gridDetalhesPagamento.addRow(4, lblDataPagamento, txtDataPagamento);

		gridDetalhesPagamento.addRow(5, lblSelecioneFormaPagamento);
		GridPane.setColumnSpan(lblSelecioneFormaPagamento, 4);

		FlowPane fpPagamento = new FlowPane();
		fpPagamento.getChildren().add(cbFormaPagamento);
		FlowPane.setMargin(cbFormaPagamento, new Insets(30d, 0d, 30d, 0d));
		fpPagamento.setAlignment(Pos.CENTER);

		gridDetalhesPagamento.addRow(6, fpPagamento);
		GridPane.setColumnSpan(fpPagamento, 4);

		FlowPane fpBotoes = new FlowPane();
		fpBotoes.getChildren().addAll(btnPagar, btnCancelar);
		fpBotoes.setAlignment(Pos.CENTER);

		gridDetalhesPagamento.addRow(7, fpBotoes);
		FlowPane.setMargin(btnCancelar, new Insets(0d, 0d, 0d, 40d));
		GridPane.setColumnSpan(fpBotoes, 4);

		gridDetalhesPagamento.getChildren().forEach(node -> {
			if (node instanceof TextField) {
				node.setDisable(true);
			}
		});

	}

	private void iniciarCss() {
		gridDetalhesPagamento.getStyleClass().add("grid");
		lblDetalhesPagamento.getStyleClass().add("textCentralizado");
		lblSelecioneFormaPagamento.getStyleClass().add("textCentralizado");

	}
}
