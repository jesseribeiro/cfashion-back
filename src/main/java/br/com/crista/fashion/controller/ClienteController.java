package br.com.crista.fashion.controller;

import br.com.crista.fashion.config.CentralConfig;
import br.com.crista.fashion.dto.*;
import br.com.crista.fashion.report.comprovantequitacao.ComprovanteQuitacaoPDF;
import br.com.crista.fashion.service.ClienteService;
import br.com.crista.fashion.service.LojaService;
import br.com.crista.fashion.service.PagamentoService;
import br.com.crista.fashion.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@PreAuthorize("hasAnyAuthority('ADMIN','SUPERVISOR', 'COMERCIAL', 'NEGOCIADOR', 'PROPRIETARIO', 'CREDIARISTA')")
@Slf4j
@RestController
@RequestMapping(path = "/v1/cliente", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteController extends GenericController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CentralConfig centralConfig;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private LojaService lojaService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid @NotNull ClienteDTO dto) {
        try {
            return ResponseEntity.ok(clienteService.salvar(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/pagination")
    public Page<ClienteLojaDTO> pagination(@RequestBody PaginationFilterDTO<ClienteDTO> paginationFilter) {
        return clienteService.pagination(paginationFilter);
    }

    @GetMapping
    public List<ClienteDTO> findAll() {
        return clienteService.findAllDTO();
    }

    @GetMapping(path = "/{id}")
    public ClienteDTO getById(@PathVariable("id") Long id) {
        return clienteService.getClienteDTOById(id);
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody @Valid @NotNull ClienteDTO dto) {
        try {
            return ResponseEntity.ok(clienteService.update(id, dto));
        } catch(Exception e){
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao atualizar: - " + e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        return clienteService.delete(id);
    }

    @RequestMapping(value = "/{id}/limite-exclusivo/{lojaId}", method = RequestMethod.GET)
    public BigDecimal getLimiteExclusivoCliente(@PathVariable("id") Long clienteId, @PathVariable("lojaId") Long lojaId) throws IOException {
        return clienteService.getLimiteExclusivoCliente(clienteId, lojaId);
    }

    @RequestMapping(value = "/{id}/limite-compartilhado-disponivel", method = RequestMethod.GET)
    public BigDecimal getLimiteCompartilhadoDisponivel(@PathVariable("id") Long clienteId) throws IOException {
        return clienteService.getLimiteCompartilhadoDisponivel(clienteId);
    }

    @RequestMapping(value = "/{id}/limite-exclusivo-disponivel/{lojaId}", method = RequestMethod.GET)
    public BigDecimal getLimiteExclusivoDisponivel(@PathVariable("id") Long clienteId, @PathVariable("lojaId") Long lojaId) throws IOException {
        return clienteService.getLimiteExclusivoDisponivel(clienteId, lojaId);
    }

    @RequestMapping(value = "/{id}/limite-disponivel/{lojaId}", method = RequestMethod.GET)
    public BigDecimal getLimiteDisponivel(@PathVariable("id") Long clienteId, @PathVariable("lojaId") Long lojaId) throws IOException {
        return clienteService.getLimiteDisponivelParaCompra(clienteId, lojaId);
    }

    @PostMapping(path = "/{id}/carnes")
    public List<CarneClienteDTO> getCarnesCliente(@PathVariable("id") Long id, @RequestBody CarneClienteDTO filtro) {
        return clienteService.getCarnesCliente(id, filtro);
    }

    @PostMapping(path = "/{id}/pagamentos")
    public Page<PagamentoDTO> paginationPagamentos(@PathVariable("id") Long id, @RequestBody PaginationFilterDTO<PagamentoDTO> paginationFilter) {
        return clienteService.paginationPagamentos(id, paginationFilter);
    }

    @PostMapping(path = "/{cpf}/parcelas")
    public Page<ParcelaClienteDTO> paginationCarnes(@PathVariable("cpf") String cpf, @RequestBody PaginationFilterDTO<ParcelaClienteDTO> paginationFilter) {
        return clienteService.paginationParcelas(cpf, paginationFilter);
    }

    @RequestMapping(value = "/{id}/altera-limite-cliente", method = RequestMethod.POST)
    public ResponseEntity alteraLimiteCliente(@PathVariable("id") Long clienteId, @RequestBody NovoLimiteClienteDTO dto) {
        try {
            clienteService.alteraLimiteCliente(clienteId, dto);
            return ResponseEntity.ok("Operação feita com sucesso");
        } catch(Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body("Este valor não está disponível - " + e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}/altera-status-cliente/{lojaId}", method = RequestMethod.POST)
    public ResponseEntity alteraStatusCliente(@PathVariable("id") Long clienteId, @PathVariable("lojaId") Long lojaId, @RequestBody NovoStatusClienteDTO dto) {
        try {
            clienteService.alteraStatusCliente(clienteId, lojaId, dto);
            return ResponseEntity.ok("Status alterado");
        } catch(Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body("Status não alterado - " + e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}/resumo-cliente/{lojaId}", method = RequestMethod.GET)
    public ResponseEntity resumoCliente(@PathVariable("id") Long clienteId, @PathVariable("lojaId") Long lojaId) {
        try {
            return ResponseEntity.ok(clienteService.resumoCliente(clienteId, lojaId));
        } catch(Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao buscar informações - " + e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}/resumo-cliente-outras-lojas/{lojaId}", method = RequestMethod.GET)
    public ResponseEntity resumoClienteOutrasLojas(@PathVariable("id") Long clienteId, @PathVariable("lojaId") Long lojaId) {
        try {
            return ResponseEntity.ok(clienteService.resumoClienteOutrasLojas(clienteId, lojaId));
        } catch(Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.badRequest().body("Erro ao buscar informações - " + e.getMessage());
        }
    }

    @PostMapping(path = "/verifica-cliente")
    public ResponseEntity verificaCliente(@RequestBody @Valid @NotNull ClienteLojaDTO dto) {
        try {
            if (clienteService.verificaCliente(dto.getClienteId(), dto.getLojaId())) {
                return ResponseEntity.badRequest().body("Cliente tem parcelas em aberto ainda neste ano");
            } else {
                return ResponseEntity.ok().body("Sucesso");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/imprime-comprovante", method = RequestMethod.POST)
    public ResponseEntity<Resource> imprimeComprovante(@RequestBody ClienteLojaDTO dto) {
        try {
            List<PagamentoDTO> dados;
            dados = pagamentoService.quitacaoDeCompra(dto);

            ComprovanteQuitacaoPDF reportPDF = new ComprovanteQuitacaoPDF(dados,
                    clienteService.getClienteDTOById(dto.getClienteId()), lojaService.getByIdDTO(dto.getLojaId()), centralConfig.getCentralTempPath());
            return FileUtils.getFile(reportPDF.print(), true);
        } catch (Exception e) {
            log.error("Não encontrou o arquivo", e);
            throw new RuntimeException("Não encontrou o arquivo");
        }
    }
}
