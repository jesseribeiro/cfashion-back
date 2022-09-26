package br.com.crista.fashion.controller;

import br.com.crista.fashion.dto.NovaSenhaDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.service.UsuarioService;
import br.com.crista.fashion.dto.RoleAtualDTO;
import br.com.crista.fashion.dto.UsuarioDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR')")
@Slf4j
@RestController
@RequestMapping(path = "/v1/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid @NotNull UsuarioDTO usuarioDTO) {
        return usuarioService.salvar(usuarioDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
    @PostMapping(path = "/pagination")
    public Page<UsuarioDTO> pagination(@RequestBody PaginationFilterDTO<UsuarioDTO> paginationFilter) {
        return usuarioService.pagination(paginationFilter);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
    @GetMapping(path = "/{id}")
    public UsuarioDTO getById(@PathVariable("id") Long id) {
        return usuarioService.getUsuarioDTOById(id);
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody @Valid @NotNull UsuarioDTO usuarioDTO) {
        return usuarioService.update(id, usuarioDTO);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
    @PostMapping(path = "/{id}/role-ativa")
    public ResponseEntity updateRoleAtiva(@PathVariable("id") Long id, @RequestBody @Valid @NotNull RoleAtualDTO role) {
        return usuarioService.updateRoleAtiva(id, role.getRole());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(HttpServletRequest request, @PathVariable("id") Long id) {
        return usuarioService.delete(request, id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
    @PostMapping(path = "/{id}/alterar-senha")
    public ResponseEntity alterarSenha(@PathVariable("id") Long id, @RequestBody @Valid @NotNull NovaSenhaDTO novaSenha) {
        return usuarioService.alterarSenha(id, novaSenha);
    }

    @GetMapping(path = "/listNegociador")
    public ResponseEntity listAllNegociador(){
        return usuarioService.getAllUsuariosNegoc();
    }
}
