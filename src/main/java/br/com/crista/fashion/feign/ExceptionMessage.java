package br.com.crista.fashion.feign;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExceptionMessage {

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}