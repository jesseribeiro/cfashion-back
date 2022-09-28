package br.com.crista.fashion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CentralConfig {

    @Value("${central.path}")
    private String centralUrl;
}
