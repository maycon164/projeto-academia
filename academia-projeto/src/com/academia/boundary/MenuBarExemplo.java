package com.academia.boundary;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuBarExemplo extends Application {

	public AlunoCadastroBoundary alunoCadastroBoundary = new AlunoCadastroBoundary();
	public GerenciarAlunosBoundary gerenciarAlunosBoundary = new GerenciarAlunosBoundary();
	private GerenciarPlanoBoundary gerenciarPlanoBoundary = new GerenciarPlanoBoundary();

	public static void main(String[] args) {
		Application.launch(MenuBarExemplo.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
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
			pane.setCenter(gerenciarPlanoBoundary.render());
		});

		menuCadastros.getItems().addAll(itemCadastrarAluno, itemCadastrarPlano);

		MenuItem itemGerenciarAlunos = new MenuItem("Gerenciar Alunos");

		itemGerenciarAlunos.setOnAction(event -> {
			System.out.println("BOTAO CLICK");
			pane.setCenter(gerenciarAlunosBoundary.render());
		});

		menuGerenciar.getItems().add(itemGerenciarAlunos);

		MenuItem itemMenuSair = new MenuItem("Sair");
		itemMenuSair.setOnAction(event -> {
			Platform.exit();
			System.exit(0);
		});

		menuOutros.getItems().add(itemMenuSair);

		menuBar.getMenus().addAll(menuCadastros, menuGerenciar, menuOutros);

		Scene scene = new Scene(pane, 500, 500);
		scene.getStylesheets().add("style.css");

		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
