package com.academia.boundary;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.academia.control.AlunoControl;
import com.academia.control.PlanoControl;
import com.academia.dto.AlunoPlanoDTO;
import com.academia.entities.Plano;
import com.academia.factory.ControllerMediator;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
	private TableView<AlunoPlanoDTO> tblAlunoPlano = new TableView<>();

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
			AlunoCadastroBoundary exemplo = new AlunoCadastroBoundary();

			exemplo.iniciarAlterar();

			Scene scene = vboxAlunoPlano.getScene();
			BorderPane bpPrincipal = (BorderPane) scene.getRoot();

			bpPrincipal.setCenter(exemplo.render());

		});

		// EVENTO DE DELEÇÃO DE ALUNO E SEU PLANO;
		btnExcluir.setOnAction(event -> {

			Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
			ButtonType btnSim = new ButtonType("Sim");
			ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
			String nome = tblAlunoPlano.getSelectionModel().getSelectedItem().getAluno();
			dialogoExe.setTitle("Excluir Registro");
			dialogoExe.setHeaderText("Deseja Excluir o Aluno " + nome + "?");
			dialogoExe.getButtonTypes().setAll(btnSim, btnCancelar);

			dialogoExe.showAndWait().ifPresent(b -> {

				if (b == btnSim) {
					System.out.println("Deletar.....");
					String cpf = tblAlunoPlano.getSelectionModel().getSelectedItem().getCpf();
					boolean excluir = alunoControl.excluir(tblAlunoPlano.getSelectionModel().getSelectedItem());

					if (excluir) {
						Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
						dialogoInfo.setTitle("EXCLUSÃO DE ALUNO");
						dialogoInfo.setHeaderText("AVISO");
						dialogoInfo.setContentText("O ALUNO COM O CPF " + cpf + " FOI EXCLUÍDO");
						dialogoInfo.showAndWait();
					}

				} else if (b == btnCancelar) {
					System.out.println("CANCELOU OPÇÃO DE CANCELAR");
				}
			});

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

		cbPlano.setItems(planoControl.getPlanos());

	}

	private void iniciarControl() {
		Bindings.bindBidirectional(txtPesquisa.textProperty(), alunoControl.pesquisaProps);
	}
}
