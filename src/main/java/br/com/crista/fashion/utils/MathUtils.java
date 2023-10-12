package br.com.crista.fashion.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.Random;

import static java.util.Objects.isNull;

public class MathUtils {

    private static final Random random = new Random();
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt","BR")));

    public static BigDecimal percentage(BigDecimal base, BigDecimal taxa) {

        if (isNull(base) || isNull(taxa)) {

            return BigDecimal.ZERO;
        }

        BigDecimal juros = taxa.divide(new BigDecimal(100));

        return base.multiply(juros).setScale(2, BigDecimal.ROUND_UP);
    }

    public static int gerarCodigoAleatorio() {

        return random.nextInt(1000) + 1000;
    }

    public static BigDecimal convertStringToBigDecimal(String value) {

        try {

            return new BigDecimal(decimalFormat.parseObject(value).toString()).setScale(2);

        } catch (ParseException e) {

            throw new RuntimeException("Não foi possível converter o valor informado para um BigDecimal");
        }
    }

    public static String convertBigDecimalToString(BigDecimal value) {

        try {

            return decimalFormat.format(value);
        }

        catch (Exception e) {

            return "";
        }
    }

    public static String calcPercent (BigDecimal dividendo, BigDecimal divisor) {

        if (isNull(dividendo) || isNull(divisor)) {

            return convertBigDecimalToString(BigDecimal.ZERO);
        }

        if (dividendo.compareTo(BigDecimal.ZERO) == 0 || divisor.compareTo(BigDecimal.ZERO) == 0) {

            return convertBigDecimalToString(BigDecimal.ZERO);
        }

        BigDecimal percent = dividendo.divide(divisor, MathContext.DECIMAL128);

        return convertBigDecimalToString(percent.multiply(new BigDecimal(100).setScale(1, RoundingMode.HALF_EVEN)));
    }
}
