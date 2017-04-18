package app.real.time.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Date;
import app.real.time.Persistence.db.DBGestor;
import app.real.time.R;
import app.real.time.entity.MyCalendar;
import app.real.time.utils.DateFormatUtils;

/**
 * Created by alejandrolozanomedina on 9/12/16.
 */
public class CalendarAdapter extends BaseAdapter {

    private static  final String TAG = CalendarAdapter.class.getCanonicalName();
    private Context mContext;
    private int itemSizes =0;
    MyCalendar[] arrMyCalendar;
    DBGestor dbGestor;
    GridView gridView;

    /**
     * Constructor ImageAdapter
     * @param c
     * @param itemSizes
     * @param arrMyCalendar
     * @param gridView
     */
    public CalendarAdapter(Context c, int itemSizes, MyCalendar[] arrMyCalendar, GridView gridView) {
        mContext = c;
        this.itemSizes = itemSizes;
        this.arrMyCalendar = arrMyCalendar;
        dbGestor = new DBGestor(c);
        this.gridView = gridView;
    }

    public int getCount() {
        return itemSizes;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }


    /**
     * Crea un nuevo ly_adapter_calendar por cada item referenciado por el Adapter
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ly_adapter_calendar, parent, false);
        }
        setItemOnLayout(convertView,position);
        return convertView;
    }


    private void setItemOnLayout(final View convertView, final int itemIndex){
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCalendar calendar = arrMyCalendar[itemIndex];
                if(arrMyCalendar[itemIndex].isDeadDay()) {
                    if(!dbGestor.isDayIsCheck(arrMyCalendar[itemIndex].getIdTarea(), arrMyCalendar[itemIndex].getFecha().getTime())) {
                        dbGestor.insertCheckDia(arrMyCalendar[itemIndex].getIdTarea(), arrMyCalendar[itemIndex].getFecha().getTime());
                        calendar.setDeadDay(false);
                        printInLayoutGoodDay(convertView, arrMyCalendar[itemIndex].getFecha());
                    }
                }else{
                    dbGestor.deleteCheckDia(arrMyCalendar[itemIndex].getIdTarea(), arrMyCalendar[itemIndex].getFecha().getTime());
                    calendar.setDeadDay(true);
                    printInLayoutDeadDay(convertView, arrMyCalendar[itemIndex].getFecha());
                }
                arrMyCalendar[itemIndex] = calendar;
            }
        });

        if(arrMyCalendar[itemIndex].isDeadDay()){
           printInLayoutDeadDay(convertView,arrMyCalendar[itemIndex].getFecha());
        }else{
            printInLayoutGoodDay(convertView,arrMyCalendar[itemIndex].getFecha());
        }
    }

    private void printInLayoutDeadDay(View convertView, Date date){
        int imageR = R.drawable.smiley_sad;
        int  backGroundColor = R.color.colorBackGroundSadImage;
        printInLayout( convertView,  date,  imageR,  backGroundColor);
    }

    private void printInLayoutGoodDay(View convertView, Date date){
        int imageR = R.drawable.star;
        int  backGroundColor = R.color.colorAccent;
        printInLayout( convertView,  date,  imageR,  backGroundColor);
    }

    /**
     * Obtiene cada View del Adpater y modifica su estado de acuerdo a la fecha.
     * @param convertView
     * @param date
     * @param imageR
     * @param backGroundColor
     */
    private void printInLayout(View convertView, Date date, int imageR, int backGroundColor){
        ImageView img  = (ImageView) convertView.findViewById(R.id.imageViewState);
        TextView txtYear = (TextView) convertView.findViewById(R.id.textViewYear);
        TextView txtMonth = (TextView) convertView.findViewById(R.id.textViewMonth);
        TextView txtDay = (TextView) convertView.findViewById(R.id.textViewDay);
        txtYear.setText(DateFormatUtils.formatDate(date,DateFormatUtils.FORMAT_yyyy));
        txtMonth.setText(DateFormatUtils.formatDate(date,DateFormatUtils.FORMAT_MMM));
        txtDay.setText(DateFormatUtils.formatDate(date,DateFormatUtils.FORMAT_dd));
        if(backGroundColor!=0){
            convertView.setBackgroundColor(ContextCompat.getColor(mContext, backGroundColor));
        }
        img.setImageResource(imageR);
    }

}