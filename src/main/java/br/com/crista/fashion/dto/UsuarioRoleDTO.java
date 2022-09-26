package br.com.crista.fashion.dto;

import br.com.crista.fashion.bean.UsuarioBean;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRoleDTO {

    private Long id;
    private String nome;
    private String role;

    public UsuarioRoleDTO(UsuarioBean bean) {
        id = bean.getId();
        nome = bean.getNome();
        role = bean.getRoleAtiva().name();
    }
}
