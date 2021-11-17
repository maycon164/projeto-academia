package com.academia.control;

import java.util.List;

import com.academia.dao.InstrutorDao;
import com.academia.dto.InstrutorDTO;
import com.academia.entities.Instrutor;
import com.academia.entities.Plano;
import com.academia.exception.EmptyFieldException;
import com.academia.factory.DaoFactory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InstrutorControl {

	// STRING PROPERTYS
	public StringProperty cpfProps = new SimpleStringProperty("");
	public StringProperty nomeProps = new SimpleStringProperty("");
	public StringProperty sexoProps = new SimpleStringProperty("");
	public StringProperty emailProps = new SimpleStringProperty("");

	private InstrutorDao instrutorConn = DaoFactory.getInstrutorDao();

	public List<InstrutorDTO> getInstrutoresByPlano(int idPlano) {

		return instrutorConn.findAllByPlano(idPlano);

	}

	public boolean cadastrar(List<Plano> planos) {

		try {
			validarCampos();

			Instrutor instrutor = instrutorFromBoundary();
			for (Plano plano : planos) {
				instrutor.getPlanos().add(plano);
			}

			boolean insercao = instrutorConn.insert(instrutor);

			if (insercao) {
				System.out.println("CADASTRO DE INSTRUTOR COM SUCESSO " + instrutor);
				return true;
			}

			return false;
		} catch (EmptyFieldException e) {
			return false;
		}
	}

	public void limparCampos() {

		cpfProps.set("");
		nomeProps.set("");
		sexoProps.set("");
		emailProps.set("");

	}

	private void validarCampos() throws EmptyFieldException {

		if (cpfProps.get().isEmpty() || nomeProps.get().isEmpty() || sexoProps.get().isEmpty()
				|| emailProps.get().isEmpty()) {

			throw new EmptyFieldException("Alguns Campos Vazios!!!!");

		}

	}

	private Instrutor instrutorFromBoundary() {
		Instrutor instrutor = new Instrutor();

		instrutor.setCpf(cpfProps.get());
		instrutor.setNome(nomeProps.get());
		instrutor.setEmail(emailProps.get());
		instrutor.setSexo(sexoProps.get().charAt(0));

		return instrutor;
	}
}
