package br.com.crista.fashion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.crista.fashion.dto.CodigoDescricaoDTO;
import br.com.crista.fashion.dto.LabelDescricaoDTO;
import br.com.crista.fashion.enumeration.EnumBalanco;
import br.com.crista.fashion.enumeration.EnumCategoria;
import br.com.crista.fashion.enumeration.EnumRole;
import br.com.crista.fashion.enumeration.EnumSexo;
import br.com.crista.fashion.enumeration.EnumStatus;
import br.com.crista.fashion.enumeration.EnumTamanho;
import br.com.crista.fashion.enumeration.EnumTipoPagamento;
import br.com.crista.fashion.enumeration.EnumTipoRelatorio;
import br.com.crista.fashion.repository.TiposRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/v1/tipos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TiposController {

    private final @NonNull
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

    @GetMapping( path = "/balancos")
    public List<LabelDescricaoDTO> getAllBalanco(){
        return EnumBalanco.getLabels();
    }

    @GetMapping( path = "/tipos-relatorio")
    public List<LabelDescricaoDTO> getAllTiposRelatorio(){
        return EnumTipoRelatorio.getLabels();
    }
}
