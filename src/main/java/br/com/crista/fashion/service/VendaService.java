package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.*;
import br.com.crista.fashion.dto.*;
import br.com.crista.fashion.enumeration.*;
import br.com.crista.fashion.exception.OperacaoNaoPermitidaException;
import br.com.crista.fashion.repository.CidadeRepository;
import br.com.crista.fashion.repository.LojaRepository;
import br.com.crista.fashion.repository.ProdutoRepository;
import br.com.crista.fashion.repository.VendaRepository;
import br.com.crista.fashion.repository.impl.VendaRepositoryImpl;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class VendaService extends GenericService<VendaBean, VendaRepository> {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private LojaService lojaService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private CarneService carneService;

    @Autowired
    private ParcelaService parcelaService;

    @Autowired
    private LimiteExclusivoService limiteExclusivoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AutorizacaoService autorizacaoService;

    @Autowired
    private ClienteLojaService clienteLojaService;

    @Autowired
    private VendaRepositoryImpl vendaRepositoryImpl;

    public List<ParcelaCompraDTO> calcularVenda(CalcularVendaDTO dto) {
        // O que quer dizer primeira compra?
        // R: É primeira compra na rede Pratico o
        // vefiricar se é a primeira compra para utilizar os limites pré aprovados inicial
        // R: Sim, mas isso vai ter q vir la da regra financeira dos campos de concessao de credito
        /**
         * limite této para compras
         *
         * Limite compartilhado por todas as lojas único
         * Limite exclusivo para uma loja especifica (N)
         *
         * O cliente poderá ter dois tipos de limite:
         *  - Limite Compartilhado
         *  - Limite Exclusivo ( serão limites exclusivos por lojas)
         */

        Boolean permitir = false;
        if (dto.getFlagAutorizacao()) {
            permitir = true;
        }

        ClienteBean cliente = clienteService.getById(dto.getClienteId());
        LojaBean loja = lojaService.getLojaById(dto.getLojaId());

        BigDecimal peEntrada = null;

        BigDecimal limiteDisponivel = clienteService.getLimiteDisponivelParaCompra(dto.getClienteId(), dto.getLojaId());

        BigDecimal valorEntrada = BigDecimal.ZERO; // scoreClienteService.calculaValorEntrada(dto.getVlProduto(), regraGrupoLimiteCreditoDTO);
        if(peEntrada != null && peEntrada.compareTo(BigDecimal.ZERO) > 0) {
            valorEntrada = MathUtils.percentage(dto.getVlProduto(), peEntrada);
        }

        if (getUsuarioLogado().getRoleAtiva() != EnumRole.ADMIN) {
            if (dto.getVlEntrada() != null && dto.getVlEntrada().compareTo(BigDecimal.ZERO) > 0) {
                if (valorEntrada.compareTo(dto.getVlEntrada()) == 1) {
                    dto.setVlEntrada(valorEntrada);
                    if (!permitir) {
                        throw new OperacaoNaoPermitidaException("Valor de entrada não pode ser menor do que R$ " + valorEntrada);
                    }
                } else if (dto.getVlEntrada().compareTo(valorEntrada) == 1) {
                    valorEntrada = dto.getVlEntrada();
                }
            }
        } else if (dto.getVlEntrada() != null) {
            valorEntrada = dto.getVlEntrada();
        }

        BigDecimal tarifaParcela = null;
        List<ParcelaCompraDTO> parcelas = new ArrayList<>();
        ParcelaCompraDTO parcelaCompraDTO;
        return parcelas;
    }

    @Transactional
    public VendaDTO vender(CalcularVendaDTO dto) throws IOException {

        List<ParcelaCompraDTO> listaParcela = calcularVenda(dto);
        ParcelaCompraDTO parcelaSelecionada = getParcelaSelecionadaCliente(listaParcela, dto.getParcelaSelecionada());

        ClienteBean cliente = clienteService.getById(dto.getClienteId());
        LojaBean loja = lojaService.getLojaById(dto.getLojaId());
        ProdutoBean produtoBean = findOrCreateProduto(dto.getNomeProduto());

        // Persistir a Venda
        VendaBean venda = new VendaBean();
        venda.setCliente(cliente);
        venda.setLoja(loja);
        venda.setValorProduto(dto.getVlProduto());
        venda.setDataVenda(Calendar.getInstance());
        venda.setProduto(produtoBean);
        venda.setVendedor(getUsuarioLogado());
        venda.setDataCorte(DateUtils.getDataDeCorte(Calendar.getInstance()));
        venda.setStatus(EnumStatusVenda.AUTORIZADA);
        if(loja.getIsPossuiLimiteExclusivo()) {
            venda.setLimiteExclusivo(limiteExclusivoService.getLimiteClienteByLoja(cliente.getId(), loja.getId()));
        }
        save(venda);

        // Persistir o CarneCompra
        CarneBean carneCompra = new CarneBean();
        carneCompra.setTipoCarne(TipoCarne.CARNE_COMPRA);
        carneCompra.setDataCompra(Calendar.getInstance());
        carneCompra.setQtdParcela(parcelaSelecionada.getQtdParcela());
        carneCompra.setStatus(EnumStatusCarne.EM_ABERTO);

        carneCompra.setValorTotal(parcelaSelecionada.getValorTotal());
        carneCompra.setValorEntrada(parcelaSelecionada.getVlEntrada());
        if(dto.getVlEntrada() != null && dto.getVlEntrada().compareTo(BigDecimal.ZERO) > 0) {
            carneCompra.setValorEntrada(dto.getVlEntrada());
        }
        carneCompra.setVenda(venda);
        carneCompra.setFormaPagamento(TipoFormaPagamento.valueOf(dto.getFormaPagamento()));
        carneService.save(carneCompra);

        Integer diasPrimeiroVencimento;
        if (dto.getDiasPrimeiraParcela() != null) {
            diasPrimeiroVencimento = dto.getDiasPrimeiraParcela();
        } else {
            diasPrimeiroVencimento = null;

            if (loja.getCampanhaAtiva()) {
                Long qtdVendas = lojaRepository.qtdVendasLojaMes(loja.getId());
                if (qtdVendas < loja.getCampanhaQtdVendas()) {
                    diasPrimeiroVencimento = Math.toIntExact((loja.getCampanhaQtdDiasPrimeiraParcela()));
                }
            }
        }
        Integer somaDiasParcelas = diasPrimeiroVencimento;
        Integer diasParaRepasse = null;
        boolean flgPrimeiraParcela = true;
        String dataVencimentoMsg = DateUtils.getDiaMesAnoPortugues(DateUtils.geraDataVencimento(somaDiasParcelas));
        Calendar dataRepasse = DateUtils.getDataRepasse(venda.getDataCorte(), diasParaRepasse);

        Date dataParcela = DateUtils.convertStringInDate(dataVencimentoMsg);
        Integer mesParcela = 1;

        for(int x = 0; x < parcelaSelecionada.getQtdParcela(); x++) {
            ParcelaBean parcela = new ParcelaBean();
            parcela.setNumero(Constants.PRIMEIRA_PARCELA + x);
            if(flgPrimeiraParcela) {
                parcela.setValor(parcelaSelecionada.getVlPrimeiraParcela());
                parcela.setDataVencimento(DateUtils.geraDataVencimento(somaDiasParcelas));
            } else {
                parcela.setValor(parcelaSelecionada.getVlDemaisParcela());
                parcela.setDataVencimento(DateUtils.proximoMes(dataParcela, mesParcela));
                mesParcela += 1;
            }
            parcela.setValorRepasse(parcelaSelecionada.getVlParcelaSemTaxaJuros());
            parcela.setStatus(EnumStatusParcela.NAO_PAGA);
            parcela.setCarne(carneCompra);
            parcela.setVlJuros(parcelaSelecionada.getJurosFinanc());
            parcela.setVlTarifa(parcelaSelecionada.getTarifa());
            parcela.setVlParcelaSemTaxaJuros(parcelaSelecionada.getVlParcelaSemTaxaJuros());
            parcela.setDataParaRepasse(dataRepasse);
            parcelaService.save(parcela);
            flgPrimeiraParcela = false;
        }

        parcelaService.persistParcelaEntrada(parcelaSelecionada.getVlEntrada(), Calendar.getInstance(), carneCompra, loja, true, EnumTipoPagamento.LOJA, dataRepasse);

        VendaDTO vendaDTO = new VendaDTO(venda, carneCompra.getId());
        return vendaDTO;
    }

    //
    @Transactional
    public void cancelarVenda(Long vendaId) {

       if (getUsuarioLogado().getRoleAtiva() == EnumRole.CREDIARISTA || getUsuarioLogado().getRoleAtiva() == EnumRole.SUPERVISOR ||
                getUsuarioLogado().getRoleAtiva() == EnumRole.ADMIN || getUsuarioLogado().getRoleAtiva() == EnumRole.SUPERVISOR) {

            VendaBean venda = getById(vendaId);
            if (!DateUtils.equalsDate(venda.getDataVenda(), Calendar.getInstance()) &&
                    getUsuarioLogado().getRoleAtiva() != EnumRole.ADMIN &&
                    getUsuarioLogado().getRoleAtiva() != EnumRole.SUPERVISOR) {
                throw new RuntimeException("Venda não pode ser cancelada. Não é permitido cancelar venda realizadas em outro dia.");
            }

            venda.setStatus(EnumStatusVenda.CANCELADA);
            venda.setUsuarioExcluiu(getUsuarioLogado());
            update(venda);

            CarneBean carne = carneService.getByVendaId(vendaId);
            carne.setStatus(EnumStatusCarne.CANCELADO);
            carne.setUsuarioExcluiu(getUsuarioLogado());
            carneService.update(carne);

            List<ParcelaBean> parcelas = parcelaService.findParcelasByCarneId(carne.getId());

            for (ParcelaBean parcela : parcelas) {
                parcela.setStatus(EnumStatusParcela.CANCELADA);
                parcelaService.update(parcela);
                if (parcela.getPagamento() != null) {
                    parcela.getPagamento().setFlgCancelado(true);
                    pagamentoService.update(parcela.getPagamento());
                }
            }
        }
    }

    //
    private ParcelaCompraDTO getParcelaSelecionadaCliente(List<ParcelaCompraDTO> listaParcela, ParcelaCompraDTO parcelaSelecionada) {

        for (ParcelaCompraDTO dto: listaParcela) {
            if(dto.getQtdParcela() == parcelaSelecionada.getQtdParcela()){
                return dto;
            }
        }
        return null;
    }

    //
    public ProdutoBean findOrCreateProduto(String nome) {

        ProdutoBean produtoBean = produtoRepository.findByNome(nome);
        if(produtoBean == null) {
            produtoBean = new ProdutoBean();
            produtoBean.setNome(nome);
            produtoRepository.save(produtoBean);
        }
        return produtoBean;
    }

    public void pagarParcela(ParcelaBean parcelaBean, LojaBean loja, BigDecimal valorPago, BigDecimal multa,
                             BigDecimal jurosMora, BigDecimal desconto, TipoFormaPagamento tipoFormaPagamento, EnumTipoPagamento tipoPagamento, EnumBanco banco) {
        parcelaService.pagarParcela(parcelaBean, loja, valorPago, multa, jurosMora, desconto, tipoFormaPagamento, tipoPagamento, banco, Calendar.getInstance());
    }

    public void pagarCarne(ParcelaClienteDTO dto) {

        if (dto.getValorPago().compareTo(dto.getValor()) == -1) {
            throw new RuntimeException("Valor pago nao pode ser menor que o valor da parcela " + dto.getValor());
        }
        ParcelaBean parcela = parcelaService.getById(dto.getId());

        LojaBean loja = lojaService.getLojaById(dto.getLojaId());

        EnumTipoPagamento tipoPagamento = EnumTipoPagamento.LOJA;
        EnumBanco banco = null;
        if (dto.getTipoPagamento() == EnumTipoPagamento.ADMINISTRADORA){
            tipoPagamento = EnumTipoPagamento.ADMINISTRADORA;
        } else if (dto.getTipoPagamento() == EnumTipoPagamento.CARTAO_CREDITO){
            tipoPagamento = EnumTipoPagamento.CARTAO_CREDITO;
        } else if (dto.getTipoPagamento() == EnumTipoPagamento.TED){
            tipoPagamento = EnumTipoPagamento.TED;
        } else if (dto.getTipoPagamento() == EnumTipoPagamento.PIX){
            tipoPagamento = EnumTipoPagamento.PIX;
        } else if (dto.getTipoPagamento() == EnumTipoPagamento.BANCO){
            tipoPagamento = EnumTipoPagamento.BANCO;
        }

        pagarParcela(parcela, loja, dto.getValorPago(), dto.getValorMulta(), dto.getValorJurosMora(), dto.getDesconto(), TipoFormaPagamento.CARNE, tipoPagamento, banco);
    }

    public void pagarListaCarne(List<ParcelaClienteDTO> parcelas) {

        for (ParcelaClienteDTO dto : parcelas) {
            ParcelaBean parcela = parcelaService.getById(dto.getId());

            LojaBean loja = lojaService.getLojaById(dto.getLojaId());

            pagarParcela(parcela, loja, dto.getValorPago(), dto.getValorMulta(), dto.getValorJurosMora(), dto.getDesconto(), TipoFormaPagamento.CARNE, EnumTipoPagamento.LOJA, null);
        }
    }

    public void cancelarPagamento(CancelarPagamentoDTO dto) {

        PagamentoBean pagamento = pagamentoService.getById(dto.getId());

        if (!DateUtils.equalsDate(pagamento.getDataPagto(), Calendar.getInstance()) &&
                getUsuarioLogado().getRoleAtiva() != EnumRole.CREDIARISTA &&
                getUsuarioLogado().getRoleAtiva() != EnumRole.ADMIN &&
                getUsuarioLogado().getRoleAtiva() != EnumRole.SUPERVISOR) {
            throw new RuntimeException("Pagamento não pode ser cancelado, não é permitido cancelar pagamento realizados em outro dia.");
        }

        if (pagamento.getLoja() == null) {
            LojaBean lojaBean = lojaService.getLojaById(dto.getLojaId());
            pagamento.setLoja(lojaBean);
        }

        if (dto.getLojaId() != null && pagamento.getLoja().getId().longValue() != dto.getLojaId().longValue()) {
            throw new RuntimeException("Pagamento só pode ser cancelado, na mesma loja que foi feito o pagamento!");
        }

        UsuarioBean usuarioCancelamento = usuarioService.getById(dto.getUsuarioId());

        ParcelaBean parcela = pagamento.getParcela();
        parcela.setStatus(EnumStatusParcela.CANCELADA);
        parcelaService.update(parcela);

        ParcelaBean parcelaClone = new ParcelaBean();
        parcelaClone.setStatus(EnumStatusParcela.NAO_PAGA);
        parcelaClone.setDataVencimento(parcela.getDataVencimento());
        parcelaClone.setNumero(parcela.getNumero());
        parcelaClone.setCarne(parcela.getCarne());
        parcelaClone.setValor(parcela.getValor());
        parcelaClone.setPagamento(null);
        parcelaService.save(parcelaClone);

        CarneBean carneCompra = parcela.getCarne();
        if( carneCompra != null && carneCompra.getStatus().equals(EnumStatusCarne.PAGO)) {
            carneCompra.setStatus(EnumStatusCarne.EM_ABERTO);
            carneService.update(carneCompra);
        }

        pagamento.setFlgCancelado(true);
        pagamento.setDataCancelamento(Calendar.getInstance());
        pagamento.setMotivoCancelamento(dto.getMotivo());
        pagamento.setUsuarioCancelamento(usuarioCancelamento);
        pagamento.setLojaCancelamento(pagamento.getLoja());
        pagamentoService.update(pagamento);
    }

    //
    public ReciboPagamentoDTO getReciboPagamento(Long pagamentoId) {

        PagamentoBean pagamento = pagamentoService.getById(pagamentoId);

        ReciboPagamentoDTO dto = new ReciboPagamentoDTO();
        dto.setPagamentoId(pagamento.getId());
        dto.setValorPago(pagamento.getValorPago());
        dto.setDataPagto(DateUtils.getDiaMesAnoPortugues(pagamento.getDataPagto()));
        dto.setVencimento(pagamento.getParcela().getDataVencimento());
        dto.setNumeroParcela(pagamento.getParcela().getNumero() + "/" +pagamento.getParcela().getCarne().getQtdParcela());
        dto.setFlgCancelado(pagamento.getFlgCancelado() != null ? pagamento.getFlgCancelado() : false);
        dto.setDadosCliente(pagamento.getParcela().getCarne().getVenda().getCliente());

        if(pagamento.getParcela().getCarne().getVenda().getLoja().getEndereco() != null &&
                pagamento.getParcela().getCarne().getVenda().getLoja().getEndereco().getEndereco() != null) {
            CidadeBean cidade = cidadeRepository.findByIbge(pagamento.getParcela().getCarne().getVenda().getLoja().getEndereco().getEndereco().getCidadeIbge());
            dto.setNomeCidade(cidade.getNome());
        }
        dto.setDadosLoja(pagamento.getParcela().getCarne().getVenda().getLoja());
        return dto;
    }

    //
    public ReciboVendaDTO getReciboVenda(Long vendaId) {

        VendaBean venda = getById(vendaId);

        CarneBean carne = carneService.getByVendaId(vendaId);

        List<ParcelaBean> parcelas = parcelaService.findParcelasByCarneId(carne.getId());

        ReciboVendaDTO dto = new ReciboVendaDTO();
        dto.setVendaId(vendaId);
        dto.setDataVenda(venda.getDataCadastro());
        dto.setCodigoAutorizacao("");
        dto.setTipoAutorizacao("");
        dto.setDadosCliente(venda.getCliente());
        dto.setValorVenda(carne.getValorTotal());
        dto.setParcelas(parcelas);
        dto.setValorEntrada(carne.getValorEntrada());
        if(venda.getLoja().getEndereco() != null &&
            venda.getLoja().getEndereco().getEndereco() != null) {
            CidadeBean cidade = cidadeRepository.findByIbge(venda.getLoja().getEndereco().getEndereco().getCidadeIbge());
            dto.setNomeCidade(cidade.getNome());
        }
        dto.setDadosLoja(venda.getLoja());
        dto.setQtdParcela(carne.getQtdParcela());
        dto.setFlgCancelado(carne.getStatus().equals(EnumStatusCarne.CANCELADO));
        return dto;
    }

    public Page<AutorizacaoDTO> paginationAutorizacoes(PaginationFilterDTO<AutorizacaoDTO> paginationFilter) {
        Pageable paging = PageRequest.of(paginationFilter.getPageNo(), paginationFilter.getPageSize(), Sort.by(paginationFilter.getSortBy()));
        AutorizacaoDTO filtros = paginationFilter.getFiltros();

        EnumStatusVenda enumStatusVenda = filtros.getSituacao() != null ? EnumStatusVenda.valueOf(filtros.getSituacao()) : null;
        Long clienteId = null;
        if(filtros.getCpf() != null && !filtros.getCpf().isEmpty()) {
            ClienteBean cliente = clienteService.findByCpf(StringUtils.desformataCpfCnpj(filtros.getCpf()));
            clienteId = cliente.getId();
        }
        Page<AutorizacaoDTO> autorizacoes;
        Calendar dataInicial = null;
        Calendar dataFinal = null;
        if(filtros.getDataInicial() != null && !filtros.getDataInicial().isEmpty()) {
            dataInicial = DateUtils.getDiaMesAno(filtros.getDataInicial());
        }
        if(filtros.getDataFinal() != null && !filtros.getDataFinal().isEmpty()) {
            dataFinal = DateUtils.getDiaMesAno(filtros.getDataFinal());
        }

        autorizacoes = vendaRepositoryImpl.paginationAutorizacoes(
                filtros.getRedeId(),
                filtros.getEmpresaId(),
                filtros.getLojaId(),
                clienteId,
                dataInicial,
                dataFinal,
                enumStatusVenda,
                paging);

        if(autorizacoes.hasContent()) {
            return autorizacoes;
        } else {
            return Page.empty();
        }
    }

    /**
     * Esse método foi criado para resolver um erro de venda que deveria ser feito no sistema da Trulogic
     * mas foi feito no sistema da Algorix.
     * Nunca utilizar esse método
     * @param dto
     * @return
     * @throws IOException
     */
    @Transactional
    public VendaDTO vendaNaMao(VendaNaMaoDTO dto) throws IOException {
        ClienteBean cliente = clienteService.getById(dto.getClienteId());
        LojaBean loja = lojaService.getLojaById(dto.getLojaId());
        ProdutoBean produtoBean = findOrCreateProduto(dto.getNomeProduto());

        Calendar dataVenda = DateUtils.getDiaMesAno(dto.getDataVenda());
        // Persistir a Venda
        VendaBean venda = new VendaBean();
        venda.setCliente(cliente);
        venda.setLoja(loja);
        venda.setValorProduto(dto.getVlProduto());
        venda.setDataVenda(dataVenda);
        venda.setProduto(produtoBean);

        venda.setVendedor(getUsuarioLogado());
        venda.setDataCorte(DateUtils.getDataDeCorte(dataVenda));
        venda.setStatus(EnumStatusVenda.AUTORIZADA);
        if(loja.getIsPossuiLimiteExclusivo()) {
            venda.setLimiteExclusivo(limiteExclusivoService.getLimiteClienteByLoja(cliente.getId(), loja.getId()));
        }
        save(venda);

        // Persistir o CarneCompra
        CarneBean carneCompra = new CarneBean();
        carneCompra.setTipoCarne(TipoCarne.CARNE_COMPRA);
        carneCompra.setDataCompra(dataVenda);
        carneCompra.setQtdParcela(dto.getQtdParcela());
        carneCompra.setStatus(EnumStatusCarne.EM_ABERTO);

        carneCompra.setValorTotal(dto.getValorTotal());
        carneCompra.setValorEntrada(dto.getVlEntrada());
        if(dto.getVlEntrada() != null && dto.getVlEntrada().compareTo(BigDecimal.ZERO) > 0) {
            carneCompra.setValorEntrada(dto.getVlEntrada());
        }
        carneCompra.setVenda(venda);
        carneCompra.setFormaPagamento(TipoFormaPagamento.BOLETO);
        carneService.save(carneCompra);

        Integer diasParaRepasse = null;
        boolean flgPrimeiraParcela = true;
        Calendar dataRepasse = DateUtils.getDataRepasse(venda.getDataCorte(), diasParaRepasse);

        Date dataParcela = DateUtils.getDiaMesAno(dto.getDataVencimento()).getTime();
        Integer mesParcela = 1;

        for(int x = 0; x < dto.getQtdParcela(); x++) {
            ParcelaBean parcela = new ParcelaBean();
            parcela.setNumero(Constants.PRIMEIRA_PARCELA + x);
            parcela.setValor(dto.getVlParcela());
            if(flgPrimeiraParcela) {
                parcela.setDataVencimento(DateUtils.getDiaMesAno(dto.getDataVencimento()));
            } else {
                parcela.setDataVencimento(DateUtils.proximoMes(dataParcela, mesParcela));
                mesParcela += 1;
            }
            parcela.setValorRepasse(dto.getVlParcelaSemTaxaJuros());
            parcela.setStatus(EnumStatusParcela.NAO_PAGA);
            parcela.setCarne(carneCompra);
            parcela.setDataParaRepasse(dataRepasse);
            parcelaService.save(parcela);
            flgPrimeiraParcela = false;
        }

        parcelaService.persistParcelaEntrada(dto.getVlEntrada(), Calendar.getInstance(), carneCompra, loja, true, EnumTipoPagamento.LOJA, dataRepasse);

        VendaDTO vendaDTO = new VendaDTO(venda, carneCompra.getId());
        return vendaDTO;
    }
}
