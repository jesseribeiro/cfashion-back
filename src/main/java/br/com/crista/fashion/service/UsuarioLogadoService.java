package br.com.crista.fashion.service;

import static java.util.Objects.nonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.crista.fashion.bean.UsuarioBean;
import br.com.crista.fashion.security.SecurityUtils;
import br.com.crista.fashion.security.service.UsuarioPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioLogadoService {

    public UsuarioBean getUsuarioLogado() {

        UserDetails userDetails = SecurityUtils.getCurrentUserLogin().orElse(null);

        if (nonNull(userDetails)) {

            return ((UsuarioPrincipal) userDetails).getUsuario();
        }

        return null;
    }
}
