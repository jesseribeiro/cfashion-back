package br.com.crista.fashion.controller;

import br.com.crista.fashion.bean.UsuarioBean;
import br.com.crista.fashion.security.service.UsuarioPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.isNull;

public class GenericController {

    public UsuarioBean getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (isNull(auth)) {

            throw new RuntimeException("Usuário não encontrado!");
        }

        return ((UsuarioPrincipal) auth.getPrincipal()).getUsuario();
    }
}
