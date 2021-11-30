package com.academia.control;

import java.util.List;

import com.academia.dao.InstrutorDao;
import com.academia.dto.InstrutorDTO;
import com.academia.entities.Instrutor;
import com.academia.entities.Plano;
import com.academia.exception.EmptyFieldException;
import com.academia.factory.DaoFactory;
import com.academia.util.Utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class InstrutorControl {

	// STRING PROPERTYS
	public StringProperty cpfProps = new SimpleStringProperty("");
	public StringProperty nomeProps = new SimpleStringProperty("");
	public StringProperty sexoProps = new SimpleStringProperty("");
	public StringProperty emailProps = new SimpleStringProperty("");

	private InstrutorDao instrutorConn = DaoFactory.getInstrutorDao();

	// LISTAS
	private ObservableList<Instrutor> instrutores = FXCollections.observableArrayList(instrutorConn.findAll());
	private FilteredList<Instrutor> filteredInstrutores = new FilteredList<>(instrutores, i -> true);

	// PLANOS SELECIONADOS
	public List<Plano> planosBoundary = null;

	public List<InstrutorDTO> getInstrutoresByPlano(int idPlano) {

		return instrutorConn.findAllByPlano(idPlano);

	}

	public boolean cadastrar() {

		try {
			validarCampos();

			Instrutor instrutor = instrutorFromBoundary();

			if (instrutorConn.insert(instrutor)) {

				System.out.println("CADASTRO DE INSTRUTOR COM SUCESSO " + instrutor);
				instrutores.add(instrutor);
				atualizarLista();
				limparCampos();

				return true;
			}

			return false;
		} catch (EmptyFieldException e) {
			return false;
		}
	}

	public boolean alterar(Instrutor i) {
		try {
			validarCampos();
			Instrutor aux = instrutorFromBoundary();

			i.setNome(aux.getNome());
			i.setSexo(aux.getSexo());
			i.setEmail(aux.getEmail());
			i.getPlanos().clear();

			for (Plano plano : aux.getPlanos()) {
				i.getPlanos().add(plano);
			}
			atualizarLista();
			return (instrutorConn.update(i));

		} catch (EmptyFieldException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ObservableList<Instrutor> getInstrutores() {
		return filteredInstrutores;
	}

	private void atualizarLista() {
		filteredInstrutores.setPredicate(i -> false);
		filteredInstrutores.setPredicate(i -> true);
	}

	public void limparCampos() {

		cpfProps.set("");
		nomeProps.set("");
		sexoProps.set("");
		emailProps.set("");

		if (planosBoundary != null)
			planosBoundary.clear();

	}

	private void validarCampos() throws EmptyFieldException {

		Utils.verificarCampos(cpfProps.get(), nomeProps.get(), sexoProps.get(), emailProps.get());

	}

	private Instrutor instrutorFromBoundary() {
		Instrutor instrutor = new Instrutor();

		instrutor.setCpf(cpfProps.get());
		instrutor.setNome(nomeProps.get());
		instrutor.setEmail(emailProps.get());
		instrutor.setSexo(sexoProps.get().charAt(0));

		if (planosBoundary != null) {
			for (Plano plano : planosBoundary) {
				instrutor.getPlanos().add(plano);
			}
		}

		return instrutor;
	}

	public void setInstrutor(Instrutor i) {

		cpfProps.set(i.getCpf());
		nomeProps.set(i.getNome());
		emailProps.set(i.getEmail());
		sexoProps.set((i.getSexo() == 'M') ? "Masculino" : "Feminino");

	}

	public boolean excluir(Instrutor i) {

		if (instrutorConn.deleteByCpf(i.getCpf())) {
			instrutores.remove(i);
			atualizarLista();
			return true;
		}

		return false;

	}
}
