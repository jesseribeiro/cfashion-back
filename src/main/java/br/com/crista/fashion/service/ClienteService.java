package br.com.crista.fashion.service;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.crista.fashion.bean.ClienteBean;
import br.com.crista.fashion.dto.ClienteDTO;
import br.com.crista.fashion.dto.PaginationFilterDTO;
import br.com.crista.fashion.dto.ParcelaDTO;
import br.com.crista.fashion.dto.VendaDTO;
import br.com.crista.fashion.enumeration.EnumStatus;
import br.com.crista.fashion.repository.ClienteRepository;
import br.com.crista.fashion.repository.impl.VendaRepositoryImpl;
import br.com.crista.fashion.utils.StringUtils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClienteService extends GenericService<ClienteBean, ClienteRepository> {

    private final @NonNull
    CidadeService cidadeService;

    private final @NonNull
    VendaRepositoryImpl vendaRepository;

    public ClienteDTO salvar(ClienteDTO dto) {

        ClienteBean cliente = new ClienteBean();
        cliente = dto.converter(cliente);
        preencheCidadeEndereco(cliente);

        save(cliente);

        return new ClienteDTO(cliente);
    }

    private void preencheCidadeEndereco(ClienteBean cliente) {

        cliente.getEndereco().setCidadeNome(cidadeService.getCidadeNomeByCidadeIbge(cliente.getEndereco().getCidadeIbge()));
    }

    public Page<ClienteDTO> pagination(PaginationFilterDTO<ClienteDTO> paginationFilter) {

        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        ClienteDTO filtros = paginationFilter.getFiltros();

        String cpf = isNull(filtros.getCpf()) ? "" : filtros.getCpf();

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

        return new ClienteDTO(cliente);
    }

    public ResponseEntity delete(Long id) {

        ClienteBean cliente = getRepository().findById(id).get();
        delete(cliente);

        return ResponseEntity.ok("Sucesso");
    }

    public List<VendaDTO> getVendasCliente(Long clienteId, VendaDTO filtro) {

        EnumStatus status = nonNull(filtro.getStatus()) ? EnumStatus.valueOf(filtro.getStatus()) : null;

        return vendaRepository.findVendasCliente(clienteId, filtro.getDataInicial(),
                filtro.getDataFinal(), status);
    }

    public Page<ParcelaDTO> paginationPagamentos(Long clienteId, PaginationFilterDTO<ParcelaDTO> paginationFilter) {

        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        ParcelaDTO filtros = paginationFilter.getFiltros();

        Page<ParcelaDTO> clientes;

        if (nonNull(filtros.getDataInicial())) {

            clientes = getRepository().paginationPagamentos(
                    clienteId,
                    filtros.getDataInicial(),
                    isNull(filtros.getDataFinal()) ? Calendar.getInstance() : filtros.getDataFinal(),
                    paging);
        } else {

            clientes = getRepository().paginationPagamentosSemDatas(
                    clienteId,
                    paging);
        }

        if (clientes.hasContent()) {

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

        if (isNull(clienteBase)) {

            return dto;
        } else {

            return new ClienteDTO(clienteBase);
        }
    }
}
