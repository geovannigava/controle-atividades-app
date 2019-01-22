package br.com.desafio.model.service;

import java.util.List;

import br.com.desafio.model.Atividade;
import br.com.desafio.model.dao.AtividadeDao;
import br.com.desafio.model.enums.StatusAtividadeEnum;
import br.com.desafio.model.enums.TipoAtividadeEnum;

public class AtividadeService {

	private AtividadeDao atividadeDao = new AtividadeDao();
	
	/**
	 * Busca Atividade no banco de dados por id
	 * 
	 * @param idAtividade
	 * @return Atividade
	 */
	public Atividade buscarAtividade(Long idAtividade) {
        return this.atividadeDao.buscarAtividade(idAtividade);
	}
	
	/**
	 * Busca todas as Atividade no banco de dados 
	 * 
	 * @return List<Atividade>
	 */
	public List<Atividade> buscarTodasAtividades() {
        return this.atividadeDao.buscarTodasAtividade();
	}
	
	/**
	 * Busca as Atividade no banco de dados por Status da atividade
	 * 0 - Atividades Pendentes
	 * 1 - Atividades Concluídas
	 * 
	 * @param status
	 * @return List<Atividade>
	 */
	public List<Atividade> buscarAtividadesPorStatus(Integer status) {
        return this.atividadeDao.buscarAtividadesPorStatus(status);
	}
	
	/**
	 * Grava atividade no banco de dados
	 * 
	 * 
	 * @param atividade
	 * @return Atividade
	 */
	public Atividade gravarAtividade(Atividade atividade) {
		return this.atividadeDao.gravarAtividade(atividade);
	}
	
	/**
	 * Exclui Atividade no banco de dados, fazendo as validaçoes necessárias
	 * 
	 * @param atividade
	 * @return Boolean
	 */
	public Boolean removerAtividade(Atividade atividade) {
		//Verifica se a atividade é do tipo manutenção urgente antes de excuir.
		if(atividade.getTipoAtividade() == TipoAtividadeEnum.MANUTENCAO_URGENTE) {
			return false;
		} else {
			return this.atividadeDao.removerAtividade(atividade);
		}
	}
	
	/**
	 * Atualiza a Atividade no banco de dados, fazendo as validaçoes necessárias, verificando se a atividade é do tipo
	 * Atendimento ou Manutençao urgente, e se a descrição possui pelo menos 50 caracteres, antes de alterar o status 
	 * para concluída.
	 * 
	 * @param atividade
	 * @return Boolean
	 */
	public Boolean atualizarAtividade(Atividade atividade) {
		if (atividade.getTipoAtividade() == TipoAtividadeEnum.ATENDIMENTO
				|| atividade.getTipoAtividade() == TipoAtividadeEnum.MANUTENCAO_URGENTE) {
			if (atividade.getStatus() == StatusAtividadeEnum.CONCLUIDA) {
				if (atividade.getDescricao().length() < 50) {
					System.out.println("sssss");
					return false;
				}
			}
		}
        return this.atividadeDao.atualizarAtividade(atividade);
	}
	
	
}
