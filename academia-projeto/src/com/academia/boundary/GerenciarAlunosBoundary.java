package com.academia.boundary;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.academia.control.AlunoControl;
import com.academia.control.PlanoControl;
import com.academia.entities.Aluno;
import com.academia.entities.Plano;
import com.academia.factory.ControllerMediator;
import com.academia.util.UtilsGui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class GerenciarAlunosBoundary {

	// CONTROLL
	private AlunoControl alunoControl = ControllerMediator.getAlunoControl();
	private PlanoControl planoControl = ControllerMediator.getPlanoControl();

	// GRID E OUTROS PANES
	private VBox vboxAlunoPlano = new VBox();
	private FlowPane fpPesquisa = new FlowPane();
	private FlowPane fpBotoes = new FlowPane();

	// TABLE VIEW
	private TableView<Aluno> tblAlunoPlano = new TableView<>();

	// LABELS
	private Label lblGerenciamentoDeAluno = new Label("Gerenciamento de Aluno");

	// TEXTFIELD
	private TextField txtPesquisa = new TextField();

	// CHOICEBOX
	private ChoiceBox<Plano> cbPlano = new ChoiceBox<>();

	// BUTTONS
	private Button btnDetalhes = new Button("DETALHES");
	private Button btnExcluir = new Button("EXCLUIR");

	// SIMPLE DATE FORMAT
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public GerenciarAlunosBoundary() {
		this.iniciarTela();
	}

	public VBox render() {
		alunoControl.atualizarListaAluno();
		return vboxAlunoPlano;
	}

	private void iniciarTela() {

		this.iniciarControl();
		this.iniciarTabelaAlunoPlano();
		this.configurarVboxAlunoPlano();
		this.iniciarChoiceBoxPlano();
		this.iniciarEventos();
		this.configurarCss();

	}

	private void iniciarEventos() {
		// EVENTO DE ORDENAR O FILTERED LIST
		cbPlano.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {

			System.out.println("SELECIONADO: " + cbPlano.getValue().getNome());

		});

		// EVENTO DE TROCA DE TELA
		btnDetalhes.setOnAction((event) -> {

			String cpf = tblAlunoPlano.getSelectionModel().getSelectedItem().getCpf();
			alunoControl.setAluno(cpf);

			// PRECISA RENDERIZAR COM OS CAMPOS PREENCHIDOS, BTNALTER DISPONIVEL, TXTCPF
			// DISABLE
			AlunoCadastroBoundary alterarAluno = new AlunoCadastroBoundary();

			alterarAluno.iniciarAlterar();

			Scene scene = vboxAlunoPlano.getScene();
			BorderPane bpPrincipal = (BorderPane) scene.getRoot();

			bpPrincipal.setCenter(alterarAluno.render());

		});

		// EVENTO DE DELEÇÃO DE ALUNO E SEU PLANO;
		btnExcluir.setOnAction(event -> {

			Aluno aluno = tblAlunoPlano.getSelectionModel().getSelectedItem();

			// CONFIRMAR EXCLUSÃO
			UtilsGui.showConfirmation("Excluir Aluno", "Deseja excluir o aluno " + aluno.getNome()).ifPresent(b -> {

				if (b.getText().equalsIgnoreCase("sim")) {

					boolean excluir = alunoControl.excluir(aluno);

					if (excluir) {
						UtilsGui.showAlert("EXCLUSÃO DE ALUNO", "AVISO",
								"O ALUNO COM O CPF " + aluno.getCpf() + " FOI EXCLUÍDO", AlertType.INFORMATION);
					}
				}

			});

		});

		cbPlano.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {

			Plano plano = cbPlano.getValue();
			Plano plano2 = tblAlunoPlano.getSelectionModel().getSelectedItem().getAssinatura().getPlano();

			System.out.println(plano == plano2);

		});

	}

	private void configurarVboxAlunoPlano() {

		tblAlunoPlano.setMaxWidth(900d);
		tblAlunoPlano.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		vboxAlunoPlano.setAlignment(Pos.CENTER);

		vboxAlunoPlano.setPadding(new Insets(0d, 30d, 40d, 0d));

		// CAMPOS DE PESQUISA
		FlowPane.setMargin(cbPlano, new Insets(0d, 0d, 0d, 20d));
		fpPesquisa.getChildren().addAll(new Label("Pesquisar: "), txtPesquisa, cbPlano);

		// BOTOES
		fpBotoes.getChildren().addAll(btnDetalhes, btnExcluir);
		FlowPane.setMargin(btnDetalhes, new Insets(0d, 10d, 0d, 10d));
		FlowPane.setMargin(btnExcluir, new Insets(0d, 10d, 0d, 10d));

		vboxAlunoPlano.getChildren().addAll(lblGerenciamentoDeAluno, fpPesquisa, tblAlunoPlano, fpBotoes);

	}

	private void configurarCss() {
		lblGerenciamentoDeAluno.getStyleClass().addAll("textCentralizado", "margin");
		fpPesquisa.getStyleClass().addAll("textCentralizado", "margin");
		fpBotoes.getStyleClass().addAll("textCentralizado", "margin");
		txtPesquisa.getStyleClass().addAll("txtPesquisa");
	}

	@SuppressWarnings("unchecked")
	private void iniciarTabelaAlunoPlano() {

		TableColumn<Aluno, String> alunoCol = new TableColumn<>("ALUNO");
		alunoCol.setCellValueFactory(new PropertyValueFactory<>("nome"));

		// COMO PEGAR O PLANO
		TableColumn<Aluno, String> planoCol = new TableColumn<>("PLANO");
		planoCol.setCellValueFactory(item -> {
			String plano = item.getValue().getAssinatura().getPlano().getNome();
			return new ReadOnlyStringWrapper(plano);
		});

		TableColumn<Aluno, String> duracaoCol = new TableColumn<>("DURAÇÃO (DIAS)");
		duracaoCol.setCellValueFactory(item -> {
			Integer duracao = item.getValue().getAssinatura().getPlano().getDuracao();
			return new ReadOnlyStringWrapper(String.valueOf(duracao));
		});

		TableColumn<Aluno, String> dataInicioCol = new TableColumn<>("DATA INICIO");

		dataInicioCol.setCellValueFactory(item -> {
			Date d = item.getValue().getAssinatura().getDataInicio();
			return new ReadOnlyStringWrapper(sdf.format(d));
		});

		TableColumn<Aluno, String> dataExpiracaoCol = new TableColumn<>("DATA EXPIRAÇÃO");

		dataExpiracaoCol.setCellValueFactory(item -> {
			Date d = item.getValue().getAssinatura().getDataExpiracao();
			return new ReadOnlyStringWrapper(sdf.format(d));
		});

		tblAlunoPlano.getColumns().setAll(alunoCol, planoCol, duracaoCol, dataInicioCol, dataExpiracaoCol);
		tblAlunoPlano.setItems(alunoControl.getAlunos());

	}

	private void iniciarChoiceBoxPlano() {

		cbPlano.setItems(planoControl.getPlanos());

	}

	private void iniciarControl() {
		Bindings.bindBidirectional(txtPesquisa.textProperty(), alunoControl.pesquisaProps);
	}
}
