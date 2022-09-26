package br.com.crista.fashion.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthDTO {

    private String username;
    private String password;
}
