package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.UsuarioBean;
import br.com.crista.fashion.security.SecurityUtils;
import br.com.crista.fashion.security.service.UsuarioPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UsuarioLogadoService {

    public UsuarioBean getUsuarioLogado(){
        UserDetails userDetails = SecurityUtils.getCurrentUserLogin().orElse(null);
        if(userDetails != null) {
            return ((UsuarioPrincipal) userDetails).getUsuario();
        }
        return null;
    }
}
