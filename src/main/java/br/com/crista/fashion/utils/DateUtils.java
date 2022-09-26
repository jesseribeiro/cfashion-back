package br.com.crista.fashion.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.util.Calendar.*;

@Slf4j
@Component("dateUtils")
public class DateUtils {
    public DateUtils() {
    }

    public static boolean isBankHoliday(Calendar d) {
        Calendar c = new GregorianCalendar();
        c.setTime(d.getTime());
        return (Calendar.SATURDAY == c.get(c.DAY_OF_WEEK)) || (Calendar.SUNDAY == c.get(c.DAY_OF_WEEK));
    }

    public static Calendar addDia(Calendar calendar, int qtdDia) {
        if(calendar != null && qtdDia != 0) {
            calendar.add(Calendar.DAY_OF_MONTH, qtdDia);
        }
        return calendar;
    }


    public static Calendar geraDataVencimento(Integer diasVencimento) {
        Calendar dataVencimento = Calendar.getInstance();
        dataVencimento.add(Calendar.DAY_OF_MONTH, diasVencimento);
        return dataVencimento;
        /*
        while (isBankHoliday(dataVencimento)) {
            dataVencimento.add(Calendar.DAY_OF_MONTH, 1);
        }
        */
    }

    public static Calendar proximoMes(Date diasVencimento, Integer mes) {

        GregorianCalendar gcal = new GregorianCalendar();

        try
        {
            gcal.setTime(diasVencimento);
            gcal.add(MONTH, mes);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        diasVencimento = new Date(gcal.getTime().getTime());

        Calendar dataVencimento = Calendar.getInstance();
        dataVencimento.setTime(diasVencimento);
        return dataVencimento;
    }

    /**
     * Compras feitas 26/03 a 05/04 (data de corte) + 35 dias - são pagas 10/05
     * Compras feitas 06/04 a 15/04 (data de corte) + 35 dias - são pagas 20/05
     * Compras feitas 16/04 a 25/04 (data de corte) + 35 dias - são pagas 30/05
     * ☝🏻 exemplo de repasse parcelado, o adiantado é o mesmo período mas adiciona 25 dias não 35 dias...
     * @param dataReferencia
     * @return
     * */
    public static Calendar getDataDeCorte(final Calendar dataReferencia) {
        if(dataReferencia == null) {
            throw new IllegalArgumentException("A data de referencia não pode ser null");
        }
        Calendar dataCorte = (Calendar) dataReferencia.clone();
        int dia = dataReferencia.get(Calendar.DAY_OF_MONTH);
        if (dia >= 26) {
            dataCorte.add(Calendar.MONTH, 1);
            dataCorte.set(Calendar.DAY_OF_MONTH, 5);
        } else {
            if (dia <= 5) {
                dataCorte.set(Calendar.DAY_OF_MONTH, 5);
            }
            if (dia >= 6 && dia <= 15) {
                dataCorte.set(Calendar.DAY_OF_MONTH, 15);
            }
            if (dia >= 16 && dia <= 25) {
                dataCorte.set(Calendar.DAY_OF_MONTH, 25);
            }
        }
        return dataCorte;
    }

    /**
     * Compras feitas 26/03 a 05/04 (data de corte) + 35 dias - são pagas 10/05
     * Compras feitas 06/04 a 15/04 (data de corte) + 35 dias - são pagas 20/05
     * Compras feitas 16/04 a 25/04 (data de corte) + 35 dias - são pagas 30/05
     * ☝🏻 exemplo de repasse parcelado, o adiantado é o mesmo período mas adiciona 25 dias não 35 dias...
     * @param dataCorte
     * @return
     * */
    public static Calendar getDataRepasse(final Calendar dataCorte, final int diasParaRepasse) {
        if(dataCorte == null) {
            throw new IllegalArgumentException("A data de corte não pode ser null");
        }
        Calendar dataRepasse = (Calendar) dataCorte.clone();
        dataRepasse.add(Calendar.DAY_OF_MONTH, diasParaRepasse);
        return dataRepasse;
    }

    /**
     * Compras feitas 26/03 a 05/04 (data de corte) + 35 dias - são pagas 10/05
     * Compras feitas 06/04 a 15/04 (data de corte) + 35 dias - são pagas 20/05
     * Compras feitas 16/04 a 25/04 (data de corte) + 35 dias - são pagas 30/05
     * ☝🏻 exemplo de repasse parcelado, o adiantado é o mesmo período mas adiciona 25 dias não 35 dias...
     * @return
     *
    public static Calendar getDataDeCorte(final Calendar dataReferencia, final Integer diasParaRepasse) {
        if(dataReferencia == null) {
            throw new IllegalArgumentException("A data de referencia não pode ser null");
        }
        if(diasParaRepasse == null) {
            throw new IllegalArgumentException("Os dias de repasse não pode ser null");
        }
        Calendar dataCorte = (Calendar) dataReferencia.clone();
        dataCorte.add(Calendar.DAY_OF_MONTH, diasParaRepasse * -1); // retiro da data de referencia a quantidade de dias para o repasse
        /*
        int dia = dataCorte.get(Calendar.DAY_OF_MONTH);
        if (dia >= 26 || dia <= 5) {
            dataCorte.set(Calendar.DAY_OF_MONTH, 5);
        } else {
            if (dia >= 6 && dia <= 15) {
                dataCorte.set(Calendar.DAY_OF_MONTH, 15);
            } else {
                dataCorte.set(Calendar.DAY_OF_MONTH, 25);
            }
        }
        return dataCorte;
    }
    */

    public static String getHoraMinuto(Calendar time) {
        if (time != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(time.getTime());
        } else {
            return null;
        }
    }

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:ms");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(inDate.trim());
            return true;
        } catch (ParseException var3) {
            return false;
        }
    }

    public static boolean isValidDateSimple(String inDate) {
        if (inDate.length() > 10) {
            return false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(inDate.trim());
            return true;
        } catch (ParseException var3) {
            return false;
        }
    }

    public static Calendar getHoraMinuto(String time) {
        try {
            if (time != null) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                calendar.setTime(sdf.parse(time));
                return calendar;
            } else {
                return null;
            }
        } catch (ParseException var3) {
            throw new RuntimeException("Formato incorreto da hora");
        }
    }

    public static String getHoraMinutoSegundo(Calendar time) {
        if (time != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            return sdf.format(time.getTime());
        } else {
            return null;
        }
    }


    public static Date addMonth(int field, int amount, Date origDate)
    {
        GregorianCalendar gcal = new GregorianCalendar();
        try
        {
            gcal.setTime(origDate);
            gcal.add(field, amount);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return (new Date(gcal.getTime().getTime()) );
    }


    public static Boolean isHoje(Calendar date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date.getTime()).equals(sdf.format(Calendar.getInstance().getTime()));
        } else {
            return false;
        }
    }

    public static String getDiaMesAno(Calendar date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date.getTime());
        }
        return null;
    }

    public static String getYYYYMMDD() {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date.getTime());
    }

    public static String getDDMMYYYY() {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        return sdf.format(date.getTime());
    }

    public static String getDDMMYYYY(Calendar date) {
        if(date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            return sdf.format(date.getTime());
        }
        return null;
    }

    public static Integer getDiaMesAnoRelatorio(String dateString) {
        Calendar date = getDiaMesAno(dateString);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt(sdf.format(date.getTime()));
    }

    public static Integer getDiaMesAnoRelatorio(Calendar date) {
        if(date == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt(sdf.format(date.getTime()));
    }

    public static String getDiaMesAnoPortugues(String dateString) {
        try{
            return getDiaMesAnoPortugues(getDiaMesAno(dateString));
        }
        catch (Exception e){
            return dateString;
        }
    }
    public static String getAno(String data){
        return data.substring(data.length() - 4);
    }

    public static String getDiaMesAnoPortugues(Calendar date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(date.getTime());
        } else {
            return null;
        }
    }

    public static String getMesAnoPortugues(Calendar date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
            return sdf.format(date.getTime());
        } else {
            return null;
        }
    }

    public static String getMesAnoPortugues(String date) {
        if (date != null) {
            return getMesAnoPortugues(getMesAno(date));
        } else {
            return null;
        }
    }

    public static String getMesAno(Calendar date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            return sdf.format(date.getTime());
        } else {
            return null;
        }
    }

    public static Calendar getMesAno(String date) {
        try {
            if (date != null && !date.isEmpty()) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                calendar.setTime(sdf.parse(date));
                return zeraHorario(calendar);
            } else {
                return null;
            }
        } catch (ParseException e) {
            return null;
        }
    }

    public static Integer getMesAnoRelatorio(Calendar date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
            return Integer.parseInt(sdf.format(date.getTime()));
        } else {
            return null;
        }
    }

    public static Calendar getDiaMesAno(String time) {
        try {
            if (time != null && !time.isEmpty()) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                calendar.setTime(sdf.parse(time));
                return zeraHorario(calendar);
            } else {
                return null;
            }
        } catch (ParseException var3) {
            throw new RuntimeException("Formato incorreto da data");
        }
    }

    public static Calendar getDiaMesAno(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } else {
            return null;
        }
    }

    public static String getDiaMesAnoHoraMinutoSegundo(Calendar date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return sdf.format(date.getTime());
        } else {
            return null;
        }
    }

    public static String getDiaMesAnoTHoraMinutoSegundo(Calendar date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return sdf.format(date.getTime());
        } else {
            return null;
        }
    }

    public static Calendar getDiaMesAnoTHoraMinutoSegundo(String time) {
        try {
            if (time != null) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                calendar.setTime(sdf.parse(time));
                return calendar;
            } else {
                return null;
            }
        } catch (ParseException var3) {
            throw new RuntimeException("Formato incorreto da data");
        }    }

    public static String getDiaMesAnoHoraMinutoSegundoMs(Calendar date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            return sdf.format(date.getTime());
        } else {
            return null;
        }
    }

    public static Calendar getDiaMesAnoHoraMinutoSegundoMs(String time) {
        try {
            if (time != null) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                calendar.setTime(sdf.parse(time));
                return calendar;
            } else {
                return null;
            }
        } catch (ParseException var3) {
            throw new RuntimeException("Formato incorreto da data");
        }
    }

    public static String getDiaMesAnoHoraMinutoSegundoArquivo(Calendar date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            return sdf.format(date.getTime());
        } else {
            return null;
        }
    }

    public static Calendar getDiaMesAnoHoraMinutoSegundoPortugues(String time) {
        try {
            if (time != null) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                calendar.setTime(sdf.parse(time));
                return calendar;
            } else {
                return null;
            }
        } catch (ParseException var3) {
            throw new RuntimeException("Formato incorreto da data");
        }
    }

    public static Calendar getDiaMesAnoHoraMinutoSegundo(String time) {
        try {
            if (time != null) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                calendar.setTime(sdf.parse(time));
                return calendar;
            } else {
                return null;
            }
        } catch (ParseException var3) {
            throw new RuntimeException("Formato incorreto da data");
        }
    }

    public static String getDiaMesAnoHoraMinutoSegundoBackupFormat(Calendar date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
            return sdf.format(date.getTime());
        } else {
            return null;
        }
    }

    public static Calendar getDiaMesAnoHoraMinutoSegundoBackupFormat(String time) {
        try {
            if (time != null) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
                calendar.setTime(sdf.parse(time));
                return calendar;
            } else {
                return null;
            }
        } catch (ParseException var3) {
            throw new RuntimeException("Formato incorreto da data");
        }
    }

    public static Calendar getHoraMinutoSegundo(String time) {
        try {
            if (time != null) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                calendar.setTime(sdf.parse(time));
                return calendar;
            } else {
                return null;
            }
        } catch (ParseException var3) {
            throw new RuntimeException("Formato incorreto da data");
        }
    }

    /**
        Em uma subtração a – b = c, a é chamado minuendo, b é chamado subtraendo e c é chamado resto ou diferença. O número c é o resultado da subtração.
     */
    public static long diffDateInDays(Calendar b, Calendar a) {
        try {
            b = zeraHorario(b);
            a = zeraHorario(a);
            long diff = a.getTimeInMillis() - b.getTimeInMillis();
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            return 0;
        }
    }

    public static boolean equalsDate(Calendar date1, Calendar date2) {
        return diffDateInDays(date1, date2) == 0;
    }

    public static String getDiferencaDateHoraMinutoSegundo(Calendar dataInicio, Calendar dataFim) {
        long difMilli = dataFim.getTimeInMillis() - dataInicio.getTimeInMillis();
        int timeInSeconds = (int)difMilli / 1000;
        if (timeInSeconds < 0) {
            timeInSeconds *= -1;
        }

        int hours = timeInSeconds / 3600;
        timeInSeconds -= hours * 3600;
        int minutes = timeInSeconds / 60;
        timeInSeconds -= minutes * 60;
        return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", timeInSeconds);
    }

    public static Integer getDiferencaDateHora(Calendar dataInicio, Calendar dataFim) {
        long difMilli = dataFim.getTimeInMillis() - dataInicio.getTimeInMillis();
        int timeInSeconds = (int)difMilli / 1000;
        if (timeInSeconds < 0) {
            timeInSeconds *= -1;
        }

        return timeInSeconds / 3600;
    }

    public static String getSecondsToHour(Integer timeInSeconds) {
        if (timeInSeconds != null && !timeInSeconds.equals(0)) {
            if (timeInSeconds < 0) {
                timeInSeconds = timeInSeconds * -1;
            }

            int hours = timeInSeconds / 3600;
            timeInSeconds = timeInSeconds - hours * 3600;
            int minutes = timeInSeconds / 60;
            timeInSeconds = timeInSeconds - minutes * 60;
            int seconds = timeInSeconds;
            return String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
        } else {
            return "00:00:00";
        }
    }

    public static Integer getTimeInSeconds(Calendar calendar) {
        int hora = calendar.get(11) * 3600;
        int minuto = calendar.get(12) * 60;
        int segundo = calendar.get(13);
        return hora + minuto + segundo;
    }

    public static Integer getTimeInMinutes(Calendar calendar) {
        int hora = calendar.get(11) * 60;
        int minuto = calendar.get(12);
        return hora + minuto;
    }

    public static Integer getTimeInMinutes(String time) {
        return getTimeInMinutes(getHoraMinutoSegundo(time));
    }

    public static Integer getTimeInSeconds(String time) {
        return getTimeInSeconds(getHoraMinutoSegundo(time));
    }

    public static Integer diasMes(Integer ano, Integer mes) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, ano);
        calendar.set(2, mes - 1);
        calendar.set(5, 1);
        return calendar.getActualMaximum(5);
    }

    public static Calendar getDataNoUltimoDia(Integer ano, Integer mes) {
        Calendar calendar = getCalendarObject(ano, mes);
        calendar.set(5, calendar.getActualMaximum(5));
        return calendar;
    }

    public static Calendar getCalendarByDate(Date date) {
        if(date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar getCalendarObject(Integer ano, Integer mes) {
        if (!validarAno(ano)) {
            throw new RuntimeException("Ano inválido: " +ano);
        } else if (!validarMes(mes)) {
            throw new RuntimeException("Mês inválido: " +mes);
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1, ano);
            calendar.set(2, mes - 1);
            calendar.set(5, 1);
            return zeraHorario(calendar);
        }
    }

    public static Integer finaisSemanaMes(Integer ano, Integer mes) {
        mes = mes - 1;
        Integer quantidade = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, ano);
        calendar.set(2, mes);
        calendar.set(5, 1);

        for(; calendar.get(2) == mes; calendar.add(5, 1)) {
            if (isFinalDeSemana(calendar)) {
                quantidade = quantidade + 1;
            }
        }

        return quantidade;
    }

    public static Integer finaisSemanaPeriodo(Calendar inicio, Calendar fim) {
        Integer quantidade;
        for(quantidade = 0; inicio.before(fim) && inicio.equals(fim); inicio.add(5, 1)) {
            if (isFinalDeSemana(inicio)) {
                quantidade = quantidade + 1;
            }
        }

        return quantidade;
    }

    public static Calendar getAmanha() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, 1);
        return calendar;
    }

    public static boolean isFinalDeSemana(Calendar data) {
        return data.get(7) == 7 || data.get(7) == 1;
    }

    public static Integer getSecondsFromTime(Calendar data) {
        return 3600 * data.get(11) + 60 * data.get(12) + data.get(13);
    }

    public static Double secondsToHour(Integer segundos) {
        return segundos > 0 ? (double)segundos * 1.0D / 60.0D / 60.0D : 0.0D;
    }

    public static boolean validarAno(Integer ano) {
        return ano > 2000;
    }

    public static boolean validarMes(Integer mes) {
        return mes >= 1 && mes <= 12;
    }

    public static void validarMesAno(Integer mes, Integer ano) {
        if (!validarAno(ano)) {
            throw new RuntimeException("Ano inválido: " +ano);
        } else if (!validarMes(mes)) {
            throw new RuntimeException("Mês inválido: " +mes);
        }
    }

    public static Calendar zeraHorario(Calendar time) {
        Calendar tempo = (Calendar)time.clone();
        tempo.set(HOUR_OF_DAY, 0);
        tempo.set(MINUTE, 0);
        tempo.set(SECOND, 0);
        tempo.set(MILLISECOND, 0);
        return tempo;
    }

    public static Calendar setUltimaHoraDoDia(Calendar time) {
        Calendar tempo = (Calendar)time.clone();
        tempo.set(HOUR_OF_DAY, 23);
        tempo.set(MINUTE, 59);
        tempo.set(SECOND, 59);
        tempo.set(MILLISECOND, 999);
        return tempo;
    }

    public static String formataDiaMesAno(Integer time) {
        return formataDiaMesAno(time, "ddMMyyyy");
    }

    public static String formataDiaMesAno(Integer time, String format) {
        try {
            if (time != null) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                calendar.setTime(sdf.parse(time.toString()));
                return getDiaMesAnoPortugues(calendar);
            } else {
                return null;
            }
        } catch (ParseException var4) {
            throw new RuntimeException("Formato incorreto da data ");
        }
    }

    public static String getMesAtualPortugues() {
        Calendar calendar = Calendar.getInstance();
        switch(calendar.get(2)) {
        case 0:
            return "Janeiro";
        case 1:
            return "Fevereiro";
        case 2:
            return "Março";
        case 3:
            return "Abril";
        case 4:
            return "Maio";
        case 5:
            return "Junho";
        case 6:
            return "Julho";
        case 7:
            return "Agosto";
        case 8:
            return "Setembro";
        case 9:
            return "Outubro";
        case 10:
            return "Novembro";
        case 11:
        case 12:
            return "Dezembro";
        default:
            return "";
        }
    }

    public static String getDiaSemanaAtual() {
        Calendar calendar = Calendar.getInstance();
        switch(calendar.get(7)) {
        case 1:
            return "domingo";
        case 2:
            return "segunda-feira";
        case 3:
            return "terça-feira";
        case 4:
            return "quarta-feira";
        case 5:
            return "quinta-feira";
        case 6:
            return "sexta-feira";
        case 7:
            return "sábado";
        default:
            return "";
        }
    }

    public static Calendar primeiroDiaMes(Calendar calendar) {
        if(calendar != null) {
            calendar = zeraHorario(calendar);
            calendar.set(DAY_OF_MONTH, 1);
        }
        return calendar;
    }

    public static Calendar ultimoDiaMes(Calendar calendar) {
        if(calendar != null) {
            calendar = setUltimaHoraDoDia(calendar);
            calendar.set(DAY_OF_MONTH, 1); // só para evitar erros de data, força primeiro dia e depois set o ultimo
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        return calendar;
    }

    public static String convertMMYYYYExtenso(String mmYYYY) {
        if (mmYYYY != null) {
            Calendar hoje = Calendar.getInstance();
            int hojeEmMeses = (hoje.get(YEAR) * 12) + (hoje.get(MONTH) + 1);
            String split[] = mmYYYY.split("/");
            int parametrosEmMeses = (Integer.parseInt(split[1]) * 12) + (Integer.parseInt(split[0]));
            int diferencaEmMeses = hojeEmMeses - parametrosEmMeses;

            int anos = 0;
            int meses = diferencaEmMeses;
            if(diferencaEmMeses > 12) {
                anos = diferencaEmMeses/12;
                meses = diferencaEmMeses % 12;
            }
            return anos + " ano(s) e " + meses + " mês(es)";
        }
        return null;
    }

    public static Time convertStringToTimeHHMM(String horaStr) {
        if(horaStr != null && !horaStr.isEmpty()) {
            int hour;
            int minute;
            int second = 0;
            int firstColon;
            int secondColon;

            firstColon = horaStr.indexOf(':');
            secondColon = horaStr.indexOf(':', firstColon+1);
            if (firstColon > 0 && firstColon < horaStr.length()-1) {
                hour = Integer.parseInt(horaStr.substring(0, firstColon));
                if (secondColon > 0 && secondColon < horaStr.length()-1) { // HH:mm:ss
                    minute = Integer.parseInt(horaStr.substring(firstColon+1, secondColon));
                    second = Integer.parseInt(horaStr.substring(secondColon+1));
                } else {
                    minute = Integer.parseInt(horaStr.substring(firstColon+1)); // HH:mm
                }
            } else {
                throw new java.lang.IllegalArgumentException();
            }

            return new Time(hour, minute, second);
        }
        return null;
    }

    public static Long convertDateAndTimeInMilliseconds(Calendar data, Time time) {
        if(data != null && time != null) {
            Calendar _data = zeraHorario(data);
            int hora = time.getHours();
            int minutos = time.getMinutes();
            _data.set(HOUR, hora);
            _data.set(MINUTE, minutos);
            Long timeResult = _data.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
            if(timeResult > 0) {
                return timeResult;
            }
        }
        return null;
    }

    public static Date convertStringInDate(String data) {
        Date parcela = null;
        try {
            parcela = new SimpleDateFormat("dd/MM/yyyy").parse(data);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return parcela;
    }

    public static List<String> getListaMesesAnoPeriodoStr(Calendar dataInicio, Calendar dataFim) {
        // ["2020-06","2020-07", "2020-08"]  2021-01-10  2021-03-01
        List<String> lista = new ArrayList();
        Calendar dataInicioClone = (Calendar) dataInicio.clone();
        dataInicioClone.set(Calendar.DAY_OF_MONTH, 1);

        Calendar dataFimClone = (Calendar) dataFim.clone();
        dataFimClone.set(Calendar.DAY_OF_MONTH, 2);
        int month = 0;
        String monthStr;
        while(dataInicioClone.before(dataFimClone)) {
            monthStr = "";
            month = dataInicioClone.get(Calendar.MONTH) + 1;
            if(month < 10) {
                monthStr = "0";
            }
            monthStr += month;

            lista.add(dataInicioClone.get(Calendar.YEAR) + "-" + monthStr);
            dataInicioClone.add(Calendar.MONTH, 1);
        }
        return lista;
    }

    public static List<String> getListaAnoPeriodoStr(Calendar dataInicio, Calendar dataFim) {
        List<String> lista = new ArrayList();
        Calendar dataInicioClone = (Calendar) dataInicio.clone();
        dataInicioClone = zeraHorario(dataInicioClone);

        Calendar dataFimClone = (Calendar) dataFim.clone();
        dataFimClone = zeraHorario(dataFimClone);

        while(dataInicioClone.compareTo(dataFimClone) <= 0) {
            lista.add(dataInicioClone.get(Calendar.YEAR) + "");
            dataInicioClone.add(YEAR, 1);
        }
        return lista;
    }

    public static List<String> getListaDiasPeriodo(Calendar dataInicio, Calendar dataFim) {
        List<String> lista = new ArrayList();
        Calendar dataInicioClone = (Calendar) dataInicio.clone();
        dataInicioClone = zeraHorario(dataInicioClone);

        Calendar dataFimClone = (Calendar) dataFim.clone();
        dataFimClone = zeraHorario(dataFimClone);

        while(dataInicioClone.compareTo(dataFimClone) <= 0) {
            lista.add(DateUtils.getDiaMesAno(dataInicioClone));
            dataInicioClone.add(Calendar.DATE, 1);
        }
        return lista;
    }

    public static Object getMesAtual (String mes){
        String atual = null;
        switch(mes) {
            case "01": atual = "Janeiro"; break;
            case "02": atual = "Fevereiro"; break;
            case "03": atual = "Março"; break;
            case "04": atual = "Abril"; break;
            case "05": atual = "Maio"; break;
            case "06": atual ="Junho"; break;
            case "07": atual = "Julho"; break;
            case "08": atual = "Agosto"; break;
            case "09": atual = "Setembro"; break;
            case "10": atual = "Outubro"; break;
            case "11": atual = "Novembro"; break;
            case "12": atual = "Dezembro"; break;
        }
        return atual;
    }

    public static Calendar convertTimestampSQL(Timestamp timestampSQL) {
        if(timestampSQL != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestampSQL.getTime());
            return calendar;
        }
        return null;
    }

    public static Calendar convertDateSQL(Object date) {
        if(date != null) {
            long timeInMillis = 0;
            try {
                java.sql.Date newDate = (java.sql.Date) date;
                timeInMillis = newDate.getTime();
            } catch (Exception e) {
                Timestamp timestamp = (Timestamp) date;
                timeInMillis = timestamp.getTime();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeInMillis);
            return calendar;
        }
        return null;
    }

    public static Integer calculaIdade(Calendar dataNasc, Calendar dataHoje) {
        if (dataNasc != null && !dataNasc.equals("")) {
            Integer idade = 0;
            while (dataNasc.compareTo(dataHoje) <= 0) {
                idade++;
                dataHoje.add(Calendar.YEAR, -1);
            }
            return idade;
        } else {
            return 0;
        }
    }

    public static String getDiaMesPortugues(String data) {
        return data.substring(8, 10) + "/" + DateUtils.getMesAtual(data.substring(5, 7));
    }

    public static String getMesAnoString(String data) {
        return DateUtils.getMesAtual(data.substring(5, 7)) + "/" + data.substring(0, 4);
    }
}
