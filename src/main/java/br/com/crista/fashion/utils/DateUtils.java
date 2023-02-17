package br.com.crista.fashion.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.util.Calendar.*;

@Slf4j
@Component("dateUtils")
public class DateUtils {

    public DateUtils() {
    }

    public static Calendar proximoMes(Calendar dataVencimento, int mes) {
        Integer tempo = mes * 30;
        Calendar data = (Calendar)dataVencimento.clone();
        data.add(Calendar.DATE, tempo);
        return data;
    }

    public static String getDia(Calendar date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            return sdf.format(date.getTime());
        }
        return null;
    }

    public static String getDiaMesAnoPortugues(Calendar date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(date.getTime());
        } else {
            return null;
        }
    }

    public static String getDiaMesAnoPortugues(String dateString) {
        try{
            return getDiaMesAnoPortugues(getDiaMesAno(dateString));
        }
        catch (Exception e){
            return dateString;
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

    public static String getDiaMesPortugues(String data) {
        return data.substring(8, 10) + "/" + DateUtils.getMesAtual(data.substring(5, 7));
    }

    public static String getMesAnoString(String data) {
        return DateUtils.getMesAtual(data.substring(5, 7)) + "/" + data.substring(0, 4);
    }
}
