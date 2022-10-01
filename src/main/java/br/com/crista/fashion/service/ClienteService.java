package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.dto.ClienteDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.ParcelaDTO;
import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.enumeration.EnumStatus;
import br.com.crista.fashion.repository.ClienteRepository;
import br.com.crista.fashion.repository.impl.VendaRepositoryImpl;
import br.com.crista.fashion.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
public class ClienteService extends GenericService<ClienteBean, ClienteRepository> {

    @Autowired
    CidadeService cidadeService;

    @Autowired
    VendaRepositoryImpl vendaRepository;

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
        ClienteBean cliente = getRepository().findById(id).get();
        delete(cliente);
        return ResponseEntity.ok("Sucesso");
    }

    public List<VendaDTO> getVendasCliente(Long clienteId, VendaDTO filtro) {

        EnumStatus status = filtro.getStatus() != null ? EnumStatus.valueOf(filtro.getStatus()) : null;
        List<VendaDTO> vendas = vendaRepository.findVendasCliente(clienteId, filtro.getDataInicial(),
                filtro.getDataFinal(), status);

        return vendas;
    }

    public Page<ParcelaDTO> paginationPagamentos(Long clienteId, PaginationFilterDTO<ParcelaDTO> paginationFilter) {
        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        ParcelaDTO filtros = paginationFilter.getFiltros();

        Page<ParcelaDTO> clientes;
        if(filtros.getDataInicial() != null) {
            clientes = getRepository().paginationPagamentos(
                    clienteId,
                    filtros.getDataInicial(),
                    filtros.getDataFinal() == null ? Calendar.getInstance() : filtros.getDataFinal(),
                    paging);
        } else {
            clientes = getRepository().paginationPagamentosSemDatas(
                    clienteId,
                    paging);
        }
        if(clientes.hasContent()) {
            return clientes;
        } else {
            return Page.empty();
        }
    }

    public ClienteBean findByCpf(String cpf) {
        return getRepository().findByCpf(cpf).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
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
