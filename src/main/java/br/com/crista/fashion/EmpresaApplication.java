package br.com.crista.fashion;

import br.com.crista.fashion.service.CidadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

/**
 *
 8 888888888o   8 888888888o.            .8.          8888888 8888888888  8 8888     ,o888888o.        ,o888888o.
 8 8888    `88. 8 8888    `88.          .888.               8 8888        8 8888    8888     `88.   . 8888     `88.
 8 8888     `88 8 8888     `88         :88888.              8 8888        8 8888 ,8 8888       `8. ,8 8888       `8b
 8 8888     ,88 8 8888     ,88        . `88888.             8 8888        8 8888 88 8888           88 8888        `8b
 8 8888.   ,88' 8 8888.   ,88'       .8. `88888.            8 8888        8 8888 88 8888           88 8888         88
 8 888888888P'  8 888888888P'       .8`8. `88888.           8 8888        8 8888 88 8888           88 8888         88
 8 8888         8 8888`8b          .8' `8. `88888.          8 8888        8 8888 88 8888           88 8888        ,8P
 8 8888         8 8888 `8b.       .8'   `8. `88888.         8 8888        8 8888 `8 8888       .8' `8 8888       ,8P
 8 8888         8 8888   `8b.    .888888888. `88888.        8 8888        8 8888    8888     ,88'   ` 8888     ,88'
 8 8888         8 8888     `88. .8'       `8. `88888.       8 8888        8 8888     `8888888P'        `8888888P'

 # Created by Trulogic https://trulogic.com.br/
 # https://devops.datenkollektiv.de/banner.txt/index.html
 */
@Slf4j
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class EmpresaApplication implements CommandLineRunner {

	@Autowired
    CidadeService cidadeService;

	public static void main(String[] args) {
		SpringApplication.run(EmpresaApplication.class, args);
	}

	@Override
	public void run(String... args) throws IOException {
	}
}
