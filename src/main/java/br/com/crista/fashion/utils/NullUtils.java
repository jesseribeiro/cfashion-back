package br.com.crista.fashion.utils;

import java.util.UUID;

public class NullUtils {

    public static boolean isNull(Boolean b) {
        return b == null ? false : b;
    }

    public static boolean isNull(String b) {
        return b == null || b.isEmpty();
    }

    public static boolean isNull(Long b) {
        return b == null;
    }

    public static boolean isNull(Integer b) {
        return b == null;
    }

    public static boolean isNull(UUID b) {
        return b == null;
    }
}
