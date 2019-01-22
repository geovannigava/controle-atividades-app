package br.com.desafio.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.com.desafio.model.Atividade;
import br.com.desafio.util.JPAUtil;

public class AtividadeDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Busca Atividade no banco de dados por id
	 * 
	 * @param idAtividade
	 * @return Atividade
	 */
	public Atividade buscarAtividade(Long idAtividade) {
		EntityManager em = new JPAUtil().getEntityManager();
	    try {
	    	em.getTransaction().begin();
		    Atividade atividade = em.find(Atividade.class, idAtividade);
		    em.getTransaction().commit();
		    return atividade;
	    } catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	/**
	 * Busca todas as Atividade no banco de dados 
	 * 
	 * @return List<Atividade>
	 */
	public List<Atividade> buscarTodasAtividade() {
		EntityManager em = new JPAUtil().getEntityManager();
	    try {
	    	List<Atividade> atividades = em.createQuery("SELECT a FROM Atividade a ORDER BY a.idAtividade ASC").getResultList();
		    return atividades;
	    } catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
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
		EntityManager em = new JPAUtil().getEntityManager();
	    try {
	    	List<Atividade> atividades = em.createQuery("SELECT a FROM Atividade a "
					+ " WHERE a.status = "+status+" ORDER BY a.idAtividade ASC").getResultList();		    
	    	return atividades;
	    } catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	/**
	 * Grava atividade no banco de dados
	 * 
	 * 
	 * @param atividade
	 * @return Atividade
	 */
	public Atividade gravarAtividade(Atividade atividade) {
		EntityManager em = new JPAUtil().getEntityManager();
		 try {
			 em.getTransaction().begin();
			 em.persist(atividade);
			 em.getTransaction().commit();	    
		    return atividade;
		    } catch (Exception e) {
				return null;
			} finally {
				em.close();
			}
	}
	
	/**
	 * Exclui Atividade no banco de dados
	 * 
	 * @param atividade
	 * @return Boolean
	 */
	@Transactional
	public Boolean removerAtividade(Atividade atividade) {
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(em.getReference(Atividade.class, atividade.getIdAtividade()));
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			em.close();
		}
	}
	
	/**
	 * Atualiza a Atividade no banco de dados
	 * 
	 * @param atividade
	 * @return Boolean
	 */
	@Transactional
	public Boolean atualizarAtividade(Atividade atividade) {
		EntityManager em = new JPAUtil().getEntityManager();
		try {
			em.getTransaction().begin();
			Query query =  em.createQuery(" UPDATE Atividade a SET a.titulo = :titulo,"
					+ " a.descricao = :descricao, a.tipoAtividade = :tipoAtividade, a.status = :status"
					+ "  WHERE a.idAtividade = :id ")
					.setParameter("id", atividade.getIdAtividade())
					.setParameter("titulo", atividade.getTitulo())
					.setParameter("descricao", atividade.getDescricao())
					.setParameter("tipoAtividade", atividade.getTipoAtividade())
					.setParameter("status", atividade.getStatus());
			query.executeUpdate();
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			em.close();
		}
		
	}
	

}
