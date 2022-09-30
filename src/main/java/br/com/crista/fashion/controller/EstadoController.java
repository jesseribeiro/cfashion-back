package br.com.crista.fashion.controller;

import br.com.crista.fashion.bean.EstadoBean;
import br.com.crista.fashion.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@RestController
@RequestMapping(path = "/v1/estado", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController {

    @Autowired
    EstadoService estadoService;

    @GetMapping
    public List<EstadoBean> findAll(){
        return estadoService.findAll();
    }

    @GetMapping(path = "/{id}")
    public EstadoBean findById(@PathVariable("id") Long id){
        return estadoService.findById(id);
    }
}
