package br.com.desafio.model.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.desafio.model.Atividade;
import br.com.desafio.model.dao.IAtividadeDao;
import br.com.desafio.model.enums.StatusAtividadeEnum;
import br.com.desafio.model.enums.TipoAtividadeEnum;
import br.com.desafio.model.service.AtividadeService;

@Stateless
@Named
public class AtividadeServiceImpl implements AtividadeService {

	@Inject
	IAtividadeDao atividadeDao;
	
	@Override
	public Atividade buscarAtividade(Long idAtividade) {
		return this.atividadeDao.buscarAtividade(idAtividade);
	}

	@Override
	public List<Atividade> buscarTodasAtividades() {
		return this.atividadeDao.buscarTodasAtividade();
	}

	@Override
	public List<Atividade> buscarAtividadesPorStatus(Integer status) {
		return this.atividadeDao.buscarAtividadesPorStatus(status);
	}

	@Override
	public Atividade gravarAtividade(Atividade atividade) {
		return this.atividadeDao.gravarAtividade(atividade);
	}

	@Override
	public Boolean removerAtividade(Atividade atividade) {
		//Verifica se a atividade é do tipo manutenção urgente antes de excuir.
		if(atividade.getTipoAtividade() == TipoAtividadeEnum.MANUTENCAO_URGENTE) {
			return false;
		} else {
			return this.atividadeDao.removerAtividade(atividade);
		}
	}

	@Override
	public Boolean atualizarAtividade(Atividade atividade) {
		Boolean retorno = true;
		if (atividade.getTipoAtividade() == TipoAtividadeEnum.ATENDIMENTO
				|| atividade.getTipoAtividade() == TipoAtividadeEnum.MANUTENCAO_URGENTE) {
			if (atividade.getStatus() == StatusAtividadeEnum.CONCLUIDA) {
				if (atividade.getDescricao().length() < 50) {
					retorno = false;
					return retorno;
				}
			}
		} else {
			Atividade atividadeAtualizada = this.atividadeDao.atualizarAtividade(atividade);
			retorno = true;
		}
		return retorno;
	}

	
}
