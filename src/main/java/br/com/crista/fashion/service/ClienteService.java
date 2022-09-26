package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.bean.ClienteLojaBean;
import br.com.crista.fashion.bean.LojaBean;
import br.com.crista.fashion.bean.VendaBean;
import br.com.crista.fashion.config.CentralConfig;
import br.com.crista.fashion.dto.*;
import br.com.crista.fashion.enumeration.*;
import br.com.crista.fashion.repository.ClienteRepository;
import br.com.crista.fashion.repository.ParcelaRepository;
import br.com.crista.fashion.repository.UsuarioRepository;
import br.com.crista.fashion.repository.impl.CarneRepositoryImpl;
import br.com.crista.fashion.repository.impl.ClienteRepositoryImpl;
import br.com.crista.fashion.utils.Constants;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.MathUtils;
import br.com.crista.fashion.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
public class ClienteService extends GenericService<ClienteBean, ClienteRepository> {

    @Autowired
    private LojaService lojaService;

    @Autowired
    private ClienteLojaService clienteLojaService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CentralConfig centralConfig;

    @Autowired
    private AutorizacaoService autorizacaoService;

    @Autowired
    private LimiteExclusivoService limiteExclusivoService;

    @Autowired
    private CarneRepositoryImpl carneRepository;

    @Autowired
    private ClienteRepositoryImpl clienteRepository;

    @Autowired
    private CEPService cepService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ParcelaService parcelaService;

    @Autowired
    private ParcelaRepository parcelaRepository;

    private List<String> validExtensions = Arrays.asList("jpeg", "jpg", "png");

    public ClienteDTO quitacao(ClienteDTO dto) {

        ClienteDTO clienteDTORetorno = dto;
        clienteDTORetorno.setLojaId(dto.getLojaId());
        clienteDTORetorno.setEmpresaId(dto.getEmpresaId());
        clienteDTORetorno.setRedeId(dto.getRedeId());

        return clienteDTORetorno;
    }


    public ClienteDTO salvar(ClienteDTO dto) {

        String digito = dto.getCelular().substring(5, 6);

        if (!digito.equalsIgnoreCase("9") && !digito.equalsIgnoreCase("8")
                && !digito.equalsIgnoreCase("7")) {
            throw new RuntimeException ("Telefone celular inválido");
        }
        ClienteBean cliente = new ClienteBean();
        cliente = dto.converter(cliente);
        cliente.setStatus(EnumStatusCliente.NORMAL);
        preencheCidadeEndereco(cliente);

        save(cliente);

        ClienteDTO clienteDTORetorno = new ClienteDTO(cliente);
        clienteDTORetorno.setLojaId(dto.getLojaId());
        clienteDTORetorno.setEmpresaId(dto.getEmpresaId());
        clienteDTORetorno.setRedeId(dto.getRedeId());

        return clienteDTORetorno;
    }

    private void preencheCidadeEndereco(ClienteBean cliente) {
        cliente.getEndereco().setCidadeNome(cidadeService.getCidadeNomeByCidadeIbge(cliente.getEndereco().getCidadeIbge()));
    }

    // TODO pegar os filtros de redeid, empresaid, lojaid baseado na role do usuario logado.
    public Page<ClienteLojaDTO> pagination(PaginationFilterDTO<ClienteDTO> paginationFilter) {
        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by("x."+paginationFilter.getSortBy()));
        ClienteDTO filtros = paginationFilter.getFiltros();
        Long redeId = selecionouTodas(filtros.getRedeId());
        Long empresaId= selecionouTodas(filtros.getEmpresaId());
        Long lojaId = selecionouTodas(filtros.getLojaId());
        String cpf = filtros.getCpf() == null ? "" : filtros.getCpf();
        Page<ClienteLojaDTO> clientes = getRepository().pagination(
                lojaId,
                filtros.getNome(),
                StringUtils.desformataCpfCnpj(cpf),
                DateUtils.getDiaMesAno(filtros.getDataNascimento()),
                filtros.getTelResidencial(),
                filtros.getIdentidade(),
                paging);
        if(clientes.hasContent()) {
            return clientes;
        } else {
            return Page.empty();
        }
    }

    private Long selecionouTodas(Long selectedId) {
        return (selectedId != null && selectedId.intValue() != Constants.SELECT_TODAS.intValue()) ? selectedId : null;
    }

    public List<ClienteDTO> findAllDTO() {
        return null;
    }

    public ClienteDTO getClienteDTOById(Long id) {
        return getRepository().findDTO(id);
    }

    public ClienteBean getById(Long id) {
        return getRepository().findById(id).get();
    }

    public ClienteDTO update(Long id, ClienteDTO dto) {
        String digito = dto.getCelular().substring(5, 6);

        if (!digito.equalsIgnoreCase("9") && !digito.equalsIgnoreCase("8")
                && !digito.equalsIgnoreCase("7")) {
            throw new RuntimeException("Telefone celular inválido");
        }
        ClienteBean cliente = getRepository().findById(id).get();
        cliente = dto.converter(cliente);
        preencheCidadeEndereco(cliente);

        update(cliente);

        ClienteDTO clienteDTORetorno = new ClienteDTO(cliente);

        clienteDTORetorno.setLojaId(dto.getLojaId());
        clienteDTORetorno.setEmpresaId(dto.getEmpresaId());
        clienteDTORetorno.setRedeId(dto.getRedeId());

        return clienteDTORetorno;
    }

    public ResponseEntity delete(Long id) {
        return null;
    }

    public BigDecimal getLimiteExclusivoCliente(Long clienteId, Long lojaId) {
        BigDecimal limiteExclusivo = getRepository().findLimiteExclusivoCliente(clienteId, lojaId);
        return limiteExclusivo == null ? BigDecimal.ZERO : limiteExclusivo;
    }

    public void alteraLimiteCliente(Long clienteId, NovoLimiteClienteDTO dto) {

        ClienteBean bean = getById(clienteId);
        BigDecimal valorMaximo = BigDecimal.valueOf(10000);
        if (getUsuarioLogado().getRoleAtiva() == EnumRole.ADMIN){
            if(dto.getNovoLimite().compareTo(valorMaximo) == -1){
                bean.setLimiteCompartilhado(dto.getNovoLimite());
                update(bean);
            }
            else{
                throw new RuntimeException("O valor deve ser menor que " + valorMaximo);
            }
        }
        valorMaximo = BigDecimal.valueOf(5000);
        if (getUsuarioLogado().getRoleAtiva() == EnumRole.SUPERVISOR){
            if(dto.getNovoLimite().compareTo(valorMaximo) == -1){
                bean.setLimiteCompartilhado(dto.getNovoLimite());
                update(bean);
            }
            else{
                throw new RuntimeException("O valor deve ser menor que " + valorMaximo);
            }
        }
    }

    public void alteraStatusCliente(Long clienteId, Long lojaId, NovoStatusClienteDTO dto) {

        if (verificaStatus(dto.getNovoStatus())) {
            List<ClienteLojaBean> lista = clienteLojaService.findByClienteId(clienteId);
            if (lista != null) {
                for (ClienteLojaBean clienteLojaBean : lista) {
                    clienteLojaBean.setStatus(dto.getNovoStatus());
                    clienteLojaBean.setDataStatus(Calendar.getInstance());
                    clienteLojaService.update(clienteLojaBean);
                }
            }
        } else {
            ClienteLojaBean bean = clienteLojaService.findByClienteLoja(clienteId, lojaId);
            bean.setStatus(dto.getNovoStatus());
            bean.setDataStatus(Calendar.getInstance());
            clienteLojaService.update(bean);
        }
    }

    public BigDecimal getLimiteCompartilhadoDisponivel(Long clienteId) {
        BigDecimal limiteCompartilhadoDisponivel = getRepository().findLimiteCompartilhadoDisponivel(clienteId);
        return limiteCompartilhadoDisponivel == null ? BigDecimal.ZERO : limiteCompartilhadoDisponivel;
    }

    public BigDecimal getLimiteExclusivoDisponivel(Long clienteId, Long lojaId) {
        BigDecimal limiteExclusivoDisponivel = getRepository().findLimiteExclusivoDisponivel(clienteId, lojaId);
        return limiteExclusivoDisponivel == null ? BigDecimal.ZERO : limiteExclusivoDisponivel;
    }

    // Caso o cliente possua um limite compartilhado de R$ 1.000,00 e na ótica da Maria tem um limite exclusivo de R$ 1.000,00
    // só que ele já usou o limite exclusivo, e quer comprar mais uma coisa, NÃO PODE SER UTILIZADO O LIMITE COMPARTILHADO
    // essa dívida foi tirada com o Louis
    // Essa duvida esta no trello (https://trello.com/c/dv5Yh2TS/213-sprint-06-01-a-20-01)
    public BigDecimal getLimiteDisponivelParaCompra(Long clienteId, Long lojaId) {

        BigDecimal limiteDisponivel = getLimiteCompartilhadoDisponivel(clienteId);
        if (lojaId != null) {
            LojaBean loja = lojaService.getLojaById(lojaId);
            if(loja.getIsPossuiLimiteExclusivo()) {
                limiteDisponivel = getLimiteExclusivoDisponivel(clienteId, lojaId);
            }
        }
        return limiteDisponivel;
    }

    public List<CarneClienteDTO> getCarnesCliente(Long clienteId, CarneClienteDTO filtro) {

        Boolean somenteCarne = false;
        /*
        Comentado por enquanto, pq os lojistas tbem precisam ver os boletos
        if (getUsuarioLogado().getRoleAtiva() == EnumRole.CREDIARISTA ||
                getUsuarioLogado().getRoleAtiva() == EnumRole.PROPRIETARIO) {
            somenteCarne = true;
        }*/
        EnumStatusCarne status = filtro.getStatus() != null ? EnumStatusCarne.valueOf(filtro.getStatus()) : null;
        List<CarneClienteDTO> carnesCliente = carneRepository.findCarnesCliente(clienteId, filtro.getLojaId(), filtro.getDataInicial(),
                filtro.getDataFinal(), status, somenteCarne);

        for(CarneClienteDTO dto : carnesCliente) {
            if(dto.getParcelas() != null && !dto.getParcelas().isEmpty()) {
                CarneClienteDTO carne = calculaMultaEJurosMoraParcelas(dto.getParcelas());
                dto.setTotalMultaJurosCarne(carne.getTotalMultaJurosCarne());
                dto.setTotalAtrasoCarne(carne.getTotalAtrasoCarne());
                dto.setSaldoDevedorCarne(carne.getSaldoDevedorCarne());
            } else {
                dto.setTotalMultaJurosCarne(BigDecimal.ZERO);
                dto.setTotalAtrasoCarne(BigDecimal.ZERO);
                dto.setSaldoDevedorCarne(BigDecimal.ZERO);
            }
        }
        return carnesCliente;
    }


    public Page<PagamentoDTO> paginationPagamentos(Long clienteId, PaginationFilterDTO<PagamentoDTO> paginationFilter) {
        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        PagamentoDTO filtros = paginationFilter.getFiltros();

        Page<PagamentoDTO> clientes;
        if(filtros.getDataInicial() != null) {
            clientes = getRepository().paginationPagamentos(
                    clienteId,
                    filtros.getLojaId(),
                    filtros.getDataInicial(),
                    filtros.getDataFinal() == null ? Calendar.getInstance() : filtros.getDataFinal(),
                    paging);
        } else {
            clientes = getRepository().paginationPagamentosSemDatas(
                    clienteId,
                    filtros.getLojaId(),
                    paging);
        }
        if(clientes.hasContent()) {
            return clientes;
        } else {
            return Page.empty();
        }
    }

    public Page<ParcelaClienteDTO> paginationParcelas(String cpf, PaginationFilterDTO<ParcelaClienteDTO> paginationFilter) {
        ClienteBean cliente = getRepository().findByCpf(StringUtils.desformataCpfCnpj(cpf)).orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
        
        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        ParcelaClienteDTO filtros = paginationFilter.getFiltros();

        EnumStatusParcela status = filtros.getStatus() != null ? EnumStatusParcela.valueOf(filtros.getStatus()) : null;
        Page<ParcelaClienteDTO> parcelas;
        if (filtros.getLojaId() == -1L) {
            filtros.setLojaId(null);
        }

        if (filtros.getEmpresaId() == -1L) {
            filtros.setEmpresaId(null);
        }

        if (filtros.getRedeId() == -1L) {
            filtros.setRedeId(null);
        }

        if(filtros.getDataInicial() != null) {
            parcelas = getRepository().paginationParcelas(
                    cliente.getId(),
                    filtros.getLojaId(),
                    status,
                    filtros.getDataInicial(),
                    filtros.getDataFinal() == null ? Calendar.getInstance() : filtros.getDataFinal(),
                    TipoFormaPagamento.CARNE,
                    paging);
        } else {
            parcelas = getRepository().paginationParcelasSemDatas(
                    cliente.getId(),
                    filtros.getLojaId(),
                    status,
                    TipoFormaPagamento.CARNE,
                    paging);
        }
        if(parcelas.hasContent()) {
            calculaMultaEJurosMoraParcelas(parcelas.getContent());
            return parcelas;
        } else {
            return Page.empty();
        }
    }

    public CarneClienteDTO calculaMultaEJurosMoraParcelas(List<ParcelaClienteDTO> parcelas) {
        CarneClienteDTO dto = new CarneClienteDTO();
        BigDecimal totalMulta = BigDecimal.ZERO;
        BigDecimal totalJurosMora = BigDecimal.ZERO;
        BigDecimal saldoDevedor = BigDecimal.ZERO;

        for(ParcelaClienteDTO parcelaDTO : parcelas) {
            if (parcelaDTO.getStatus().equalsIgnoreCase(EnumStatusParcela.NAO_PAGA.getLabel())) {
                long diasAtraso = DateUtils.diffDateInDays(DateUtils.getDiaMesAnoHoraMinutoSegundoPortugues(parcelaDTO.getDataVencimento() + " 00:00:00"), Calendar.getInstance());
                parcelaDTO.setValorPago(parcelaDTO.getValor());//o default do valor a ser pago é o próprio valor da parcela
                if(diasAtraso > 0 ) {
                   if (BigDecimal.ZERO.compareTo(parcelaDTO.getPeMulta()) != 0) {
                       parcelaDTO.setValorMulta(MathUtils.percentage(parcelaDTO.getValor(), parcelaDTO.getPeMulta()));
                       totalMulta = totalMulta.add(parcelaDTO.getValorMulta());
                   }
                   if (BigDecimal.ZERO.compareTo(parcelaDTO.getPeJurosMora()) != 0) {
                       parcelaDTO.setValorJurosMora(MathUtils.calculaJurosAoMes(diasAtraso, parcelaDTO.getValor(), parcelaDTO.getPeJurosMora()));
                       totalJurosMora = totalJurosMora.add(parcelaDTO.getValorJurosMora());
                   }
                   parcelaDTO.setValorPago(parcelaDTO.getValor().add(parcelaDTO.getValorMulta()).add(parcelaDTO.getValorJurosMora()));
                   saldoDevedor = saldoDevedor.add(parcelaDTO.getValorPago());
                }
            }
        }

        dto.setTotalAtrasoCarne(totalMulta);
        dto.setTotalMultaJurosCarne(totalJurosMora);
        dto.setSaldoDevedorCarne(saldoDevedor);
        return dto;
    }

    public ClienteBean findByCpf(String cpf) {
        return getRepository().findByCpf(cpf).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public ResumoClienteDTO resumoCliente(Long clienteId, Long lojaId) {

        ResumoClienteDTO resumoClienteDTO = clienteRepository.getResumoCliente(clienteId, lojaId);
        resumoClienteDTO.setClienteId(clienteId);
        resumoClienteDTO.setLojaId(lojaId);
        resumoClienteDTO.setLimiteCompartilhadoDisponivel(getLimiteCompartilhadoDisponivel(clienteId));
        resumoClienteDTO.setLimiteExclusivoDisponivel(getLimiteExclusivoDisponivel(clienteId, lojaId));
        resumoClienteDTO.setLimiteExclusivo(getLimiteExclusivoCliente(clienteId, lojaId));

        return resumoClienteDTO;
    }

    public List<ResumoClienteDTO> resumoClienteOutrasLojas(Long clienteId, Long lojaId) {

        List<ClienteLojaBean> outrasLojasCliente = clienteLojaService.findOutrasLojasCliente(clienteId, lojaId);
        List<ResumoClienteDTO> listaResumoOutrasLojas = new ArrayList<>();
        if(outrasLojasCliente != null && !outrasLojasCliente.isEmpty()) {
            for(ClienteLojaBean clienteLojaBean : outrasLojasCliente) {
                ResumoClienteDTO resumoClienteDTO = (ResumoClienteDTO) this.resumoCliente(clienteId, clienteLojaBean.getLoja().getId());
                listaResumoOutrasLojas.add(resumoClienteDTO);
            }
        }
        return listaResumoOutrasLojas;
    }

    public Integer getQtdPagamentos(Long clienteId) {
        return getRepository().getQtdPagamentos(clienteId);
    }

    public Integer getMaiorAtrasNosUltimosNPagementos(Long clienteId, Integer ultimosPagamentos) {
        return clienteRepository.getMaiorAtrasNosUltimosNPagementos(clienteId, ultimosPagamentos);
    }

    public VendaBean getUltimaCompra(Long clienteId) {
        return clienteRepository.getUltimaCompra(clienteId);
    }

    public boolean existClienteCpf(String cpf) {
        return getRepository().existClienteCpf(cpf);
    }

    public ClienteBean getClienteByCpf(String cpf) {
        return getRepository().getClienteByCpf(cpf);
    }

    public boolean verificaCliente(Long clienteId, Long lojaId) {

        Integer ano = Integer.parseInt(DateUtils.getDiaMesAno(Calendar.getInstance()).substring(0,4));
        ano--;
        Calendar dataInicial = DateUtils.getDiaMesAno(ano+"-"+01+"-"+01);
        Calendar dataFinal = DateUtils.getDiaMesAno(ano+"-"+12+"-"+31);

        return parcelaRepository.existParcelaNaoPagaAno(clienteId, lojaId, dataInicial, dataFinal);
    }

    public boolean verificaStatus (EnumStatusCliente status) {
        if (status.equals(EnumStatusCliente.CANCELADO_PEDIDO_ADMINISTRADORA) ||
                status.equals(EnumStatusCliente.CANCELADO_PEDIDO_CLIENTE) ||
                status.equals(EnumStatusCliente.CANCELADO_FALECIMENTO) ||
                status.equals(EnumStatusCliente.CANCELADO_COBRANCA_JUDICIAL) ||
                status.equals(EnumStatusCliente.CANCELADO_CREDITO_LIQUIDACAO) ||
                status.equals(EnumStatusCliente.CANCELADO_MIGRACAO_DE_CONTA) ||
                status.equals(EnumStatusCliente.CANCELADO_SUSPEITA_FRAUDE) ||
                status.equals(EnumStatusCliente.CANCELADO_PEDIDO_CLIENTE) ||
                status.equals(EnumStatusCliente.CANCELADO_OUTROS_MOTIVOS)) {
            return true;
        }
        return false;
    }
}
