package com.academia.boundary;

import com.academia.control.AlunoControl;
import com.academia.entities.Pagamento;
import com.academia.entities.Plano;
import com.academia.factory.ControllerMediator;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AlunoAlterarBoundary extends Application {

	// CONTROL
	private AlunoControl alunoControl = ControllerMediator.getAlunoControl();

	// LABELS
	private Label lblAluno = new Label("ALTERAR ALUNO");
	private Label lblDadosCadastrais = new Label("DADOS DO ALUNO");
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

	// PLANOS
	private Label lblPlanos = new Label("Planos Assinados");

	private ListView<Plano> lvPlanos = new ListView<>();

	private Button btnAdicionar = new Button("Adicionar");
	private Button btnRemover = new Button("Remover");

	// PAGAMENTOS
	private Label lblPagamentos = new Label("MENSALIDADES");
	private TableView<Pagamento> tblPagamentos = new TableView<>();
	private Button btnDetalhes = new Button("Detalhes");

	private GridPane gridAlterarAluno = new GridPane();

	// BUTTONS
	private Button btnAlterar = new Button("Alterar");
	private Button btnCancelar = new Button("Cancelar");

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
		iniciarTabelaPagamentos();
		iniciarCss();
	}

	@SuppressWarnings("unchecked")
	private void iniciarTabelaPagamentos() {

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

		tblPagamentos.getColumns().setAll(nomeCol, planoCol, valorCol, statusCol);
		// tblPagamentos.setItems();
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

		// PLANOS ASSINADOS

		lvPlanos.setPrefHeight(200d);
		lvPlanos.setPrefWidth(350d);
		lvPlanos.setMaxWidth(350d);
		lvPlanos.setMinWidth(350d);

		FlowPane fpBotoesPlano = new FlowPane();
		fpBotoesPlano.getChildren().addAll(btnAdicionar, btnRemover);
		FlowPane.setMargin(btnRemover, new Insets(0d, 0d, 0d, 30d));
		fpBotoesPlano.setAlignment(Pos.CENTER);

		FlowPane fpListaPlanos = new FlowPane();
		fpListaPlanos.getChildren().add(lvPlanos);

		fpListaPlanos.setAlignment(Pos.CENTER);

		gridAlterarAluno.add(lblPlanos, 5, 0);
		gridAlterarAluno.add(fpListaPlanos, 5, 2);
		gridAlterarAluno.add(fpBotoesPlano, 5, 7);

		// PARTE DE PAGAMENTOS
		gridAlterarAluno.add(lblPagamentos, 5, 8);

		FlowPane fpTabelaPagamentos = new FlowPane();
		fpTabelaPagamentos.getChildren().add(tblPagamentos);
		gridAlterarAluno.add(fpTabelaPagamentos, 5, 9);
		fpTabelaPagamentos.setAlignment(Pos.CENTER);

		gridAlterarAluno.add(btnDetalhes, 5, 10);

		GridPane.setColumnSpan(lblPagamentos, 3);
		GridPane.setColumnSpan(fpTabelaPagamentos, 3);

		tblPagamentos.setPrefHeight(200d);
		tblPagamentos.setMaxWidth(400d);
		tblPagamentos.setMinWidth(400d);
		tblPagamentos.setPrefWidth(400d);

		GridPane.setColumnSpan(btnDetalhes, 4);
		// SPANS
		GridPane.setColumnSpan(lblPlanos, 3);
		GridPane.setColumnSpan(fpListaPlanos, 3);
		GridPane.setRowSpan(fpListaPlanos, 5);
		GridPane.setColumnSpan(fpBotoesPlano, 3);

		GridPane.setColumnSpan(lblDadosCadastrais, 4);
		GridPane.setColumnSpan(lblDadosEndereco, 4);

		// BUTTONS DE ALTERAR E EXCLUIR
		FlowPane fpBotoesAlterarCancelar = new FlowPane();
		fpBotoesAlterarCancelar.getChildren().addAll(btnAlterar, btnCancelar);
		GridPane.setColumnSpan(fpBotoesAlterarCancelar, 4);
		FlowPane.setMargin(btnAlterar, new Insets(0d, 30d, 0d, 0d));

		GridPane.setMargin(fpBotoesAlterarCancelar, new Insets(40d, 0d, 0d, 0d));

		gridAlterarAluno.add(fpBotoesAlterarCancelar, 1, 8);
		gridAlterarAluno.setGridLinesVisible(true);
		fpBotoesAlterarCancelar.setAlignment(Pos.CENTER);
	}

	private void iniciarCss() {
		gridAlterarAluno.getStyleClass().add("grid");
		lblDadosCadastrais.getStyleClass().add("textCentralizado");
		lblDadosEndereco.getStyleClass().add("textCentralizado");
		lblPlanos.getStyleClass().add("textCentralizado");
		lblPagamentos.getStyleClass().add("textCentralizado");
	}

}
