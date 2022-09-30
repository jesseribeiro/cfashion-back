package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.UsuarioBean;
import br.com.crista.fashion.dto.NovaSenhaDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.UsuarioDTO;
import br.com.crista.fashion.enumeration.EnumRole;
import br.com.crista.fashion.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@Slf4j
@Service
public class UsuarioService extends GenericService<UsuarioBean, UsuarioRepository> {

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity salvar(UsuarioDTO usuarioDTO){
        if(getRepository().existsByLogin(usuarioDTO.getLogin())) {
            return new ResponseEntity<String>("O Login informado já existe no sistema, favor tentar outro!",
                    HttpStatus.BAD_REQUEST);
        }

        UsuarioBean usuario = new UsuarioBean();
        usuario = usuarioDTO.converter(usuario);
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        // TODO caso usuário possa selecionar mais de uma loja, só deletar essas 3 linhas
        List<Long> loja = new ArrayList<>();
        loja.add(usuarioDTO.getLojaId());
        usuarioDTO.setLojas(loja);
        save(usuario);

        return ResponseEntity.ok().body("Usuário cadastrado com sucesso!");
    }

    public ResponseEntity update(Long usuarioId, UsuarioDTO usuarioDTO){
        UsuarioBean usuario = getById(usuarioId);
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setIsAtivo(usuarioDTO.isAtivo());
        usuario.setBloqueioIp(usuarioDTO.getBloqueioIp());

        // TODO caso usuário possa selecionar mais de uma loja, só deletar essas 3 linhas
        List<Long> loja = new ArrayList<>();
        loja.add(usuarioDTO.getLojaId());
        usuarioDTO.setLojas(loja);
        update(usuario);
        return ResponseEntity.ok().body("Usuário atualizado com sucesso!");
    }

    public UsuarioDTO getUsuarioDTOById(Long id) {
        UsuarioBean usuario = getById(id);
        return new UsuarioDTO(usuario);
    }

    public ResponseEntity delete(HttpServletRequest request, Long id) {
        if(hasPermissao(request, new EnumRole[]{ EnumRole.ADMIN, EnumRole.SUPERVISOR })) {
            UsuarioBean usuario = getById(id);
            delete(usuario);
            return ResponseEntity.ok().body("Usuário excluído com sucesso!");
        } else {
            return ResponseEntity.status(METHOD_NOT_ALLOWED).body("Sem Permissão para realizar essa operação");
        }
    }

    public Page<UsuarioDTO> pagination(PaginationFilterDTO<UsuarioDTO> paginationFilterDTO){
        Pageable paging = PageRequest.of(paginationFilterDTO.getPageNo(), paginationFilterDTO.getPageSize(), Sort.by(paginationFilterDTO.getSortBy()));
        UsuarioDTO filtros = paginationFilterDTO.getFiltros();

        Page<UsuarioDTO> usuarios = getRepository().pagination(filtros.getNome(), filtros.getLogin(), filtros.getEmail(), paging);
        if(usuarios.hasContent()) {
            return usuarios;
        } else {
            return Page.empty();
        }
    }

    public ResponseEntity updateRoleAtiva(Long id, String role) {
        UsuarioBean usuario = getById(id);
        usuario.setRoleAtiva(EnumRole.valueOf(role));
        update(usuario);
        return ResponseEntity.ok().body("Role atualizada com sucesso!");
    }

    public ResponseEntity alterarSenha(Long usuarioId, NovaSenhaDTO novaSenha) {
        try {
            UsuarioBean usuario = getById(usuarioId);
            usuario.setSenha(passwordEncoder.encode(novaSenha.getSenha()));
            update(usuario);
            return ResponseEntity.ok().body("Senha alterada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao tentar alterar a senha, tente novamente mais tarde");
        }
    }

    public List<UsuarioBean> getAllUsuariosByRole(EnumRole role) {
        return getRepository().getAllUsuariosByRole(role);
    }

    public ResponseEntity getAllUsuariosNegoc() {
        EnumRole [] roles = {EnumRole.NEGOCIADOR, EnumRole.ADMIN, EnumRole.SUPERVISOR, EnumRole.COMERCIAL};
        return ResponseEntity.ok(getRepository().getAllUsuariosDTOByRole(roles));
    }
}
