package br.com.crista.fashion.feign;

import br.com.crista.fashion.bean.CepBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url="https://viacep.com.br/ws/", name = "viacep")
public interface ViaCEPClient {

    @GetMapping("{cep}/json")
    CepBean buscaEnderecoPor(@PathVariable("cep") String cep);
}
