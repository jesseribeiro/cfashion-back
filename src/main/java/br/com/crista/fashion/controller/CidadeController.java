package br.com.crista.fashion.controller;

import br.com.crista.fashion.bean.CidadeBean;
import br.com.crista.fashion.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@RestController
@RequestMapping( path = "/v1/cidade", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController {

    @Autowired
    CidadeService cidadeService;

    @GetMapping(path = "/{id}")
    public CidadeBean findById(@PathVariable("id") Long id){
        return cidadeService.findById(id);
    }

    @GetMapping(path = "/uf/{uf}")
    public List<CidadeBean> findByUf(@PathVariable("uf") String uf){
        return cidadeService.findByUf(uf);
    }

    @GetMapping(path = "/atualiza-cidades")
    public ResponseEntity atualizaCidades(){
        try {
            cidadeService.updateCidadeSemAcento();
            return ResponseEntity.ok("Sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Não foi possível atualizar as cidades");
        }
    }
}
