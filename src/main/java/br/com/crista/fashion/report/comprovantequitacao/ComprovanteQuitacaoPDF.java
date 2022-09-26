package br.com.crista.fashion.report.comprovantequitacao;

import br.com.crista.fashion.dto.ClienteDTO;
import br.com.crista.fashion.dto.LojaDTO;
import br.com.crista.fashion.report.RelatorioBasePDF;
import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.StringUtils;
import br.com.crista.fashion.dto.PagamentoDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.List;

@Slf4j
public class ComprovanteQuitacaoPDF extends RelatorioBasePDF {

    private final List<PagamentoDTO> dados;
    public ClienteDTO filtro;
    private LojaDTO lojaDto;
    private String anoUltPagamento;
    private static final float[] columnWidths = {50f,50f};
    private static final String[] titles = {
            "Data Pagamento", "Valor Pago"
    };

    public ComprovanteQuitacaoPDF(List<PagamentoDTO> dados, ClienteDTO clienteDto, LojaDTO lojaDto, String diretorio) throws FileNotFoundException, DocumentException {
        super("Comprovante de Quitação", PageSize.A4, diretorio);
        this.dados = dados;
        this.filtro = clienteDto;
        this.lojaDto = lojaDto;
        this.anoUltPagamento = DateUtils.getAno(dados.get(dados.size() -1).getDataPagto());
    }

    public String print() throws DocumentException {
        open();
        PdfPTable table = new PdfPTable(titles.length);
        table.setWidthPercentage(100);
        table.setWidths(columnWidths);

        for (PagamentoDTO dto : dados) {
            table.addCell(newCellNoBorder(dto.getDataPagto() + "", FONT_NORMAL, Element.ALIGN_CENTER));
            table.addCell(newCellNoBorder(dto.getValorPago() + "", FONT_NORMAL, Element.ALIGN_CENTER));
        }

        addTable(table);
        rodape();

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
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(1);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setPhrase(new Phrase(titles[i], FONT_NORMAL));
                header.setBorder(Rectangle.NO_BORDER);
                table.addCell(header);
            }
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void rodape() {
        try {
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);

            printNovaLinha(table,titles.length);
            printNovaLinha(table,titles.length);
            table.addCell(newCellNoBorder("Nos  termos  do  artigo  4  da  mencionada  Lei,  informamos  que  a  presente  declaração  de  quitação  substitui  os comprovantes  de  pagamentos  que  foram  efetivamente  lançados  nas  respectivas  parcelas,  inclusive  aqueles relativos a parcelas de anos anteriores pagas no ano de "+ anoUltPagamento +", as quais consideramos quitadas.", FONT_NORMAL,false, Element.ALIGN_JUSTIFIED));
            printNovaLinha(table,titles.length);
            table.addCell(newCellNoBorder("Esta  declaração  de  quitação  não  contempla  valores  em  atraso  ou  parcelas  vincendas  referentes  a  transações parceladas.", FONT_NORMAL,false, Element.ALIGN_JUSTIFIED));
            printNovaLinha(table,titles.length);
            printNovaLinha(table,titles.length);
            table.addCell(newCellNoBorder("Atenciosamente,", FONT_NORMAL,false, Element.ALIGN_LEFT));
            table.addCell(newCellNoBorder(lojaDto.getNomeFantasia(), FONT_BOLD,false, Element.ALIGN_LEFT));
            table.addCell(newCellNoBorder("Central de Atendimento: " + lojaDto.getTelComercial(), FONT_NORMAL,false, Element.ALIGN_LEFT));
            table.addCell(newCellNoBorder(lojaDto.getEmail(), FONT_NORMAL,false, Element.ALIGN_LEFT));
            addTable(table);
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    private String nomemclaturaFormal(){
        switch (filtro.getSexo()){
            case "F":
                return "Prezada Sra. ";
            default:
                return "Prezado Sr. ";
        }
    }

    private void cabecalho()  {
        String dataAtual = DateUtils.getDiaMesAnoPortugues(Calendar.getInstance());
        try {
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);
            table.addCell(newCellNoBorder(filtro.getNome() + "," + StringUtils.inserirMascaraCpfCnpj(filtro.getCpf()), FONT_NORMAL,false, Element.ALIGN_LEFT));
            table.addCell(newCellNoBorder(filtro.getEndereco().getLogradouro(), FONT_NORMAL,false, Element.ALIGN_LEFT));
            table.addCell(newCellNoBorder(filtro.getEndereco().getBairro() + "," + filtro.getEndereco().getCidadeNome() + "/" + filtro.getEndereco().getEstado(), FONT_NORMAL,false, Element.ALIGN_LEFT));
            table.addCell(newCellNoBorder(filtro.getEndereco().getCep(), FONT_NORMAL,false, Element.ALIGN_LEFT));
            printNovaLinha(table,titles.length);
            printNovaLinha(table,titles.length);
            table.addCell(newCellNoBorder(filtro.getEndereco().getCidadeNome() + "," + dataAtual, FONT_NORMAL,false, Element.ALIGN_LEFT));
            table.addCell(newCellNoBorder(nomemclaturaFormal()  + filtro.getNome(), FONT_NORMAL,false, Element.ALIGN_LEFT));
            printNovaLinha(table,titles.length);
            printNovaLinha(table,titles.length);
            table.addCell(newCellNoBorder("Em  cumprimento  ao  disposto  na  Lei  Federal  Número  12.007  de  29/07/2009  declaramos  que  no  ano  de  "+ anoUltPagamento +" foram  efetuados  os  seguintes  pagamentos  na  conta-cartão  vinculada    ao  cartão  de  crédito  acima  as  quais  damos plena quitação:", FONT_NORMAL,false, Element.ALIGN_JUSTIFIED));
            printNovaLinha(table,titles.length);
            addTable(table);
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addStartPage(Document document) {
        if (filtro != null) {
            cabecalho();
        }
        addTableHeader(document);
    }
}