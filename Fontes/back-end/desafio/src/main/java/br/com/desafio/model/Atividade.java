package br.com.desafio.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.desafio.model.enums.StatusAtividadeEnum;
import br.com.desafio.model.enums.TipoAtividadeEnum;

@XmlRootElement(name="atividade")
@Entity
@Table(name = "atividade", schema = "atividade")
public class Atividade implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atividade", updatable=false)
	private Long idAtividade;
    
	@Column(name = "titulo")
	@NotNull(message = "O campo titulo é Obrigatorio")
	private String titulo;
	
	@Column(name = "descricao")
	@NotNull(message = "O campo descricao é Obrigatorio")
	private String descricao;
	
	@Column(name = "tipo_atividade")
	@Enumerated(EnumType.ORDINAL)
	@NotNull(message = "O campo TipoAtividadeEnum é Obrigatorio")
	private TipoAtividadeEnum tipoAtividade;
	
	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	@NotNull(message = "O campo StatusAtividadeEnum é Obrigatorio")
	private StatusAtividadeEnum status;

	public Long getIdAtividade() {
		return idAtividade;
	}

	public void setIdAtividade(Long idAtividade) {
		this.idAtividade = idAtividade;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoAtividadeEnum getTipoAtividade() {
		return tipoAtividade;
	}

	public void setTipoAtividade(TipoAtividadeEnum tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}

	public StatusAtividadeEnum getStatus() {
		return status;
	}

	public void setStatus(StatusAtividadeEnum status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAtividade == null) ? 0 : idAtividade.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Atividade other = (Atividade) obj;
		if (idAtividade == null) {
			if (other.idAtividade != null)
				return false;
		} else if (!idAtividade.equals(other.idAtividade))
			return false;
		return true;
	}
	
	

}
