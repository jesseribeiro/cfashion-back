package br.com.crista.fashion.report;

import br.com.crista.fashion.utils.DateUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;

public class HeaderFooterPageEvent extends PdfPageEventHelper {

    private PdfTemplate t;
    private Image total;
    private RelatorioBasePDF relatorioBasePDF;

    public HeaderFooterPageEvent(RelatorioBasePDF relatorioBasePDF) {
        this.relatorioBasePDF = relatorioBasePDF;
    }

    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        addHeader(writer, document);
        try {
            relatorioBasePDF.addStartPage(document);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addFooter(writer, document);
    }

    private void addHeader(PdfWriter writer, Document document){
        PdfPTable header = new PdfPTable(3);
        try {
            // set defaults
            header.setTotalWidth(document.getPageSize().getWidth() - (document.leftMargin() * 2));
            header.setWidthPercentage(100);
            header.setWidths(new float[]{10f, 60f, 30f});
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(30);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            // add image
            Image logo = Image.getInstance(HeaderFooterPageEvent.class.getResource("/logo.png"));
            PdfPCell cellImage = new PdfPCell();
            cellImage.setPaddingTop(7);
            cellImage.setBorder(Rectangle.NO_BORDER);
            cellImage.addElement(logo);
            header.addCell(cellImage);

            // add text
            PdfPCell text = new PdfPCell();
            text.setPaddingBottom(15);
            text.setPaddingLeft(10);
            text.setBorder(Rectangle.NO_BORDER);
            text.addElement(new Phrase("Prático Administradora", new Font(Font.FontFamily.HELVETICA, 10)));
            text.addElement(new Phrase("http://crediariopratico.com.br", new Font(Font.FontFamily.HELVETICA, 8)));
            header.addCell(text);

            PdfPCell dataEmissao = new PdfPCell(new Paragraph("Emitido em " + DateUtils.getDiaMesAnoHoraMinutoSegundo(Calendar.getInstance()), new Font(Font.FontFamily.HELVETICA, 8)));
            dataEmissao.setBorder(Rectangle.NO_BORDER);
            dataEmissao.setHorizontalAlignment(Element.ALIGN_RIGHT);
            dataEmissao.setPaddingTop(10);
            header.addCell(dataEmissao);

            // write content
            header.writeSelectedRows(0, -1, document.leftMargin(), document.getPageSize().getHeight()-2, writer.getDirectContent());

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100); //Width 100%
            //table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

            //Set Column widths
            float[] columnWidths = {10f, 90f};
            table.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Grupo:", new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            cell1.setBorder(Rectangle.NO_BORDER);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Prático", new Font(Font.FontFamily.HELVETICA, 10)));
            cell2.setBorder(Rectangle.NO_BORDER);

            table.addCell(cell1);
            table.addCell(cell2);

            PdfPCell cell4 = new PdfPCell(new Paragraph(relatorioBasePDF.getTitulo(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
            cell4.setBorder(Rectangle.NO_BORDER);
            cell4.setColspan(3);
            table.addCell(cell4);

            document.add(table);
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }

    private void addFooter(PdfWriter writer, Document document){
        PdfPTable footer = new PdfPTable(3);
        try {
            // set defaults
            footer.setTotalWidth(document.getPageSize().getWidth() - (document.leftMargin() * 2));
            footer.setWidthPercentage(100);
            footer.setWidths(new float[]{85f, 10f, 5f});
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(30);
            footer.getDefaultCell().setBorder(Rectangle.TOP);
            footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

            // add copyright
            footer.addCell(new Phrase("\u00A9 http://crediariopratico.com.br", new Font(Font.FontFamily.HELVETICA, 8)));

            // add current page count
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(new Phrase(String.format("Página %d de", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));

            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
            footer.addCell(totalPageCount);

            // write page
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            footer.writeSelectedRows(0, -1, document.leftMargin(), 30, canvas);
            canvas.endMarkedContentSequence();
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
        ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
                new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
                totalWidth, 6, 0);
    }
}