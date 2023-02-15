package br.com.crista.fashion.service;

import br.com.crista.fashion.dto.ComissaoDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class ComissaoServiceTest {

    @Autowired
    ComissaoService comissaoService;

    @Test
    public void testComissao() {
        ComissaoDTO dto = new ComissaoDTO();
        dto.setComissao(new BigDecimal(0.20));
        dto.setId(2L);

        comissaoService.editarComissao(dto);

    }
}
