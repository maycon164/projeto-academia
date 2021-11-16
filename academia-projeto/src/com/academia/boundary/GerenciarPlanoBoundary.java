package com.academia.boundary;

import com.academia.control.PlanoControl;
import com.academia.entities.Plano;
import com.academia.factory.ControllerMediator;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

	/*
	 * public static void main(String[] args) {
	 * Application.launch(GerenciarPlanoBoundary.class, args); }
	 * 
	 * @Override public void start(Stage primaryStage) throws Exception {
	 * this.iniciarTela();
	 * 
	 * Scene scene = new Scene(splitGerenciamentoPlano, 500, 500);
	 * scene.getStylesheets().add("style.css"); primaryStage.setScene(scene);
	 * primaryStage.show(); }
	 */
	
	  public GerenciarPlanoBoundary() { iniciarTela();
	  
	  }
	 

	public SplitPane render() {
		return splitGerenciamentoPlano;
	}

	private void iniciarTela() {
		iniciarCss();
		iniciarTabelaPlano();
		iniciarSplitPane();
		iniciarGridGerenciamentoPlano();
		iniciarVboxTableViewPlanos();
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
		gridCadastroPlano.addRow(8, new Label(""));
		
		gridCadastroPlano.addRow(9, btnCadastrar, btnAlterar, btnCancelar);

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
		tblPlanos.setItems(FXCollections.observableArrayList(planoControl.getPlanos()));

	}

	private void iniciarCss() {

		gridCadastroPlano.getStyleClass().add("grid");

		lblGerenciamentoPlanos.getStyleClass().add("textCentralizado");
		lblTodosPlanos.getStyleClass().add("textCentralizado");

		txtDescricao.getStyleClass().add("textArea");

	}
}
