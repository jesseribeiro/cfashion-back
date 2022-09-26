package br.com.crista.fashion.report.carne;

import br.com.crista.fashion.dto.ParcelaReportDTO;
import br.com.crista.fashion.report.RelatorioBaseCarnePDF;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.StringUtils;
import br.com.crista.fashion.dto.CarneReportDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.util.Calendar;

@Slf4j
public class ImpressaoCarnePDF extends RelatorioBaseCarnePDF {

    private final CarneReportDTO dados;
    private static final float[] columnWidths = {35f,15f,35f,15f};
    private static final String[] titles = {
            "","","",""
    };

    public ImpressaoCarnePDF(CarneReportDTO dados, String diretorio) throws FileNotFoundException, DocumentException {
        super("", PageSize.A4, diretorio);
        this.dados = dados;
    }

    public String print() throws DocumentException {
        open();
        PdfPTable table = new PdfPTable(titles.length);
        table.setWidthPercentage(100);
        table.setWidths(columnWidths);

        table.addCell(newCellNoBorder("COMPROVANTE DE VENDA", FONT_NORMAL, false, Element.ALIGN_CENTER, 2));
        table.addCell(newCellNoBorder("COMPROVANTE DE VENDA", FONT_NORMAL, false, Element.ALIGN_CENTER, 2));

        table.addCell(newCellNoBorder(dados.getNomeLoja() + ", " + dados.getTelefoneLoja(), FONT_NORMAL, false, Element.ALIGN_LEFT, 2));
        table.addCell(newCellNoBorder(dados.getNomeLoja() + ", " + dados.getTelefoneLoja(), FONT_NORMAL, false, Element.ALIGN_LEFT, 2));

        table.addCell(newCellNoBorder(dados.getEnderecoLoja() + ", " + dados.getCidadeLoja()
                + ", " + dados.getCep(), FONT_NORMAL, false, Element.ALIGN_LEFT, 2));
        table.addCell(newCellNoBorder(dados.getEnderecoLoja() + ", " + dados.getCidadeLoja()
                + ", " + dados.getCep(), FONT_NORMAL, false, Element.ALIGN_LEFT, 2));

        table.addCell(newCellNoBorder("CNPJ " + dados.getCnpj(), FONT_NORMAL, false, Element.ALIGN_LEFT, 2));
        table.addCell(newCellNoBorder("CNPJ " + dados.getCnpj(), FONT_NORMAL, false, Element.ALIGN_LEFT, 2));

        table.addCell(newCellNoBorder("COMPROVANTE", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder(dados.getCarneId() + "", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder("COMPROVANTE", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder(dados.getCarneId() + "", FONT_NORMAL, Element.ALIGN_LEFT));

        table.addCell(newCellNoBorder("DATA DA VENDA", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder(DateUtils.getDiaMesAnoHoraMinutoSegundo(Calendar.getInstance()), FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder("DATA DA VENDA", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder(DateUtils.getDiaMesAnoHoraMinutoSegundo(Calendar.getInstance()), FONT_NORMAL, Element.ALIGN_LEFT));

        table.addCell(newCellNoBorder("AUTORIZAÇÃO", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder(dados.getAutorizacaoId() + "", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder("AUTORIZAÇÃO", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder(dados.getAutorizacaoId() + "", FONT_NORMAL, Element.ALIGN_LEFT));

        table.addCell(newCellNoBorder("NOME DO PRODUTO", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder(dados.getNomeProduto() + "", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder("NOME DO PRODUTO", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder(dados.getNomeProduto() + "", FONT_NORMAL, Element.ALIGN_LEFT));

        table.addCell(newCellNoBorder("PARCELAS", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder(dados.getQtdParcelas() + "", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder("PARCELAS", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder(dados.getQtdParcelas() + "", FONT_NORMAL, Element.ALIGN_LEFT));

        table.addCell(newCellNoBorder("VALOR DA PARCELA", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder("R$ " + dados.getValorParcela().setScale(2), FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder("VALOR DA PARCELA", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder("R$ " + dados.getValorParcela().setScale(2), FONT_NORMAL, Element.ALIGN_LEFT));

        table.addCell(newCellNoBorder("VALOR DA VENDA", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder("R$ " + dados.getValorVenda().setScale(2), FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder("VALOR DA VENDA", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder("R$ " + dados.getValorVenda().setScale(2), FONT_NORMAL, Element.ALIGN_LEFT));

        table.addCell(newCellNoBorder("---------- PARCELAS CARNÊ ----------", FONT_NORMAL, false, Element.ALIGN_CENTER, 2));
        table.addCell(newCellNoBorder("---------- PARCELAS CARNÊ ----------", FONT_NORMAL, false, Element.ALIGN_CENTER, 2));

        table.addCell(newCellNoBorder("PARCELA    " + "       VENCIMENTO", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder("VALOR", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder("PARCELA    " + "       VENCIMENTO", FONT_NORMAL, Element.ALIGN_LEFT));
        table.addCell(newCellNoBorder("VALOR", FONT_NORMAL, Element.ALIGN_LEFT));

        for (ParcelaReportDTO dto : dados.getParcelas()){
            table.addCell(newCellNoBorder(numeroParcela(dto.getNuParcela()) + "         " + "       " + dto.getDataVencimento(), FONT_NORMAL, Element.ALIGN_LEFT));
            table.addCell(newCellNoBorder("R$ " + dto.getVlParcela(), FONT_NORMAL, Element.ALIGN_LEFT));
            table.addCell(newCellNoBorder(numeroParcela(dto.getNuParcela()) + "         " + "       " + dto.getDataVencimento(), FONT_NORMAL, Element.ALIGN_LEFT));
            table.addCell(newCellNoBorder("R$ " + dto.getVlParcela(), FONT_NORMAL, Element.ALIGN_LEFT));

        }

        printNovaLinha(table,titles.length);
        table.addCell(newCellNoBorder(dados.getNomeCliente() + ", CPF " + StringUtils.inserirMascaraCpfCnpj(dados.getCpf()), FONT_NORMAL, false, Element.ALIGN_CENTER, 2));
        table.addCell(newCellNoBorder(dados.getNomeCliente() + ", CPF " + StringUtils.inserirMascaraCpfCnpj(dados.getCpf()), FONT_NORMAL, false, Element.ALIGN_CENTER, 2));

        table.addCell(newCellNoBorder("***COMPROVANTE NÃO FISCAL***", FONT_NORMAL, false, Element.ALIGN_CENTER, 2));
        table.addCell(newCellNoBorder("***COMPROVANTE NÃO FISCAL***", FONT_NORMAL, false, Element.ALIGN_CENTER, 2));

        table.addCell(newCellNoBorder("1 VIA ESTABELECIMENTO", FONT_NORMAL, false, Element.ALIGN_CENTER, 2));
        table.addCell(newCellNoBorder("1 VIA DO CLIENTE", FONT_NORMAL, false, Element.ALIGN_CENTER, 2));

        table.addCell(newCellNoBorder("PARABÉNS PELO PRODUTO QUE VOCÊ COMPROU!!!", FONT_BOLD, false, Element.ALIGN_CENTER, 2));
        table.addCell(newCellNoBorder("PARABÉNS PELO PRODUTO QUE VOCÊ COMPROU!!!", FONT_BOLD, false, Element.ALIGN_CENTER, 2));

        table.addCell(newCellNoBorder("PAGAR EXCLUSIVAMENTE EM DINHEIRO E NA " + dados.getNomeLoja(), FONT_NORMAL, false, Element.ALIGN_CENTER, 2));
        table.addCell(newCellNoBorder("PAGAR EXCLUSIVAMENTE EM DINHEIRO E NA " + dados.getNomeLoja(), FONT_NORMAL, false, Element.ALIGN_CENTER, 2));

        table.addCell(newCellNoBorder("CENTRAL DE ATENDIMENTO - " + dados.getTelefoneConfig(), FONT_NORMAL, false, Element.ALIGN_CENTER, 2));
        table.addCell(newCellNoBorder("CENTRAL DE ATENDIMENTO - " + dados.getTelefoneConfig(), FONT_NORMAL, false, Element.ALIGN_CENTER, 2));

        addTable(table);
        close();
        return getFileLocation();
    }

    protected void addTableHeader(Document document) {
        PdfPTable table = new PdfPTable(titles.length);
        table.setWidthPercentage(100);
        try {
            table.setWidths(columnWidths);
            for (int i = 0; i < titles.length; i++) {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.WHITE);
                //header.setBorderWidth(1);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setPhrase(new Phrase(titles[i], FONT_NORMAL));
                //header.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                table.addCell(header);
            }
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public PdfPCell newCellNoBorder(String texto, Font font, int alignment) {
        return this.newCellNoBorder(texto, font, false, alignment, 1);
    }

    public PdfPCell newCellNoBorder(String texto, Font font, boolean backgound, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        if(backgound){
            cell.setBackgroundColor(ROW_GRAY);
        }
        return cell;
    }

    public PdfPCell newCellNoBorder(String texto, Font font, boolean backgound, int alignment, int colspan) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        if(backgound){
            cell.setBackgroundColor(ROW_GRAY);
        }
        cell.setColspan(colspan);
        return cell;
    }

    private String numeroParcela(Integer parcela) {
        String valor = parcela.toString();
        if (parcela < 10) {
            valor = "0" + valor;
        }
        return valor;
    }

    @Override
    public void addStartPage(Document document) {
    }
}