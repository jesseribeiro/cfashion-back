package br.com.crista.fashion.controller;

import br.com.crista.fashion.bean.UsuarioBean;
import br.com.crista.fashion.security.service.UsuarioPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class GenericController {

    public UsuarioBean getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new RuntimeException("Usuário não encontrado!");
        }
        return ((UsuarioPrincipal) auth.getPrincipal()).getUsuario();
    }
}
