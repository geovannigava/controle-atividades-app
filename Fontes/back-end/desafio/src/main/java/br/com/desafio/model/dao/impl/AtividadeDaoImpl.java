package br.com.desafio.model.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;

import org.apache.log4j.Logger;

import br.com.desafio.model.Atividade;
import br.com.desafio.model.dao.IAtividadeDao;
import br.com.desafio.util.Transacional;


public class AtividadeDaoImpl implements IAtividadeDao, Serializable {

    @Inject
    private EntityManager em;
    
    private static Logger log = Logger.getLogger(AtividadeDaoImpl.class);
	
	/**
	 * Busca Atividade no banco de dados por id
	 * 
	 * @param idAtividade
	 * @return Atividade
	 */
	@Override
	public Atividade buscarAtividade(Long idAtividade) {
		return em.find(Atividade.class, idAtividade);
	}

	/**
	 * Busca todas as Atividade no banco de dados 
	 * 
	 * @return List<Atividade>
	 */
	@Override
	public List<Atividade> buscarTodasAtividade() {
        return em.createQuery("SELECT a FROM Atividade a ORDER BY a.idAtividade ASC").getResultList();
	}

	/**
	 * Busca as Atividade no banco de dados por Status da atividade
	 * 0 - Atividades Pendentes
	 * 1 - Atividades Concluídas
	 * 
	 * @param status
	 * @return List<Atividade>
	 */
	@Override
	public List<Atividade> buscarAtividadesPorStatus(Integer status) {
		return em.createQuery("SELECT a FROM Atividade a "
				+ " WHERE a.status = "+status+" ORDER BY a.idAtividade ASC").getResultList();
	}

	/**
	 * Grava atividade no banco de dados
	 * 
	 * 
	 * @param atividade
	 * @return Atividade
	 */
	@Override
	@Transacional
	public Atividade gravarAtividade(Atividade atividade) {
		em.persist(atividade);
		return atividade;
	}

	/**
	 * Exclui Atividade no banco de dados
	 * 
	 * @param atividade
	 * @return Boolean
	 */
	@Override
	@Transacional
	public Boolean removerAtividade(Atividade atividade) {
		try {
			Atividade atv = em.merge(atividade);
	        em.remove(atv);
			return true;
		} catch (IllegalArgumentException | TransactionRequiredException ex) {
			log.error(ex);
			return false;
		}
	}

	/**
	 * Atualiza a Atividade no banco de dados
	 * 
	 * @param atividade
	 * @return Boolean
	 */
	@Override
	@Transacional
	public Atividade atualizarAtividade(Atividade atividade) {
		return em.merge(atividade);
	}

}
