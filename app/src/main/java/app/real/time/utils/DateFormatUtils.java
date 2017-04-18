package app.real.time.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alejandrolozanomedina on 13/12/16.
 */
public class DateFormatUtils {

    public static  final String FORMAT_yyyy = "yyyy";
    public static  final String FORMAT_MMM = "MMM";
    public static  final String FORMAT_dd = "dd";

    public static String formatDate(Date date, String format){
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatDate(String date, String format){
        long lgDate = DataTypesUtils.stringToLong(date);
        Date dateToFormat = new Date(lgDate);
        return formatDate(dateToFormat, format);
    }


}
