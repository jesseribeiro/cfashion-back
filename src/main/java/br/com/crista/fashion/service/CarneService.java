package br.com.crista.fashion.service;

import br.com.crista.fashion.bean.*;
import br.com.crista.fashion.config.CentralConfig;
import br.com.crista.fashion.dto.CarneReportDTO;
import br.com.crista.fashion.dto.ParcelaReportDTO;
import br.com.crista.fashion.enumeration.EnumStatusCarne;
import br.com.crista.fashion.enumeration.EnumStatusParcela;
import br.com.crista.fashion.report.carne.ImpressaoCarnePDF;
import br.com.crista.fashion.repository.CarneRepository;
import br.com.crista.fashion.repository.CidadeRepository;
import br.com.crista.fashion.utils.Constants;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.FileUtils;
import br.com.crista.fashion.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class CarneService extends GenericService<CarneBean, CarneRepository> {

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    ParcelaService parcelaService;

    @Autowired
    CidadeService cidadeService;

    @Autowired
    LojaService lojaService;

    @Autowired
    CentralConfig centralConfig;

    @Autowired
    VendaService vendaService;

    @Autowired
    AutorizacaoService autorizacaoService;

    public CarneBean getByVendaId(Long vendaId) {
        return getRepository().findByVendaId(vendaId);
    }

    private ResponseEntity<Resource> geraCarnePDF(CarneBean carne, ClienteBean cliente, LojaBean loja, String nomeVendedor) throws IOException {
        try {
            BigDecimal valorParcela = BigDecimal.ZERO;
            VendaBean venda = null;
            AutorizacaoBean autorizacao = null;

            if (carne.getVenda() != null){
                venda = vendaService.getById(carne.getVenda().getId());
                autorizacao = autorizacaoService.getRepository().autorizacaoVenda(venda.getId());
            }

            List<ParcelaBean> parcelasCarne = parcelaService.findParcelasByCarneId(carne.getId());
            CarneReportDTO dto = new CarneReportDTO();
            List<ParcelaReportDTO> parcelas = new ArrayList<>();
            for(ParcelaBean parcelaBean : parcelasCarne) {
                if(parcelaBean.getNumero() != Constants.PARCELA_ENTRADA && parcelaBean.getStatus().equals(EnumStatusParcela.NAO_PAGA)) {
                    ParcelaReportDTO parcelaDTO = new ParcelaReportDTO();
                    parcelaDTO.setDataVencimento(DateUtils.getDiaMesAnoPortugues(parcelaBean.getDataVencimento()));
                    parcelaDTO.setNuParcela(parcelaBean.getNumero());
                    parcelaDTO.setPlano(String.valueOf(carne.getQtdParcela()));
                    parcelaDTO.setVlParcela(MathUtils.convertBigDecimalToString(parcelaBean.getValor()));
                    valorParcela = parcelaBean.getValor();
                    parcelas.add(parcelaDTO);
                }
            }
            Collections.sort(parcelas, Comparator.comparing(ParcelaReportDTO::getNuParcela));

            dto.setNomeLoja(loja.getNomeLoja());
            String telefoneLoja = (loja.getTelComercial() != null && !loja.getTelComercial().isEmpty()) ? loja.getTelComercial() : loja.getWhatsapp();
            dto.setTelefoneLoja(telefoneLoja);
            dto.setEnderecoLoja(loja.getEndereco().getEndereco().getLogradouro() + ", " + loja.getEndereco().getEndereco().getNumero());
            dto.setCidadeLoja(loja.getEndereco().getEndereco().getCidadeNome() + "/" + loja.getEndereco().getEndereco().getEstado());
            dto.setCep(loja.getEndereco().getEndereco().getCep());
            dto.setCnpj(loja.getCnpj());

            if (venda != null){
                dto.setNomeProduto(venda.getProduto().getNome());
            }

            dto.setCarneId(carne.getId());
            dto.setDataVenda(carne.getDataCompra());
            if (autorizacao != null) {
                dto.setAutorizacaoId(autorizacao.getId());
            } else {
                dto.setAutorizacaoId(carne.getId());
            }
            dto.setQtdParcelas(carne.getQtdParcela());
            dto.setValorParcela(valorParcela);
            dto.setValorVenda(carne.getValorTotal());

            dto.setParcelas(parcelas);
            dto.setNomeCliente(cliente.getNome());
            dto.setCpf(cliente.getCpf());

            //String encodedString = Base64.encodeBase64String(reportService.gerarPdf(new CarneReport(dto)));
            //return FileUtils.getFile(reportService.gerarPdf(new CarneReport(dto)), "carne.pdf", true);
            ImpressaoCarnePDF reportPDF = new ImpressaoCarnePDF(dto,
                    centralConfig.getCentralTempPath());
            return FileUtils.getFile(reportPDF.print(), true);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Erro ao gerar a carne: "+carne.getId());
        }
    }

    public List<CarneBean> findCarnesByParcelasIds(List<Long> parcelasIds) {
        return getRepository().findCarnesByParcelasIds(parcelasIds);
    }

    public void atualizaCarnesNaoPagos () {
        List<CarneBean> lista = getRepository().listaCarnePagosComParcelasAbertas();
        for (CarneBean bean : lista) {
            List<ParcelaBean> parcelas = parcelaService.findParcelasByCarneId(bean.getId());

            Integer count = 0;
            for (ParcelaBean parcelaBean : parcelas) {
                if (parcelaBean.getStatus() == EnumStatusParcela.NAO_PAGA) {
                    count++;
                }
            }

            if (count > 0) {
                log.info("Carne " + bean.getId() + " tem " + count + " parcelas abertas");
            }

            bean.setStatus(EnumStatusCarne.EM_ABERTO);
            update(bean);
        }
    }
}
