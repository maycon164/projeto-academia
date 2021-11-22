package com.academia.boundary;

import com.academia.control.GeralControl;
import com.academia.factory.ControllerMediator;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TelaInicialBoundary {

	// CONTROL
	private GeralControl control = ControllerMediator.getGeralControl();

	// GRIDGERAL
	private GridPane gridGeral = new GridPane();

	// LABEL
	private Label lblGeral = new Label("DADOS GERAIS");
	private Label lblQtdPlanos = new Label("QUANTIDADE DE PLANOS:");
	private Label lblQtdAlunos = new Label("QUANTIDADE DE ALUNOS:");
	private Label lblQtdInstrutores = new Label("QUANTIDADE DE INSTRUTORES:");

	private Label qtdPlanos = new Label("");
	private Label qtdAlunos = new Label("");
	private Label qtdInstrutores = new Label("");

	private Label lblPlanoMaisAssinado = new Label("PLANO MAIS ASSINADO: ");
	private Label lblPlanoComMaisInstrutores = new Label("PLANO COM MAIS INSTRUTORES: ");
	private Label planoAssinado = new Label("");
	private Label planoInstrutor = new Label("");

	private Label pendencias = new Label("");

	public TelaInicialBoundary() {
		iniciarTela();
	}

	public void atualizarTela() {
		control.atualizarDados();
	}

	private void iniciarTela() {
		iniciarControl();
		iniciarGrid();
		iniciarCss();
		control.atualizarDados();
	}

	public GridPane render() {
		return gridGeral;
	}

	private void iniciarGrid() {
		gridGeral.addRow(1, lblGeral);
		gridGeral.addRow(2, lblQtdAlunos, lblQtdPlanos, lblQtdInstrutores);
		gridGeral.addRow(3, qtdAlunos, qtdPlanos, qtdInstrutores);

		gridGeral.addRow(4, lblPlanoMaisAssinado);
		gridGeral.addRow(5, planoAssinado);
		gridGeral.addRow(6, lblPlanoComMaisInstrutores);
		gridGeral.addRow(7, planoInstrutor);
		gridGeral.addRow(8, pendencias);

		GridPane.setColumnSpan(lblGeral, 3);
		GridPane.setColumnSpan(lblPlanoMaisAssinado, 3);
		GridPane.setColumnSpan(planoAssinado, 3);
		GridPane.setColumnSpan(lblPlanoComMaisInstrutores, 3);
		GridPane.setColumnSpan(planoInstrutor, 3);
		GridPane.setColumnSpan(pendencias, 3);

	}

	private void iniciarControl() {

		Bindings.bindBidirectional(qtdAlunos.textProperty(), control.qtdAlunos);
		Bindings.bindBidirectional(qtdInstrutores.textProperty(), control.qtdInstrutores);
		Bindings.bindBidirectional(qtdPlanos.textProperty(), control.qtdPlanos);

		Bindings.bindBidirectional(planoAssinado.textProperty(), control.planoAssinado);
		Bindings.bindBidirectional(planoInstrutor.textProperty(), control.planoInstrutor);

		Bindings.bindBidirectional(pendencias.textProperty(), control.pendencias);
	}

	private void iniciarCss() {
		gridGeral.getStyleClass().addAll("grid", "gridMargin");
		lblGeral.getStyleClass().addAll("textCentralizado", "textInfoGeral");
		lblPlanoComMaisInstrutores.getStyleClass().addAll("textCentralizado", "textInfoGeral");
		lblPlanoMaisAssinado.getStyleClass().addAll("textCentralizado", "textInfoGeral");

		qtdAlunos.getStyleClass().add("info");
		qtdInstrutores.getStyleClass().add("info");
		qtdPlanos.getStyleClass().add("info");

		planoAssinado.getStyleClass().add("info");
		planoInstrutor.getStyleClass().add("info");

		pendencias.getStyleClass().add("info-pendencias");
	}
}
