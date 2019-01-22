package br.com.desafio.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.desafio.model.Atividade;
import br.com.desafio.model.service.AtividadeService;
import br.com.desafio.util.JsonConversor;

@WebServlet("/atividade/*")
public class AtividadeController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private AtividadeService atividadeService = new AtividadeService();
	
	
	/* Requisições do tipo GET
	 * Endpoints das requisiçoes:
	 * http://{host}/desafio/atividade - Buscar todas as tividades
	 * http://{host}/desafio/atividade/{id} - Buscar atividade por id
	 * http://{host}/desafio/atividade?statusAtividade={status} - Buscar atividade por status
	 * status = 0 - Atividades pendentes
	 * status = 1 - Atividades concluídas
	 * 
	 * Retorno no formato Json
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		String parametro = request.getParameter("statusAtividade");
		if (parametro == null) {
			if(pathInfo == null || pathInfo.equals("/")){
				List<Atividade> atividades = atividadeService.buscarTodasAtividades();
				JsonConversor.objetoParaJson(response, atividades);
				return;
			}
			Long idAtividade = this.verificarPath(pathInfo.split("/"));
			if (idAtividade == -1L) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			} else {
				Atividade atividade = this.atividadeService.buscarAtividade(idAtividade);
				if(atividade == null) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					return;
				} else {
					JsonConversor.objetoParaJson(response, atividade);
					return;
				}
			}
		} else {
			//Verifica se o parametro é válido
			try {
				Integer status = Integer.parseInt(parametro);
				if (status == 0 || status == 1) {
					List<Atividade> atividades = atividadeService.buscarAtividadesPorStatus(Integer.parseInt(parametro));
					JsonConversor.objetoParaJson(response, atividades);
					return;
				} else {
					JsonConversor.objetoParaJson(response, "Parametro Inválido");
					return;
				}
			} catch(Exception ex) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}
	}
	
	/* Requisições do tipo POST
	 * Endpoint da requisição:
	 * http://{host}/desafio/atividade
	 * 
	 * Corpo da requisiçao e retorno no formato Json
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/")){
		    Atividade atividade = JsonConversor.jsonParaObjeto(request);
		    Atividade atividadeSalva = atividadeService.gravarAtividade(atividade);
		    JsonConversor.objetoParaJson(response, atividadeSalva);
		}
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}
	
	/* Requisições do tipo PUT
	 * Endpoint da requisição:
	 * http://{host}/desafio/atividade/{id}
	 * 
	 * Corpo da requisiçao formato Json e retorno boolean
	 */
	protected void doPut( HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String pathInfo = request.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/")){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		Long idAtividade = this.verificarPath(pathInfo.split("/"));
		if (idAtividade == -1L) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		} else {
			Atividade atividade = this.atividadeService.buscarAtividade(idAtividade);
			if(atividade == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			} else {
				Atividade atividadeNova = JsonConversor.jsonParaObjeto(request);
			    atividadeNova.setIdAtividade(atividade.getIdAtividade());
			    if(atividadeService.atualizarAtividade(atividadeNova)) {
			    	JsonConversor.objetoParaJson(response, true);
			    	return;
			    } else {
			    	JsonConversor.objetoParaJson(response, false);
			    	return;
			    }
			    
				
			}
		}	    
	}

	/* Requisições do tipo DELETE
	 * Endpoint da requisição:
	 * http://{host}/desafio/atividade/{id}
	 * 
	 * Retorno boolean
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String pathInfo = request.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/")){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		Long idAtividade = this.verificarPath(pathInfo.split("/"));
		if (idAtividade == -1L) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		} else {
			Atividade atividade = this.atividadeService.buscarAtividade(idAtividade);
			if(atividade == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			} else {
				if(atividadeService.removerAtividade(atividade)) {
					JsonConversor.objetoParaJson(response, true);
					return;
				} else {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
			}
		}
	}
	
	
	/**
	 * Verifica se o path da requisição é válido, e retorna o ID da atividade, ou -1 caso o ID nao seja válido
	 * 
	 * @param path
	 * @return Long
	 */
	private Long verificarPath(String[] path) {
		if(path.length != 2) {
			return -1L;
		} else {
			try {
				Long idAtividade = Long.parseLong(path[1]);
				return idAtividade;
			} catch (Exception ex) {
				return -1L;
			}
		}
	}

}
