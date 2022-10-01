package br.com.crista.fashion.controller;

import br.com.crista.fashion.dto.CodigoDescricaoDTO;
import br.com.crista.fashion.dto.LabelDescricaoDTO;
import br.com.crista.fashion.enumeration.*;
import br.com.crista.fashion.repository.TiposRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@RestController
@RequestMapping(path = "/v1/tipos", produces = MediaType.APPLICATION_JSON_VALUE)
public class TiposController {

    @Autowired
    TiposRepository tiposRepository;

    @GetMapping( path = "/sexo")
    public List<LabelDescricaoDTO> getAllSexo(){
        return EnumSexo.getLabels();
    }

    @GetMapping( path = "/tipo-pagamento")
    public List<LabelDescricaoDTO> getAllTipoPagamento(){
        return EnumTipoPagamento.getLabels();
    }

    @GetMapping( path = "/lojas/{empresaId}")
    public List<CodigoDescricaoDTO> getAllLojas(){
        return tiposRepository.findAllLojas();
    }

    @GetMapping( path = "/status")
    public List<LabelDescricaoDTO> getAllStatus(){
        return EnumStatus.getLabels();
    }

    @GetMapping( path = "/usuarios")
    public List<LabelDescricaoDTO> getAllUsuarios(){
        return tiposRepository.findAllUsuarios();
    }

    @GetMapping( path = "/roles-ativas")
    public List<LabelDescricaoDTO> getAllRoles(){
        return EnumRole.getLabels();
    }

    @GetMapping( path = "/categorias")
    public List<LabelDescricaoDTO> getAllCategorias(){
        return EnumCategoria.getLabels();
    }

    @GetMapping( path = "/tamanhos")
    public List<LabelDescricaoDTO> getAllTamanho(){
        return EnumTamanho.getLabels();
    }
}
