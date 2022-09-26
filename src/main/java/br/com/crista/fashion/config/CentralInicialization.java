package br.com.crista.fashion.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.FileSystemUtils;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration("centralInicialization")
public class CentralInicialization {

    @Autowired
    private CentralConfig centralConfig;

    @PostConstruct
    public void centralInicialization() {
        criarDiretorios();
    }

    public void criarDiretorios() {
        new File(centralConfig.getCentralPath()).mkdir();
        new File(centralConfig.getCentralAnexosPath()).mkdir();
        new File(centralConfig.getCentralImagesPath()).mkdir();
        new File(centralConfig.getCentralRemessasPath()).mkdir();
        new File(centralConfig.getCentralImportacaoPath()).mkdir();

        // Diretório temp é excluído sempre que o sistema sobe
        FileSystemUtils.deleteRecursively(new File(centralConfig.getCentralTempPath()));
        new File(centralConfig.getCentralTempPath()).mkdir();
    }
}
