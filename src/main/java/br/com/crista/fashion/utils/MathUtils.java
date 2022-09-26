package br.com.crista.fashion.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.Random;

public class MathUtils {

    private static final Random random = new Random();
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt","BR")));

    /*
        https://www.todamateria.com.br/juros-compostos/
        Para calcular os juros compostos, utiliza-se a expressão:

        M = C (1+i)^t

        Onde,

        M: montante
        C: capital
        i: taxa fixa
        t: período de tempo

        CALCULADORA DE JUROS COMPOSTOS
        Fórmula valor futuro: F = P * (1 + i)^n

        Calcule: F= 1000*(1 + 0,05)^12 = R$ 1.795,86
     */
    public static BigDecimal calcJurosComposto(BigDecimal capital, BigDecimal taxa, int tempo){
        BigDecimal montante;
        BigDecimal juros = taxa.divide(new BigDecimal(100));

        montante = capital.multiply(BigDecimal.ONE.add(juros).pow(tempo)).setScale(4, BigDecimal.ROUND_HALF_EVEN);;

        return montante.subtract(capital);

    }

    public static BigDecimal percentage(BigDecimal base, BigDecimal taxa){
        if (base == null || taxa == null) {
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
        try{
            return decimalFormat.format(value);
        }
        catch (Exception e){
            return "";
        }
        //BigDecimal novo = new BigDecimal(value.toString(), MathContext.DECIMAL32).setScale(2);
        //return novo.toString().replace(".", ",");
    }

    public static String convertBigDecimalToStringDIMP(BigDecimal value) {
        BigDecimal novo = new BigDecimal(value.toString(), MathContext.DECIMAL32).setScale(2);
        return novo.toString().replace(".", ",");
    }


    public static String convertBigDecimalToStringSemPonto(BigDecimal value) {
        BigDecimal novo = new BigDecimal(value.toString(), MathContext.DECIMAL32).setScale(2);
        return novo.toString().replaceAll("\\D+","");
    }

    // ref: https://www.jornalcontabil.com.br/saiba-como-calcular-os-juros-e-multas-do-boleto-bancario/
    public static BigDecimal calculaJurosAoMes(long qtdDias, BigDecimal valor, BigDecimal peJuros) {
        if (qtdDias == 0 || valor == null || peJuros == null || valor.compareTo(BigDecimal.ZERO) == 0 || peJuros.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal peJurosPorDia = peJuros.divide(new BigDecimal(Constants.QTD_DIAS_MES_COMERCIAL), 4, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal totalPeJuros = peJurosPorDia.multiply(new BigDecimal(qtdDias));
        return MathUtils.percentage(valor, totalPeJuros);
    }

    public static String valorPorExtenso(double vlr) {
        if (vlr == 0)
            return("zero");

        long inteiro = (long)Math.abs(vlr); // parte inteira do valor
        double resto = vlr - inteiro;       // parte fracionária do valor

        String vlrS = String.valueOf(inteiro);
        if (vlrS.length() > 15)
            return("Erro: valor superior a 999 trilhões.");

        String s = "", saux, vlrP;
        String centavos = String.valueOf((int)Math.round(resto * 100));

        String[] unidade = {"", "um", "dois", "três", "quatro", "cinco",
                "seis", "sete", "oito", "nove", "dez", "onze",
                "doze", "treze", "quatorze", "quinze", "dezesseis",
                "dezessete", "dezoito", "dezenove"};
        String[] centena = {"", "cento", "duzentos", "trezentos",
                "quatrocentos", "quinhentos", "seiscentos",
                "setecentos", "oitocentos", "novecentos"};
        String[] dezena = {"", "", "vinte", "trinta", "quarenta", "cinquenta",
                "sessenta", "setenta", "oitenta", "noventa"};
        String[] qualificaS = {"", "mil", "milhão", "bilhão", "trilhão"};
        String[] qualificaP = {"", "mil", "milhões", "bilhões", "trilhões"};

// definindo o extenso da parte inteira do valor
        int n, unid, dez, cent, tam, i = 0;
        boolean umReal = false, tem = false;
        while (!vlrS.equals("0")) {
            tam = vlrS.length();
// retira do valor a 1a. parte, 2a. parte, por exemplo, para 123456789:
// 1a. parte = 789 (centena)
// 2a. parte = 456 (mil)
// 3a. parte = 123 (milhões)
            if (tam > 3) {
                vlrP = vlrS.substring(tam-3, tam);
                vlrS = vlrS.substring(0, tam-3);
            }
            else { // última parte do valor
                vlrP = vlrS;
                vlrS = "0";
            }
            if (!vlrP.equals("000")) {
                saux = "";
                if (vlrP.equals("100"))
                    saux = "cem";
                else {
                    n = Integer.parseInt(vlrP, 10);  // para n = 371, tem-se:
                    cent = n / 100;                  // cent = 3 (centena trezentos)
                    dez = (n % 100) / 10;            // dez  = 7 (dezena setenta)
                    unid = (n % 100) % 10;           // unid = 1 (unidade um)
                    if (cent != 0)
                        saux = centena[cent];
                    if ((n % 100) <= 19) {
                        if (saux.length() != 0)
                            saux = saux + " e " + unidade[n % 100];
                        else saux = unidade[n % 100];
                    }
                    else {
                        if (saux.length() != 0)
                            saux = saux + " e " + dezena[dez];
                        else saux = dezena[dez];
                        if (unid != 0) {
                            if (saux.length() != 0)
                                saux = saux + " e " + unidade[unid];
                            else saux = unidade[unid];
                        }
                    }
                }
                if (vlrP.equals("1") || vlrP.equals("001")) {
                    if (i == 0) // 1a. parte do valor (um real)
                        umReal = true;
                    else saux = saux + " " + qualificaS[i];
                }
                else if (i != 0)
                    saux = saux + " " + qualificaP[i];
                if (s.length() != 0)
                    s = saux + ", " + s;
                else s = saux;
            }
            if (((i == 0) || (i == 1)) && s.length() != 0)
                tem = true; // tem centena ou mil no valor
            i = i + 1; // próximo qualificador: 1- mil, 2- milhão, 3- bilhão, ...
        }

        if (s.length() != 0) {
            if (umReal)
                s = s + " real";
            else if (tem)
                s = s + " reais";
            else s = s + " de reais";
        }

        // definindo o extenso dos centavos do valor
        if (!centavos.equals("0")) { // valor com centavos
            if (s.length() != 0) // se não é valor somente com centavos
                s = s + " e ";
            if (centavos.equals("1"))
                s = s + "um centavo";
            else {
                n = Integer.parseInt(centavos, 10);
                if (n <= 19)
                    s = s + unidade[n];
                else {             // para n = 37, tem-se:
                    unid = n % 10;   // unid = 37 % 10 = 7 (unidade sete)
                    dez = n / 10;    // dez  = 37 / 10 = 3 (dezena trinta)
                    s = s + dezena[dez];
                    if (unid != 0)
                        s = s + " e " + unidade[unid];
                }
                s = s + " centavos";
            }
        }
        return(s);
    }

    public static String calcPercent (BigDecimal dividendo, BigDecimal divisor) {
        if (dividendo == null || divisor == null) {
            return convertBigDecimalToString(BigDecimal.ZERO);
        }

        if (dividendo.compareTo(BigDecimal.ZERO) == 0 || divisor.compareTo(BigDecimal.ZERO) == 0) {
            return convertBigDecimalToString(BigDecimal.ZERO);
        }

        BigDecimal percent = dividendo.divide(divisor, MathContext.DECIMAL128);

        return convertBigDecimalToString(percent.multiply(new BigDecimal(100).setScale(1, RoundingMode.HALF_EVEN)));
    }
}
