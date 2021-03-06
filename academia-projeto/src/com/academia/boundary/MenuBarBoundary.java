package com.academia.boundary;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuBarBoundary extends Application {

	private AlunoCadastroBoundary alunoCadastroBoundary = new AlunoCadastroBoundary();
	private GerenciarAlunosBoundary gerenciarAlunosBoundary = new GerenciarAlunosBoundary();
	private GerenciarPlanoBoundary gerenciarPlanoBoundary = new GerenciarPlanoBoundary();
	private GerenciarInstrutorBoundary gerenciarInstrutorBoundary = new GerenciarInstrutorBoundary();
	private GerenciarPagamentosBoundary gerenciarPagamentosBoundary = new GerenciarPagamentosBoundary();
	private TelaInicialBoundary telaInicialBoundary = new TelaInicialBoundary();

	private static Stage root;

	public static void main(String[] args) {
		Application.launch(MenuBarBoundary.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		root = primaryStage;
		BorderPane pane = new BorderPane();

		MenuBar menuBar = new MenuBar();

		pane.setTop(menuBar);

		Menu menuCadastros = new Menu("Realizar Cadastros");
		Menu menuGerenciar = new Menu("Gerenciar");
		Menu menuOutros = new Menu("Outros");

		MenuItem itemCadastrarAluno = new MenuItem("Cadastrar Aluno");

		itemCadastrarAluno.setOnAction(event -> {
			alunoCadastroBoundary.iniciarCadastrar();
			pane.setCenter(alunoCadastroBoundary.render());
		});

		MenuItem itemCadastrarPlano = new MenuItem("Cadastrar Plano");

		itemCadastrarPlano.setOnAction(event -> {
			gerenciarPlanoBoundary.iniciarParaCadastro();
			pane.setCenter(gerenciarPlanoBoundary.render());
		});

		MenuItem itemCadastrarInstrutor = new MenuItem("Cadastrar Instrutor");
		itemCadastrarInstrutor.setOnAction(event -> {
			gerenciarInstrutorBoundary.iniciarParaCadastro();
			pane.setCenter(gerenciarInstrutorBoundary.render());
		});

		menuCadastros.getItems().addAll(itemCadastrarAluno, itemCadastrarPlano, itemCadastrarInstrutor);

		MenuItem itemGerenciarAlunos = new MenuItem("Gerenciar Alunos");

		itemGerenciarAlunos.setOnAction(event -> {
			System.out.println("BOTAO CLICK");
			pane.setCenter(gerenciarAlunosBoundary.render());
		});

		MenuItem itemGerenciarPagamentos = new MenuItem("Gerenciar Pagamentos");
		itemGerenciarPagamentos.setOnAction(event -> {
			gerenciarPagamentosBoundary.atualizarPagamentos();
			pane.setCenter(gerenciarPagamentosBoundary.render());
		});

		menuGerenciar.getItems().addAll(itemGerenciarAlunos, itemGerenciarPagamentos);

		MenuItem itemMenuSair = new MenuItem("Sair");
		itemMenuSair.setOnAction(event -> {
			Platform.exit();
			System.exit(0);
		});

		MenuItem itemTelaInicial = new MenuItem("Tela Inicial");
		itemTelaInicial.setOnAction(event -> {
			telaInicialBoundary.atualizarTela();
			pane.setCenter(telaInicialBoundary.render());
		});

		menuOutros.getItems().addAll(itemTelaInicial, itemMenuSair);

		menuBar.getMenus().addAll(menuCadastros, menuGerenciar, menuOutros);

		Scene scene = new Scene(pane, 500, 500);
		scene.getStylesheets().add("style.css");

		pane.setCenter(telaInicialBoundary.render());
		root.setTitle("SISTEMA DE ACADEMIA");
		root.setMaximized(true);
		root.setScene(scene);
		root.show();
	}

	public static Stage getRoot() {
		return root;
	}
}
