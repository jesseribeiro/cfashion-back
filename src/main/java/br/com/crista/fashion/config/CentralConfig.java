package br.com.crista.fashion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class CentralConfig {

    @Value("${central.path}")
    private String centralUrl;

    public String getCentralPath(){
        return centralUrl;
    }

    public String getCentralImagesPath() {
        return centralUrl.concat("/images/");
    }

    public String getCentralAnexosPath() {
        return centralUrl.concat("/anexos/");
    }

    public String getCentralRemessasPath() {
        return centralUrl.concat("/remessas/");
    }

    public String getCentralTempPath() {
        return centralUrl.concat("/temp/");
    }
    public String getCentralImportacaoPath() {
        return centralUrl.concat("/importacao/");
    }
}
