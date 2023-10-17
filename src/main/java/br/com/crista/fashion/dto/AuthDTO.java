package br.com.crista.fashion.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AuthDTO {

    private String username;
    private String password;
}
