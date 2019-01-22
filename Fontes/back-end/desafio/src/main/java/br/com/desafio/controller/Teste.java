package br.com.desafio.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class Models
 */
@WebServlet("/models/*")
public class Teste extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	class Model{
		public String name;
		public String urn;
		public String id;
		
		public Model(String _id, String _name, String _urn) {
			name = _name;
			urn = _urn;
			id = _id;
		}
	}
	
	private Gson _gson = null;
	
	private HashMap<String, Model> _modelsDb = new HashMap<String, Model>();
	
	//Adds some default models to our db
	public Teste() {
		super();
		
		_gson = new Gson();
		
		String id1 = UUID.randomUUID().toString();
		String id2 = UUID.randomUUID().toString();
		String id3 = UUID.randomUUID().toString();
		
		_modelsDb.put(id1, 
			new Model(
			 id1,		
			"Engine", 
			"... base 64 URN ..."));
		
		_modelsDb.put(id2, 
			new Model(
			id2,
			"Hairdryer", 
			"... base 64 URN ..."));
		
		_modelsDb.put(id3, 
			new Model(
			id3,		
			"Plane Engine", 
			"... base 64 URN ..."));
	}
	
	//a utility method to send object
	//as JSON response
	private void sendAsJson( HttpServletResponse response, Object obj) throws IOException {
		response.setContentType("application/json");
		String res = _gson.toJson(obj);
		PrintWriter out = response.getWriter();
		out.print(res);
		out.flush();
	}
	
	// Get models
	// GET/JavaViewer/models/
	// GET/JavaViewer/models/id 
	protected void doGet(
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {

		String pathInfo = request.getPathInfo();

		if(pathInfo == null || pathInfo.equals("/")){

			Collection<Model> models = _modelsDb.values();
			
			sendAsJson(response, models);
			return;
		}

		String[] splits = pathInfo.split("/");
		
		if(splits.length != 2) {
			
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		String modelId = splits[1];
		
		if(!_modelsDb.containsKey(modelId)) {
			
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		sendAsJson(response, _modelsDb.get(modelId));
		return;
	}

	// Adds new model in DB
	// POST/JavaViewer/models
	protected void doPost(
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {

		String pathInfo = request.getPathInfo();

		if(pathInfo == null || pathInfo.equals("/")){

			StringBuilder buffer = new StringBuilder();
		    BufferedReader reader = request.getReader();
		    String line;
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		    }
		    
		    String payload = buffer.toString();
		    
		    Model model = _gson.fromJson(payload, Model.class);
		    
		    model.id = UUID.randomUUID().toString();
		    
		    _modelsDb.put(model.id, model);
		    
		    sendAsJson(response, model);
		}
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}

	// Updates a model in DB
	// PUT/JavaViewer/models/id
	protected void doPut(
			HttpServletRequest request,
			HttpServletResponse response) 
					throws IOException, ServletException {

		String pathInfo = request.getPathInfo();

		if(pathInfo == null || pathInfo.equals("/")){

			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		String[] splits = pathInfo.split("/");
		
		if(splits.length != 2) {
			
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		String modelId = splits[1];
		
		if(!_modelsDb.containsKey(modelId)) {
			
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	    }
	    
	    String payload = buffer.toString();
	    
	    Model model = _gson.fromJson(payload, Model.class);
	    
	    model.id = modelId;
	     
	    _modelsDb.put(modelId, model);
	    
	    sendAsJson(response, model);
	}

	// Deletes a model in DB
	// DELETE/JavaViewer/models/id
	protected void doDelete(
			HttpServletRequest request,
			HttpServletResponse response) 
					throws IOException, ServletException {

		String pathInfo = request.getPathInfo();

		if(pathInfo == null || pathInfo.equals("/")){

			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		String[] splits = pathInfo.split("/");
		
		if(splits.length != 2) {
			
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		String modelId = splits[1];
		
		if(!_modelsDb.containsKey(modelId)) {
			
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		Model model = _modelsDb.get(modelId);
		
		_modelsDb.remove(modelId);
		
		sendAsJson(response, model);
		return;
	}
}