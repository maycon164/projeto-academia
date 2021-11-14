package com.academia.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.academia.entities.Endereco;
import com.academia.exception.CepNotFound;
import com.google.gson.Gson;

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

}
