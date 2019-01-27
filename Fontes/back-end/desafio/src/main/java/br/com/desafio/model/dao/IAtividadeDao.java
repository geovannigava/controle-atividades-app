package br.com.desafio.model.dao;

import java.util.List;

import br.com.desafio.model.Atividade;

public interface IAtividadeDao {
	
	public Atividade buscarAtividade(Long idAtividade);
	public List<Atividade> buscarTodasAtividade();
	public List<Atividade> buscarAtividadesPorStatus(Integer status);
	public Atividade gravarAtividade(Atividade atividade);
	public Boolean removerAtividade(Atividade atividade);
	public Atividade atualizarAtividade(Atividade atividade);
}
