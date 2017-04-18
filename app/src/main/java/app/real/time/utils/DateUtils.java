package app.real.time.utils;

import android.nfc.Tag;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by alejandrolozanomedina on 24/10/16.
 */
public class DateUtils {

    private static final String TAG = DateUtils.class.getCanonicalName();


    /**
     * Pone en ceros las horas, los minutos los segundos, los milisegundos y el am_pm en am
     * de una fecha determinada
     * @param date
     * @return
     */
    public static Date resetTimeOnDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.AM_PM,calendar.AM);
        date.setTime(calendar.getTimeInMillis());
        return date;
    }

    /**
     * Obtiene la diferencia en minutos entre dos fechas.
     * @param dateStart
     * @param dateEnd
     * @param resetTime
     * @return
     */
    public static long getDateDifference(Date dateStart, Date dateEnd, boolean resetTime) {
        if(resetTime){
            dateStart = resetTimeOnDate(dateStart);
            dateEnd = resetTimeOnDate(dateEnd);
        }
        long diffInMillies = dateEnd.getTime() - dateStart.getTime();
        return TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

    /**
     * Convierte un String a Date
     * @param strDate
     * @return
     */
    public static Date convertStringToDate(String strDate){
        long lgDate = Long.parseLong(strDate);
        return new Date(lgDate);
    }

    /**
     * Pone en ceros las horas, los minutos los segundos, los milisegundos y el am_pm en am
     * de una fecha determinada.
     * @param date
     * @return
     */
    public static long normalizeDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * Crea un Date a partir del año, el mes, el dia del mes, la hora, los minutos, los segundos, los milisegundos y  el am_pm.
     * @param year
     * @param month
     * @param dayOfMonth
     * @param hour
     * @param minute
     * @param second
     * @param milliSecond
     * @param amPm
     * @return
     */
    public static Date createDate(int year, int month, int dayOfMonth, int hour, int minute, int second, int milliSecond, int amPm){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.AM_PM, amPm);
        calendar.set(Calendar.MILLISECOND, milliSecond);
        calendar.set(year,month,dayOfMonth,hour,minute,second);
        Date date = new Date(calendar.getTimeInMillis());
        return date;
    }

    /**
     * Formatea una fecha con el formato [hora] hrs [minutos] mins
     * ejemplo 0 hrs 00 mins.
     * @param minutes
     * @return
     */
    public static String formatDateDiff(long minutes){
        String txt="";

        int hr = (int) (minutes/60);
        int min = (int) (minutes%60);
        if(hr!=0||min!=0) {
            if(hr>0) {
                txt += hr > 0 ? hr : "0";
                txt += " hrs ";
            }
            if(min>0) {
                txt += min > 0 ? "" + min : "00";
                txt += " mins ";
            }
        }else{
            txt="0";
        }
        return txt;
    }

    /**
     * Incrementa una fecha según los dias a incrementar específicados
     * @param date
     * @param daysToIncrement
     * @return
     */
    public static Date incrementDays(Date date, int daysToIncrement){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, daysToIncrement);  // number of days to add
        return c.getTime();
    }

    /**
     * Ordena un arreglo de tipo Date de la fecha más antigua a la fecha más reciente.
     * @param arrDate
     * @return
     */
    public static Date[] orderArrays(Date[] arrDate){
        if(arrDate!=null) {
            int subIndex=0;
            long date;
            Date auxElement = null;
            for (int index = subIndex; index < arrDate.length; index++) {
                date = arrDate[index].getTime();
                if(index < arrDate.length-1 && date>arrDate[index+1].getTime()){
                    auxElement = arrDate[index+1];
                    arrDate[index] = auxElement;
                    arrDate[index+1] = new Date(date);
                    index =-1;
                }
                if(index==arrDate.length-1){
                    subIndex++;
                }
            }
        }
        return arrDate;
    }


    /**
     * Ordena una arreglo de tipo long de menor a mayor
     * @param arrDate
     * @return
     */
    public static long[] orderArraysTest(long[] arrDate){
        if(arrDate!=null) {
            long date;
            long auxElement = 0;
            int subIndex=0;
            for (int index = subIndex; index < arrDate.length; index++) {
                date = arrDate[index];
                if(index < arrDate.length-1 && date>arrDate[index+1]){
                    auxElement = arrDate[index+1];
                    arrDate[index] = auxElement;
                    arrDate[index+1] = date;
                    index =-1;
                }
                if(index==arrDate.length-1){
                    subIndex++;
                }
            }
        }
        return arrDate;
    }


}
