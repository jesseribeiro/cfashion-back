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

    @GetMapping( path = "/tipos-conta-banco")
    public List<LabelDescricaoDTO> getAllTipoContaBanco(){
        return TipoContaBanco.getLabels();
    }

    @GetMapping( path = "/sexo")
    public List<LabelDescricaoDTO> getAllSexo(){
        return EnumSexo.getLabels();
    }

    @GetMapping( path = "/estado-civil")
    public List<LabelDescricaoDTO> getAllEstadoCivil(){
        return EnumEstadoCivil.getLabels();
    }

    @GetMapping( path = "/escolaridade")
    public List<LabelDescricaoDTO> getAllEscolaridade(){
        return EnumEscolaridade.getDescricoes();
    }

    @GetMapping( path = "/tipo-residencia")
    public List<LabelDescricaoDTO> getAllTipoResidencia(){
        return EnumTipoResidencia.getLabels();
    }

    @GetMapping( path = "/tipo-pagamento")
    public List<LabelDescricaoDTO> getAllTipoPagamento(){
        return EnumTipoPagamento.getLabels();
    }

    @GetMapping( path = "/periodo-relatorio")
    public List<LabelDescricaoDTO> getAllPeriodoRelatorio(){
        return EnumPeriodoRelatorio.getLabels();
    }

    @GetMapping( path = "/por-relatorio")
    public List<LabelDescricaoDTO> getAllPorRelatorio(){
        return EnumMetodoPorRelatorio.getLabels();
    }

    @GetMapping( path = "/totalizar-data-relatorio")
    public List<LabelDescricaoDTO> getAllTotalizarPorDataRelatorio(){
        return EnumTotalizarPorDataRelatorio.getLabels();
    }

    @GetMapping( path = "/tipo-referencia-pessoal")
    public List<LabelDescricaoDTO> getAllTipoReferenciaPessoal(){
        return EnumTipoReferencia.getLabels();
    }

    @GetMapping( path = "/status-loja")
    public StatusLoja[] getAllStatusLoja(){
        return StatusLoja.values();
    }

    @GetMapping( path = "/bancos")
    public List<LabelDescricaoDTO> getAllBancos(){
       return EnumBanco.getLabels();
    }

    @GetMapping( path = "/lojas/{empresaId}")
    public List<CodigoDescricaoDTO> getAllLojas(){
        return tiposRepository.findAllLojas();
    }

    @GetMapping( path = "/status-carne")
    public List<LabelDescricaoDTO> getAllStatusCarne(){
        return EnumStatusCarne.getLabels();
    }

    @GetMapping( path = "/status-cliente")
    public List<LabelDescricaoDTO> getAllStatusCliente(){
        return EnumStatusCliente.getLabels();
    }

    @GetMapping( path = "/situacao-conta")
    public List<LabelDescricaoDTO> getAllSituacaoConta(){
        return EnumSituacaoConta.getLabels();
    }

    @GetMapping( path = "/status")
    public List<LabelDescricaoDTO> getAllStatus(){
        return EnumStatus.getLabels();
    }

    @GetMapping( path = "/status-venda")
    public List<LabelDescricaoDTO> getAllStatusVenda(){
        return EnumStatusVenda.getLabels();
    }

    @GetMapping( path = "/tipos-relatorio")
    public List<LabelDescricaoDTO> getAllTiposRelatorio(){
        return EnumTipoRelatorio.getLabels();
    }

    @GetMapping( path = "/usuarios")
    public List<LabelDescricaoDTO> getAllUsuarios(){
        return tiposRepository.findAllUsuarios();
    }

    @GetMapping( path = "/operacoes-auditoria")
    public List<LabelDescricaoDTO> getAllOperacoesAuditoria(){
        return EnumOperacaoAuditoria.getDescricoes();
    }

    @GetMapping( path = "/prioridades")
    public List<LabelDescricaoDTO> getAllPrioridades(){
        return EnumPrioridade.getLabels();
    }

    @GetMapping( path = "/saldo-devedor")
    public List<LabelDescricaoDTO> getAllSaldoDevedor(){
        return EnumSaldoDevedor.getLabels();
    }

    @GetMapping( path = "/segmentos")
    public List<LabelDescricaoDTO> getAllSegmentos(){
        return EnumSegmentacao.getLabels();
    }

    @GetMapping( path = "/tipos-regra")
    public List<LabelDescricaoDTO> getAllTiposRegra(){
        return EnumTipoRegra.getLabels();
    }

    @GetMapping( path = "/roles-ativas")
    public List<LabelDescricaoDTO> getAllRoles(){
        return EnumRole.getLabels();
    }

    @GetMapping( path = "/recusa-venda")
    public List<LabelDescricaoDTO> getAllRecusaVenda(){
        return EnumRecusaVenda.getLabels();
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
