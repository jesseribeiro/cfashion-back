package br.com.crista.fashion.bean;//imports omitidos

import br.com.crista.fashion.service.UsuarioLogadoService;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Component
public class AuditListener implements RevisionListener {

    @Autowired
    UsuarioLogadoService usuarioLogadoService;

    @Override
    public void newRevision(Object revisionEntity) {

    	AuditEntity revEntity = (AuditEntity) revisionEntity;
        UsuarioBean usuario = usuarioLogadoService.getUsuarioLogado();

        if (nonNull(usuario)) {

    	    revEntity.setUsuario(usuario.getLogin());
            revEntity.setIp(usuario.getIp());
        }
    }
}