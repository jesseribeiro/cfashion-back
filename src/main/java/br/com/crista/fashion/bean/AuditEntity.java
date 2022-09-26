package br.com.crista.fashion.bean;//imports omitidos

import lombok.Data;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;

// https://www.infoq.com/br/articles/auditoria-simples-com-hibernate-envers/
@Data
@Entity(name="revinfo_cust")
@RevisionEntity(AuditListener.class)
public class AuditEntity extends DefaultRevisionEntity {
	
	private static final long serialVersionUID = 1L;
	
	public String usuario;
	public String ip;
}