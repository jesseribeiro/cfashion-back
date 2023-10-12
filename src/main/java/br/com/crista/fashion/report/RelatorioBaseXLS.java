package br.com.crista.fashion.report;

import br.com.crista.fashion.utils.DateUtils;
import br.com.crista.fashion.utils.FileUtils;
import br.com.crista.fashion.utils.MathUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Getter
@Setter
public abstract class RelatorioBaseXLS {

    private String titulo;
    private boolean landscape;
    private String fileLocation;
    private Workbook workbook;
    private Map<String, CellStyle> styles;
    private Sheet sheet;
    private int rownum;
    private static final String[] COLUNAS = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private int totalColunas;

    public static final String STYLE_TITLE = "STYLE_TITLE";
    public static final String STYLE_HEADER = "STYLE_HEADER";
    public static final String STYLE_CELL = "STYLE_CELL";
    public static final String STYLE_CELL_BOLD = "STYLE_CELL_BOLD";
    public static final String STYLE_VALOR = "STYLE_VALOR";
    public static final String STYLE_VALOR_BOLD = "STYLE_VALOR_BOLD";
    public static final String STYLE_CELL_GREY = "STYLE_CELL_GREY";
    public static final String STYLE_NO_BORDER = "STYLE_NO_BORDER";
    public static final String WRAP_STYLE = "WRAP_STYLE";

    public RelatorioBaseXLS(String titulo, Boolean landscape, String diretorio, int totalColunas) {
        this.titulo = titulo;
        this.landscape = landscape;
        this.fileLocation = diretorio + FileUtils.generateCode() + ".xlsx";
        this.rownum = 0;
        this.totalColunas = totalColunas;
        this.init();
    }

    public RelatorioBaseXLS(String titulo, Boolean landscape) {
        this(titulo, landscape, "/tmp", 5);
    }

    private void init() {

        workbook = new XSSFWorkbook();
        styles = createStyles(workbook);
        sheet = createSheet("Relatorio");

        Row headerRow = createRow();
        createCell(headerRow, 0,"Cristã Fashion - https://cristafashion.com.br/", STYLE_NO_BORDER, totalColunas - 2);
        createCell(headerRow, totalColunas - 1,"Emitido em " + DateUtils.getDiaMesAnoHoraMinutoSegundo(Calendar.getInstance()), STYLE_NO_BORDER);

        Row titleRow = createRow();
        createCell(titleRow, 0," ", STYLE_TITLE);
        createCell(titleRow, 1," ", STYLE_CELL, totalColunas - 1);
    }

    public void close() throws IOException {

        for (int i=0;i<totalColunas;i++) {
            sheet.autoSizeColumn(i);
        }

        FileOutputStream outputStream = new FileOutputStream(getFileLocation());
        workbook.write(outputStream);
        workbook.close();
    }

    public Sheet createSheet(String titulo) {

        Sheet sheet = workbook.createSheet(titulo);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(landscape);
        //sheet.setFitToPage(true);
        return sheet;
    }

    public Row createRow() {

        return sheet.createRow(rownum++);
    }

    public Cell createCell(Row row, int index, String value, String style) {

        Cell cell = row.createCell(index);
        cell.setCellValue(value);
        cell.setCellStyle(styles.get(style));
        return cell;
    }

    public Cell createCell(Row row, int index, double value, String style) {

        Cell cell = row.createCell(index);
        cell.setCellValue(value);
        cell.setCellStyle(styles.get(style));
        return cell;
    }

    public Cell createCell(Row row, int index, String value, boolean lineBreak) {

        Cell cell = row.createCell(index);
        cell.setCellValue(value);

        if (lineBreak){
            CellStyle wrapStyle = workbook.createCellStyle();
            wrapStyle.setWrapText(true);
            wrapStyle.setAlignment(HorizontalAlignment.CENTER);
            cell.setCellStyle(wrapStyle);
        }
        return cell;
    }

    public Cell createCell(Row row, int index, String value, String style, int indexFim) {

        Cell cell = row.createCell(index);
        cell.setCellValue(value);
        cell.setCellStyle(styles.get(style));
        cell.getSheet().addMergedRegion(CellRangeAddress.valueOf("$"+COLUNAS[index]+"$"+getRownum()+":$"+COLUNAS[indexFim]+"$"+getRownum())); // " Ex.: $B$1:$E$1"
        return  cell;
    }

    public void printTotal(String titulo) {

        Row row = createRow();
        Cell cell = row.createCell(0);
        cell.setCellValue(titulo);
        cell.setCellStyle(styles.get(STYLE_TITLE));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+(rownum + 1)+":$F$"+(rownum + 1)));
    }

    /**
     * Método responsável por iniciar a impressão do relatorio, é o ponto inicial do relatório
     */
    public abstract String print() throws IOException;

    /**
     * Create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(Workbook wb) {

        Map<String, CellStyle> styles = new HashMap<>();
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)14);
        titleFont.setBold(true);
        style = wb.createCellStyle();
        style.setFont(titleFont);
        styles.put(STYLE_TITLE, style);

        Font fontNormal = wb.createFont();
        fontNormal.setFontHeightInPoints((short)11);
        style = wb.createCellStyle();
        style.setFont(fontNormal);
        styles.put(STYLE_NO_BORDER, style);

        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short)11);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(monthFont);
        styles.put(STYLE_HEADER, style);

        style = wb.createCellStyle();
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put(STYLE_CELL, style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put(STYLE_VALOR, style);

        Font fontBold = wb.createFont();
        fontBold.setFontHeightInPoints((short)11);
        fontBold.setBold(true);
        style = wb.createCellStyle();
        style.setFont(fontBold);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put(STYLE_CELL_BOLD, style);

        style = wb.createCellStyle();
        style.setFont(fontBold);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put(STYLE_VALOR_BOLD, style);

        style = wb.createCellStyle();
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put(STYLE_CELL_GREY, style);

        return styles;
    }

    public void printTotal(String titulo, String merge) {

        Row row = createRow();
        Cell cell = row.createCell(0);
        cell.setCellValue(titulo);
        cell.setCellStyle(styles.get(STYLE_TITLE));
        sheet.addMergedRegion(CellRangeAddress.valueOf(merge));
    }

    public BigDecimal seNullReturnZero(BigDecimal value) {

        return isNull(value) ? BigDecimal.ZERO : value;
    }

    public Object seZeroRetornaVazio(BigDecimal value) {

        return (value.compareTo(BigDecimal.ZERO) == 0) ? "" : ("R$ " + MathUtils.convertBigDecimalToString(value));
    }

    public Object seStringVazia(Object texto) {

        return isNull(texto) ? "": texto;
    }

    public void printNovaLinha(Integer index) {

        Row row = createRow();

        for (int i = 0; i < index; i++) {

            createCell(row, i, "", STYLE_CELL);
        }
    }

    public void printNovaLinhaSemBorda(Integer index) {

        Row row = createRow();

        for (int i = 0; i < index; i++) {

            createCell(row, i, "", STYLE_NO_BORDER);
        }
    }

    public void printRow(String text, Integer index) {

        Row row = createRow();
        createCell(row, 0, text, STYLE_VALOR_BOLD, index);
    }
}
