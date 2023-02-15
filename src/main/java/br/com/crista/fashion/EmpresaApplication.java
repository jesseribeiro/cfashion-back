package br.com.crista.fashion;

import br.com.crista.fashion.service.ComissaoService;
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
 a88888b.          oo            dP                88888888b                   dP       oo
 d8'   `88                        88                88                          88
 88        88d888b. dP .d8888b. d8888P .d8888b.    a88aaaa    .d8888b. .d8888b. 88d888b. dP .d8888b. 88d888b.
 88        88'  `88 88 Y8ooooo.   88   88'  `88     88        88'  `88 Y8ooooo. 88'  `88 88 88'  `88 88'  `88
 Y8.   .88 88       88       88   88   88.  .88     88        88.  .88       88 88    88 88 88.  .88 88    88
 Y88888P' dP       dP `88888P'   dP   `88888P8     dP        `88888P8 `88888P' dP    dP dP `88888P' dP    dP


 # https://devops.datenkollektiv.de/banner.txt/index.html
 */
@Slf4j
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class EmpresaApplication implements CommandLineRunner {

	@Autowired
	ComissaoService comissaoService;

	public static void main(String[] args) {
		SpringApplication.run(EmpresaApplication.class, args);
	}

	@Override
	public void run(String... args) throws IOException {

	}
}
