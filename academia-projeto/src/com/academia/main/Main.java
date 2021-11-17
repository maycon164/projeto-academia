package com.academia.main;

import com.academia.util.UtilsGui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new Pane();
		Scene scene = new Scene(pane, 500, 500);

		// Utils.showAlert("TESTE", "TESTANDO HEADER", "TESTANDO CONTEUDO",
		// AlertType.CONFIRMATION);

		UtilsGui.showConfirmation("DESEJA MESMO CONFIRMAR", "RONALDO").ifPresent(b -> {

			System.out.println(b.getText());

		});

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		Application.launch(Main.class, args);
	}
}
