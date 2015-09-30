package mx.shellcore.android.twitterapi.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static String setDateFormat(String date) {
        String str = removeTimeZone(date);

        String strData = null;
        TimeZone tzUTC = TimeZone.getTimeZone("UTC");
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US);
        formatoEntrada.setTimeZone(tzUTC);
        SimpleDateFormat formatoSalida = new SimpleDateFormat("EEE, yy/MM/dd, HH:mm");

        try {
            strData = formatoSalida.format(formatoEntrada.parse(str));
        } catch (ParseException e) {
            Log.e("Error parser data", Log.getStackTraceString(e));
        }

        return strData;
    }

    private static String removeTimeZone(String data) {
        return data.replaceFirst("(\\s[+|-]\\d{4})", "");
    }
}
