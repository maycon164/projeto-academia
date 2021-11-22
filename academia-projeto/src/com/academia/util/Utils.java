package com.academia.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.academia.entities.Endereco;
import com.academia.exception.CepNotFound;
import com.academia.exception.EmptyFieldException;
import com.google.gson.Gson;

public class Utils {
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public static void verificarCampos(String... campos) throws EmptyFieldException {

		for (String campo : campos) {

			if (campo.length() == 0) {

				throw new EmptyFieldException("Preencha todos os campos");

			}
		}
	}

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

	public static String formatarData(Date data) {
		return sdf.format(data);
	}

	public static Date converterParaData(String data) throws ParseException {
		return sdf.parse(data);
	}
}
