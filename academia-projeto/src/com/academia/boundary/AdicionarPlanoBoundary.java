package com.academia.boundary;

import com.academia.control.PlanoControl;
import com.academia.entities.Plano;
import com.academia.factory.ControllerMediator;
import com.academia.util.EventListener;
import com.academia.util.Listener;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class AdicionarPlanoBoundary implements EventListener {

	// PANES
	private VBox vbox = new VBox();
	private FlowPane flowPane = new FlowPane();

	// Label
	private Label lblAdicionarPlano = new Label("ESCOLHA UM PLANO");

	// Buttons
	private Button btnAdicionar = new Button("Adicionar");
	private Button btnCancelar = new Button("Cancelar");

	// listView
	private ListView<Plano> lvPlanos = new ListView<>();

	// CONTROLER
	private PlanoControl planoControl = ControllerMediator.getPlanoControl();

	public AdicionarPlanoBoundary() {
		iniciarTela();
	}

	public VBox render() {
		planoControl.atualizarListaPlanos();
		return vbox;
	}

	private void iniciarTela() {
		iniciarVbox();
		iniciarCss();
		iniciarListViewPlanos();
		iniciarEventos();

	}

	private void iniciarEventos() {

		btnAdicionar.setOnAction(event -> {

			notifyListener();

		});

	}

	private void iniciarVbox() {

		flowPane.getChildren().addAll(btnAdicionar, btnCancelar);
		vbox.getChildren().addAll(lblAdicionarPlano, lvPlanos, flowPane);
		VBox.setMargin(lvPlanos, new Insets(20D));
		VBox.setMargin(flowPane, new Insets(0d, 0d, 40d, 20d));

	}

	private void iniciarCss() {

		lblAdicionarPlano.getStyleClass().add("textCentralizado");

	}

	private void iniciarListViewPlanos() {

		lvPlanos.setItems(planoControl.getPlanos());

	}

	@Override
	public void addListener(Listener obj) {
		listeners.add(obj);
	}

	@Override
	public void notifyListener() {
		Plano plano = lvPlanos.getSelectionModel().getSelectedItem();

		if (plano != null) {
			for (Listener list : listeners) {
				list.update(plano);
			}
		}

	}

}
