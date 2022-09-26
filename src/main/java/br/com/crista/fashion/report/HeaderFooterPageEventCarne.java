package br.com.crista.fashion.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;
import java.net.MalformedURLException;

public class HeaderFooterPageEventCarne extends PdfPageEventHelper {

    private PdfTemplate t;
    private Image total;
    private RelatorioBaseCarnePDF relatorioBaseCarnePDF;

    public HeaderFooterPageEventCarne(RelatorioBaseCarnePDF relatorioBaseCarnePDF) {
        this.relatorioBaseCarnePDF = relatorioBaseCarnePDF;
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
        relatorioBaseCarnePDF.addStartPage(document);
    }

    private void addHeader(PdfWriter writer, Document document){
        PdfPTable header = new PdfPTable(2);
        try {
            // set defaults
            header.setTotalWidth(document.getPageSize().getWidth() - (document.leftMargin() * 2));
            header.setWidthPercentage(100);
            header.setWidths(new float[]{50f,50f});
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(5);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            // add image
            Image logo = Image.getInstance(HeaderFooterPageEventCarne.class.getResource("/logo.png"));
            logo.scalePercent(10);
            logo.setAlignment(Element.ALIGN_CENTER);
            PdfPCell cellImage = new PdfPCell();
            cellImage.setBorder(Rectangle.NO_BORDER);
            cellImage.setPaddingTop(10);
            cellImage.addElement(logo);
            header.addCell(cellImage);
            header.addCell(cellImage);

            // write content
            header.writeSelectedRows(0, -1, document.leftMargin(), document.getPageSize().getHeight()-2, writer.getDirectContent());

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100); //Width 100%
            //table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

            //Set Column widths
            float[] columnWidths = {10f, 90f};
            table.setWidths(columnWidths);

            document.add(table);
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
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