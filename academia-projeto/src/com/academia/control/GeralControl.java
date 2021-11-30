package com.academia.control;

import com.academia.dao.GeralConexao;
import com.academia.dto.InfoDTO;
import com.academia.factory.DaoFactory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GeralControl {

	private GeralConexao conexao = DaoFactory.getGeralConexao();

	public StringProperty qtdAlunos = new SimpleStringProperty("");
	public StringProperty qtdPlanos = new SimpleStringProperty("");
	public StringProperty qtdInstrutores = new SimpleStringProperty("");

	public StringProperty planoAssinado = new SimpleStringProperty("");
	public StringProperty planoInstrutor = new SimpleStringProperty("");

	public StringProperty pendencias = new SimpleStringProperty("");

	public void atualizarDados() {
		int nums[] = conexao.infoGeral();

		qtdAlunos.set(String.valueOf(nums[0]));
		qtdInstrutores.set(String.valueOf(nums[1]));
		qtdPlanos.set(String.valueOf(nums[2]));

		InfoDTO info = conexao.planoMaisAssinado();
		
		if(info != null) {
			planoAssinado.set(info.getNomePlano() + ": " + info.getQtd() + " Alunos");
		}

		info = conexao.planoComMaisInstrutores();
		if(info != null) {
			planoInstrutor.set(info.getNomePlano() + ": " + info.getQtd() + " Instrutores");	
		}
		
		pendencias.set("Pendencias: " + conexao.infoPendencias() + " pagamentos em aberto");
	}

}
