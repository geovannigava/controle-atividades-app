package br.com.desafio.model.service;

import java.util.List;

import br.com.desafio.model.Atividade;

public interface AtividadeService {
	
	public Atividade buscarAtividade(Long idAtividade);
	public List<Atividade> buscarTodasAtividades();
	public List<Atividade> buscarAtividadesPorStatus(Integer status);
	public Atividade gravarAtividade(Atividade atividade);
	public Boolean removerAtividade(Atividade atividade);
	public Boolean atualizarAtividade(Atividade atividade);

}
