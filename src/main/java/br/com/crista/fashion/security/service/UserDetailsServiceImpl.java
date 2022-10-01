package br.com.crista.fashion.security.service;
import br.com.crista.fashion.bean.UsuarioBean;
import br.com.crista.fashion.exception.UsuarioInativoException;
import br.com.crista.fashion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        UsuarioBean usuario = usuarioRepository.findById(1L)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado: " + login)
                );
        if(!usuario.getIsAtivo()){
            throw new UsuarioInativoException();
        }
        return UsuarioPrincipal.build(usuario);
    }
}