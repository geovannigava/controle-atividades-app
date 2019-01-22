package br.com.desafio.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.com.desafio.model.Atividade;

public class JsonConversor {
	
	private static Gson _gson = new Gson();
	
	
	/**
	 * Converte Objeto para enviar como resposta JSON
	 * 
	 * @param response
	 * @param obj
	 * @throws IOException
	 */
	public static void objetoParaJson(HttpServletResponse response, Object obj) throws IOException {
		response.setContentType("application/json");
		String res = _gson.toJson(obj);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.flush();
	}
	
	
	/**
	 * Converte Json para um objeto do tipo Atividade
	 * 
	 * @param request
	 * @throws IOException
	 */
	public static Atividade jsonParaObjeto(HttpServletRequest request) throws IOException {
		StringBuilder buffer = new StringBuilder();
		request.setCharacterEncoding("UTF-8");
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
		    buffer.append(line);
		}
		String payload = buffer.toString();
		Gson _gson = new Gson();
		Atividade atividadeNova = _gson.fromJson(payload, Atividade.class);
		return atividadeNova;
	}

}
