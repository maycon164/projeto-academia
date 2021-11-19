package com.academia.boundary;

import com.academia.control.PagamentoControl;
import com.academia.entities.Pagamento;
import com.academia.factory.ControllerMediator;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PagamentosBoundary extends Application {

	// CONTROL
	private PagamentoControl pagamentoControl = ControllerMediator.getPagamentoControl();

	// LABEL
	private Label lblTodosPagamentos = new Label("TODOS OS PAGAMENTOS");

	// BOTOES
	private TextField txtPagamento = new TextField();

	// TABLE VIEW
	private TableView<Pagamento> tblPagamentos = new TableView<>();

	// BUTTON
	private Button btnDetalhes = new Button("DETALHES");

	// PANES
	private GridPane gridGerenciarPagamentos = new GridPane();

	public static void main(String[] args) {
		Application.launch(PagamentosBoundary.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Scene scene = new Scene(gridGerenciarPagamentos, 500, 500);
		scene.getStylesheets().add("style.css");
		iniciarTela();

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void iniciarTela() {
		iniciarGrid();
		iniciarTabela();
	}

	private void iniciarGrid() {
		gridGerenciarPagamentos.addRow(0, lblTodosPagamentos);

		VBox vboxTabela = new VBox();
		vboxTabela.getChildren().add(tblPagamentos);

		tblPagamentos.setMaxWidth(900d);
		tblPagamentos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		vboxTabela.setAlignment(Pos.CENTER);

		gridGerenciarPagamentos.addRow(1, txtPagamento);

		gridGerenciarPagamentos.addRow(2, vboxTabela);
		gridGerenciarPagamentos.addRow(5, btnDetalhes);

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

}
