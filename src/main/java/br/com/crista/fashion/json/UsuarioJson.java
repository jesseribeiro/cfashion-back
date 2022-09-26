package br.com.crista.fashion.json;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioJson {

    private Long id;
    private Long empresaId;
    private Long lojaId;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String[] roles;
    private boolean isAtivo;


}
