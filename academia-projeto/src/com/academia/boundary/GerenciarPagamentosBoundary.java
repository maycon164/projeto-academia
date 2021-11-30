package com.academia.boundary;

import com.academia.control.PagamentoControl;
import com.academia.entities.Pagamento;
import com.academia.factory.ControllerMediator;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GerenciarPagamentosBoundary {

	// CONTROL
	private PagamentoControl pagamentoControl = ControllerMediator.getPagamentoControl();

	// LABEL
	private Label lblTodosPagamentos = new Label("TODOS OS PAGAMENTOS");
	private Label lblPesquisar = new Label("Pesquisar: ");

	// BOTOES
	private TextField txtPesquisarPagamento = new TextField();
	private ChoiceBox<String> cbStatus = new ChoiceBox<>();

	// TABLE VIEW
	private TableView<Pagamento> tblPagamentos = new TableView<>();

	// BUTTON
	private Button btnDetalhes = new Button("DETALHES");

	// PANES
	private GridPane gridGerenciarPagamentos = new GridPane();
	private FlowPane fpPesquisa = new FlowPane();
	private FlowPane fpBotoes = new FlowPane();

	// STAGE
	private DetalhesPagamentoBoundary detalhesPagamentoBoundary = new DetalhesPagamentoBoundary();
	private Stage s1 = new Stage();

	public GerenciarPagamentosBoundary() {
		iniciarTela();
	}

	public GridPane render() {
		return gridGerenciarPagamentos;
	}

	private void iniciarTela() {
		iniciarControl();
		iniciarGrid();
		iniciarModalDetalhesPagamento();
		iniciarChoiceBox();
		iniciarTabela();
		iniciarCss();
		iniciarEventos();
	}

	private void iniciarControl() {
		Bindings.bindBidirectional(cbStatus.valueProperty(), pagamentoControl.statusProps);
		Bindings.bindBidirectional(txtPesquisarPagamento.textProperty(), pagamentoControl.pesquisarPagamentosProps);
	}

	private void iniciarModalDetalhesPagamento() {
		Scene sceneTeste = new Scene(detalhesPagamentoBoundary.render(), 750, 400);
		sceneTeste.getStylesheets().add("style.css");

		s1.initOwner(MenuBarBoundary.getRoot());
		s1.initModality(Modality.APPLICATION_MODAL);
		s1.setTitle("DETALHES DO PAGAMENTO");
		s1.setScene(sceneTeste);
	}

	private void iniciarEventos() {
		// BTN DETALHES
		btnDetalhes.setOnAction(event -> {
			Pagamento p = tblPagamentos.getSelectionModel().getSelectedItem();
			if (p != null) {
				pagamentoControl.setPagamento(p);
				s1.show();
			}
		});

	}

	private void iniciarGrid() {
		gridGerenciarPagamentos.addRow(0, lblTodosPagamentos);

		fpPesquisa.getChildren().addAll(lblPesquisar, txtPesquisarPagamento, cbStatus);
		txtPesquisarPagamento.setPrefWidth(500d);
		fpPesquisa.setAlignment(Pos.CENTER);
		FlowPane.setMargin(cbStatus, new Insets(0d, 0d, 0d, 40d));

		gridGerenciarPagamentos.addRow(1, fpPesquisa);

		VBox vboxTabela = new VBox();
		vboxTabela.getChildren().add(tblPagamentos);

		tblPagamentos.setPrefWidth(900d);
		tblPagamentos.setMaxWidth(900d);
		tblPagamentos.setMinWidth(900d);

		tblPagamentos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		vboxTabela.setAlignment(Pos.CENTER);

		fpBotoes.getChildren().add(btnDetalhes);
		gridGerenciarPagamentos.addRow(2, vboxTabela);
		gridGerenciarPagamentos.addRow(5, fpBotoes);

	}

	private void iniciarCss() {
		gridGerenciarPagamentos.getStyleClass().add("grid");
		lblTodosPagamentos.getStyleClass().add("textCentralizado");
		tblPagamentos.getStyleClass().add("table-view");
		fpPesquisa.getStyleClass().add("margin");
		fpBotoes.getStyleClass().addAll("margin", "centralizar");
	}

	private void iniciarChoiceBox() {
		cbStatus.getItems().addAll("PAGO", "ABERTO", "CANCELADO");

	}

	@SuppressWarnings("unchecked")
	private void iniciarTabela() {

		TableColumn<Pagamento, String> cpfCol = new TableColumn<>("CPF");
		cpfCol.setCellValueFactory(new PropertyValueFactory<>("cpfAluno"));

		TableColumn<Pagamento, String> nomeCol = new TableColumn<>("NOME");
		nomeCol.setCellValueFactory(new PropertyValueFactory<>("nomeAluno"));

		TableColumn<Pagamento, String> planoCol = new TableColumn<>("PLANO");
		planoCol.setCellValueFactory(new PropertyValueFactory<>("nomePlano"));

		TableColumn<Pagamento, String> valorCol = new TableColumn<>("VALOR");
		valorCol.setCellValueFactory(item -> {
			double valorTotal = item.getValue().getValorTotal();
			return new ReadOnlyStringWrapper("R$" + String.valueOf(valorTotal));
		});

		TableColumn<Pagamento, String> statusCol = new TableColumn<>("STATUS");
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

		tblPagamentos.getColumns().setAll(cpfCol, nomeCol, planoCol, valorCol, statusCol);
		tblPagamentos.setItems(pagamentoControl.getPagamentos());

	}

	public void atualizarPagamentos() {
		pagamentoControl.atualizarPagamentos();
	}

}