package br.com.crista.fashion.service;

import br.com.crista.fashion.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class ClienteServiceTest {

    @Test
    public void testCalcul() {

        Double primeiro = 1526.02D;

        Double resultado = 0.5D;
        Double segundo = 1500.06D;

        Double dr = primeiro - segundo;
        Double calculo1 = dr/600;
        Double calculo2 = 0-calculo1;
        Double calculo3 = Math.pow(10D, calculo2);
        Double calculo4 = calculo3 + 1;
        Double calculo5 = 1 / calculo4;
        Double calculo6 = resultado - calculo5;
        Double calculo7 = 10 * calculo6;

        BigDecimal big1 = new BigDecimal(calculo7);

        resultado = 1D;
        segundo = 1484.95D;

        dr = primeiro - segundo;
        calculo1 = dr/600;
        calculo2 = 0-calculo1;
        calculo3 = Math.pow(10D, calculo2);
        calculo4 = calculo3 + 1;
        calculo5 = 1 / calculo4;
        calculo6 = resultado - calculo5;
        calculo7 = 10 * calculo6;
        BigDecimal big2 = new BigDecimal(calculo7);

        BigDecimal total = big1.add(big2);
        log.info(MathUtils.convertBigDecimalToString(total));
    }
}
