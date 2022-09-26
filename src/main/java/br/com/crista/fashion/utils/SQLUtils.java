package br.com.crista.fashion.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SQLUtils {

    public static Integer getInt(Object obj) {
        if(obj == null) return 0;

        if(obj instanceof BigInteger) {
            return ((BigInteger) obj).intValue();
        }
        if(obj instanceof Double) {
            return ((Double) obj).intValue();
        }
        if(obj instanceof BigDecimal) {
            return ((BigDecimal) obj).intValue();
        }
        if(obj instanceof Long) {
            return ((Long) obj).intValue();
        }
        return (Integer) obj;
    }

    public static Long getLong(Object obj) {
        if(obj == null) return 0L;
        if(obj instanceof BigInteger) {
            return ((BigInteger) obj).longValue();
        }
        if(obj instanceof Double) {
            return ((Double) obj).longValue();
        }
        if(obj instanceof BigDecimal) {
            return ((BigDecimal) obj).longValue();
        }
        if(obj instanceof Integer) {
            return ((Integer) obj).longValue();
        }
        return (Long) obj;
    }

    public static BigInteger getBigInteger(Object obj) {
        return new BigInteger(String.valueOf(getInt(obj)));
    }

    public static BigDecimal getBigDecimal(Object obj) {
        return new BigDecimal(String.valueOf(getInt(obj)));
    }
}
