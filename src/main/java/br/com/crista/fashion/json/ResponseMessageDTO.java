package br.com.crista.fashion.json;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseMessageDTO {

    private String requestId;
    private String response;
    private String message;
    private String midia;
    private String destino;
    private Integer status;
}
