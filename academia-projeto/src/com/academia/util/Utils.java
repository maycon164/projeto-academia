package com.academia.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import com.academia.entities.Endereco;
import com.academia.exception.CepNotFound;
import com.google.gson.Gson;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class Utils {

	public static Endereco pegarCep(String cep) throws CepNotFound, IOException {

		if (cep.length() == 8) {
			String url = "http://viacep.com.br/ws/" + cep + "/json";

			URL urlConn = new URL(url);

			HttpURLConnection httpConn = (HttpURLConnection) urlConn.openConnection();

			if (httpConn.getResponseCode() != 200) {
				throw new CepNotFound("CEP " + cep + " NÃO ENCONTRADO");
			}

			BufferedReader resposta = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));

			String jsonString = Utils.jsonToString(resposta);

			Gson gson = new Gson();
			Endereco endereco = gson.fromJson(jsonString, Endereco.class);

			if (endereco.getCep() == null || endereco.getBairro() == null || endereco.getLogradouro() == null) {

				throw new CepNotFound("CEP " + cep + " NÃO ENCONTRADO");

			}

			return endereco;

		}

		return null;

	}

	private static String jsonToString(BufferedReader br) {
		String linha = "";
		StringBuilder json = new StringBuilder();

		try {
			while ((linha = br.readLine()) != null) {
				json.append(linha);
			}
			return json.toString();

		} catch (IOException e) {
			System.out.println("PASSOU AQUI");
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static Date somarData(Date dataInicio, Integer dias) {
		Date dataFim;

		Calendar cal = Calendar.getInstance();
		cal.setTime(dataInicio);
		cal.add(Calendar.DATE, dias);
		dataFim = cal.getTime();

		return dataFim;
	}


	public static Optional<ButtonType> showConfirmation(String title, String text) {

		Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);

		ButtonType btnSim = new ButtonType("Sim");
		ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

		dialogoExe.setTitle(title);
		dialogoExe.setHeaderText(text);
		dialogoExe.getButtonTypes().setAll(btnSim, btnCancelar);

		return dialogoExe.showAndWait();

	}

	public static void showAlert(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
}
