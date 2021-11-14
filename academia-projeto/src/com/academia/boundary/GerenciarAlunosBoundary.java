package com.academia.boundary;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.academia.control.AlunoControl;
import com.academia.control.PlanoControl;
import com.academia.dto.AlunoPlanoDTO;
import com.academia.entities.Plano;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GerenciarAlunosBoundary extends Application {

	// CONTROLL
	private AlunoControl alunoControl = new AlunoControl();
	private PlanoControl planoControl = new PlanoControl();

	// GRID E OUTROS PANES
	private VBox vboxAlunoPlano = new VBox();
	FlowPane fpPesquisa = new FlowPane();

	// TABLE VIEW
	private TableView<AlunoPlanoDTO> tblAlunoPlano = new TableView<>();

	// LABELS
	private Label lblGerenciamentoDeAluno = new Label("Gerenciamento de Aluno");

	// TEXTFIELD
	private TextField txtPesquisa = new TextField();

	// CHOICEBOX
	private ChoiceBox<Plano> cbPlano = new ChoiceBox<>();

	// SIMPLE DATE FORMAT
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public static void main(String[] args) {
		Application.launch(GerenciarAlunosBoundary.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Scene scene = new Scene(vboxAlunoPlano, 1020, 1000);
		scene.getStylesheets().add("style.css");

		this.iniciarControl();
		this.iniciarTabelaAlunoPlano();
		this.configurarVboxAlunoPlano();
		this.iniciarChoiceBoxPlano();
		this.configurarCss();

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void configurarVboxAlunoPlano() {

		tblAlunoPlano.setMaxWidth(900d);
		tblAlunoPlano.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		vboxAlunoPlano.setAlignment(Pos.CENTER);

		vboxAlunoPlano.setPadding(new Insets(0d, 30d, 40d, 0d));

		// CAMPOS DE PESQUISA
		FlowPane.setMargin(cbPlano, new Insets(0d, 0d, 0d, 20d));
		fpPesquisa.getChildren().addAll(new Label("Pesquisar: "),txtPesquisa, cbPlano);

		vboxAlunoPlano.getChildren().addAll(lblGerenciamentoDeAluno, fpPesquisa, tblAlunoPlano);

	}

	private void configurarCss() {
		lblGerenciamentoDeAluno.getStyleClass().addAll("textCentralizado", "margin");
		fpPesquisa.getStyleClass().addAll("textCentralizado", "margin");
		txtPesquisa.getStyleClass().addAll("txtPesquisa");
	}

	@SuppressWarnings("unchecked")
	private void iniciarTabelaAlunoPlano() {
		TableColumn<AlunoPlanoDTO, String> alunoCol = new TableColumn<>("ALUNO");
		alunoCol.setCellValueFactory(new PropertyValueFactory<>("aluno"));

		TableColumn<AlunoPlanoDTO, String> planoCol = new TableColumn<>("PLANO");
		planoCol.setCellValueFactory(new PropertyValueFactory<>("plano"));

		TableColumn<AlunoPlanoDTO, Integer> duracaoCol = new TableColumn<>("DURAÇÃO (DIAS)");
		duracaoCol.setCellValueFactory(new PropertyValueFactory<>("duracao"));

		TableColumn<AlunoPlanoDTO, String> dataInicioCol = new TableColumn<>("DATA INICIO");

		dataInicioCol.setCellValueFactory(item -> {
			Date d = item.getValue().getDataInicio();
			return new ReadOnlyStringWrapper(sdf.format(d));
		});

		TableColumn<AlunoPlanoDTO, String> dataExpiracaoCol = new TableColumn<>("DATA EXPIRAÇÃO");

		dataExpiracaoCol.setCellValueFactory(item -> {
			Date d = item.getValue().getDataExpiracao();
			return new ReadOnlyStringWrapper(sdf.format(d));
		});

		tblAlunoPlano.getColumns().setAll(alunoCol, planoCol, duracaoCol, dataInicioCol, dataExpiracaoCol);
		tblAlunoPlano.setItems(alunoControl.getAlunosPlanosDTO());

	}

	private void iniciarChoiceBoxPlano() {
		cbPlano.getItems().addAll(FXCollections.observableArrayList(planoControl.getPlanos()));
	}
	
	private void iniciarControl() {
		Bindings.bindBidirectional(txtPesquisa.textProperty(), alunoControl.pesquisaProps);
	}
}
