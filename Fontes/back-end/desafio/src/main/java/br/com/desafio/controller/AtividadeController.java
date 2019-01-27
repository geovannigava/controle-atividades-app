package br.com.desafio.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import br.com.desafio.model.Atividade;
import br.com.desafio.model.service.AtividadeService;

@Named
@Path("atividade")
@Produces("application/json; charset=UTF-8")
@Consumes("application/json; charset=UTF-8")
@RequestScoped
public class AtividadeController {
	
	@Inject
	private AtividadeService atividadeService;
	
	/* Requisições do tipo GET
	 * Endpoints das requisiçoes:
	 * http://{host}/desafio/atividade - Buscar todas as tividades
	 * http://{host}/desafio/atividade?statusAtividade={status} - Buscar atividade por status
	 * status = 0 - Atividades pendentes
	 * status = 1 - Atividades concluídas
	 */
    @GET
    public List<Atividade> buscarTodasAtividades(@QueryParam("statusAtividade") Integer status) {
    	List<Atividade> atividades = null;
    	if(status != null) {
    		atividades = atividadeService.buscarAtividadesPorStatus(status);
    	} else {
    		atividades = atividadeService.buscarTodasAtividades();
    	}
        return atividades;
    }
    
	/* Requisições do tipo GET
	 * Endpoints da requisição:
	 * http://{host}/desafio/atividade/{id} - Buscar atividade por id
	 * 
	 * Retorno no formato Json
	 */
    @GET
    @Path("/{codigo}")
    public Atividade buscarAtividade(@PathParam("codigo") Long codigo) {
        return atividadeService.buscarAtividade(codigo);
    }
    
	/* Requisições do tipo PUT
	 * Endpoint da requisição:
	 * http://{host}/desafio/atividade/{id}
	 * 
	 * Corpo da requisiçao formato Json e retorno boolean
	 */
    @PUT
    @Path("/{codigo}")
    public Boolean atualizarAtividade(Atividade atividade, @PathParam("codigo") Long codigo) {
        atividade.setIdAtividade(codigo);
    	return atividadeService.atualizarAtividade(atividade);
    }
    
	/* Requisições do tipo DELETE
	 * Endpoint da requisição:
	 * http://{host}/desafio/atividade/{id}
	 * 
	 * Retorno boolean
	 */
    @DELETE
    @Path("/{codigo}")
    public Boolean removerAtividade(@PathParam("codigo") Long codigo) {
    	Atividade atividade = atividadeService.buscarAtividade(codigo);
    	return atividadeService.removerAtividade(atividade);
    }
    
	/* Requisições do tipo POST
	 * Endpoint da requisição:
	 * http://{host}/desafio/atividade
	 * 
	 * Corpo da requisiçao e retorno no formato Json
	 */
    @POST
    public Response salvar(@Valid Atividade atividade) {
        try {
            return Response.ok(atividadeService.gravarAtividade(atividade)).build();
        } catch (ConstraintViolationException cvex) {
            final Set<String> erros = cvex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
            return Response.status(Response.Status.BAD_REQUEST).entity(erros).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Instabilidade no serviço").build();
        }
    }

}
