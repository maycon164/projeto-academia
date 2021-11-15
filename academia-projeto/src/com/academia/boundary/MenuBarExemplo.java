package com.academia.boundary;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuBarExemplo extends Application {

	public AlunoCadastroBoundary alunoCadastroBoundary = new AlunoCadastroBoundary();
	public GerenciarAlunosBoundary gerenciarAlunosBoundary = new GerenciarAlunosBoundary();

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

		MenuItem itemCadastrarAluno = new MenuItem("CADASTRAR ALUNO");

		itemCadastrarAluno.setOnAction(event -> {
			alunoCadastroBoundary.iniciarCadastrar();
			pane.setCenter(alunoCadastroBoundary.render());
		});

		menuCadastros.getItems().add(itemCadastrarAluno);

		MenuItem itemGerenciarAlunos = new MenuItem("GERENCIAR ALUNOS");
		itemGerenciarAlunos.setOnAction(event -> {
			System.out.println("BOTAO CLICK");
			pane.setCenter(gerenciarAlunosBoundary.render());
		});

		menuGerenciar.getItems().add(itemGerenciarAlunos);

		menuBar.getMenus().addAll(menuCadastros, menuGerenciar);

		Scene scene = new Scene(pane, 500, 500);
		scene.getStylesheets().add("style.css");

		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
