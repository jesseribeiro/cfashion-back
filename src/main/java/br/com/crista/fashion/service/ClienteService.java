package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.bean.VendaBean;
import br.com.crista.fashion.config.CentralConfig;
import br.com.crista.fashion.dto.ClienteDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.repository.ClienteRepository;
import br.com.crista.fashion.repository.ParcelaRepository;
import br.com.crista.fashion.repository.UsuarioRepository;
import br.com.crista.fashion.repository.impl.ClienteRepositoryImpl;
import br.com.crista.fashion.utils.Constants;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
public class ClienteService extends GenericService<ClienteBean, ClienteRepository> {

    @Autowired
    private LojaService lojaService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CentralConfig centralConfig;

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

    public ClienteDTO salvar(ClienteDTO dto) {

        ClienteBean cliente = new ClienteBean();
        cliente = dto.converter(cliente);
        preencheCidadeEndereco(cliente);

        save(cliente);

        ClienteDTO clienteDTORetorno = new ClienteDTO(cliente);
        return clienteDTORetorno;
    }

    private void preencheCidadeEndereco(ClienteBean cliente) {
        cliente.getEndereco().setCidadeNome(cidadeService.getCidadeNomeByCidadeIbge(cliente.getEndereco().getCidadeIbge()));
    }

    public Page<ClienteDTO> pagination(PaginationFilterDTO<ClienteDTO> paginationFilter) {
        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        ClienteDTO filtros = paginationFilter.getFiltros();

        String cpf = filtros.getCpf() == null ? "" : filtros.getCpf();
        Page<ClienteDTO> clientes = getRepository().pagination(
                filtros.getNome(),
                StringUtils.desformataCpfCnpj(cpf),
                paging);
        if (clientes.hasContent()) {
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
        ClienteBean cliente = getRepository().findById(id).get();
        cliente = dto.converter(cliente);
        preencheCidadeEndereco(cliente);

        update(cliente);

        ClienteDTO clienteDTORetorno = new ClienteDTO(cliente);
        return clienteDTORetorno;
    }

    public ResponseEntity delete(Long id) {
        return null;
    }

/*
    public List<CarneClienteDTO> getCarnesCliente(Long clienteId, CarneClienteDTO filtro) {

        Boolean somenteCarne = false;

        Comentado por enquanto, pq os lojistas tbem precisam ver os boletos
        if (getUsuarioLogado().getRoleAtiva() == EnumRole.CREDIARISTA ||
                getUsuarioLogado().getRoleAtiva() == EnumRole.PROPRIETARIO) {
            somenteCarne = true;
        }
        EnumStatusCarne status = filtro.getStatus() != null ? EnumStatusCarne.valueOf(filtro.getStatus()) : null;
        List<CarneClienteDTO> carnesCliente = carneRepository.findCarnesCliente(clienteId, filtro.getLojaId(), filtro.getDataInicial(),
                filtro.getDataFinal(), status, somenteCarne);

        for(CarneClienteDTO dto : carnesCliente) {
            if(dto.getParcelas() != null && !dto.getParcelas().isEmpty()) {

            } else {
                dto.setTotalMultaJurosCarne(BigDecimal.ZERO);
                dto.setTotalAtrasoCarne(BigDecimal.ZERO);
                dto.setSaldoDevedorCarne(BigDecimal.ZERO);
            }
        }
        return carnesCliente;
    }
*/

    /*
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

     */

    public ClienteBean findByCpf(String cpf) {
        return getRepository().findByCpf(cpf).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
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

        return false;
    }

    public ClienteDTO consultaCPF(ClienteDTO dto) {
        ClienteBean clienteBase = getRepository().findByCpf(StringUtils.desformataCpfCnpj(dto.getCpf())).orElse(null);

        if (clienteBase == null) {
            return dto;
        } else {
            ClienteDTO clienteDTO = new ClienteDTO(clienteBase);
            return clienteDTO;
        }
    }
}
