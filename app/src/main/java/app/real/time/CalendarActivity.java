package app.real.time;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import app.real.time.Persistence.db.DBGestor;
import app.real.time.entity.MyCalendar;
import app.real.time.utils.DateUtils;
import app.real.time.view.CalendarAdapter;

/**
 * Created by alejandrolozanomedina on 8/12/16.
 */
public class CalendarActivity extends AppCompatActivity {

    private static  final String TAG = CalendarActivity.class.getCanonicalName();
    GridView gridView;
    private DBGestor dbGestor;
    int idTarea =0;
    int countDeadDays =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_calendar);
        idTarea = getExtrasDataIdTarea();
        dbGestor = new DBGestor(getApplicationContext());
        GridView gridview = (GridView) findViewById(R.id.gridview);
        //Obtiene los dias checkeados por id
        Date[] arrCheckDays = dbGestor.getCheckDiasById(idTarea);
        if(arrCheckDays!=null) {
            MyCalendar[] arrMyCalendar = getDeadDays(arrCheckDays);
            gridview.setAdapter(new CalendarAdapter(this, arrCheckDays.length + this.countDeadDays, arrMyCalendar,gridview));
            gridview.setOnItemClickListener(onItemClick);
        }
    }

    private AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v,
                                int position, long id) {
            Toast.makeText(CalendarActivity.this, "" + (position+1),
                    Toast.LENGTH_SHORT).show();
        }
    };

    private int getExtrasDataIdTarea(){
        Intent intent = getIntent();
        return intent.getIntExtra(MainActivity.EXTRA_MESSAGE,0);
    }

    private MyCalendar[]  getDeadDays(Date[] arrCheckDays){
        int countDeadDays =0;
        ArrayList<int[]> arrIndexDeadDays = new ArrayList<>();
        for (int itemIndex = 0; itemIndex <  arrCheckDays.length; itemIndex++) {
            if (itemIndex + 1 < arrCheckDays.length) {
                Date dateStart = arrCheckDays[itemIndex];
                Date dateEnd = arrCheckDays[itemIndex + 1];
                int dayOfDifference = (int) ((DateUtils.getDateDifference(dateStart, dateEnd, true) / 60) / 24);
                int[] arrDeadDays;
                if (dayOfDifference - 1 > 0) {
                    //Dead days
                    countDeadDays+=(dayOfDifference - 1);
                    arrDeadDays = new int[]{itemIndex+1, (dayOfDifference - 1)};
                    arrIndexDeadDays.add(arrDeadDays);
                }
            }
        }
        this.countDeadDays = countDeadDays;
        return joinDeadDaysToArray(arrCheckDays,arrIndexDeadDays, countDeadDays);
    }

    private MyCalendar[] joinDeadDaysToArray(Date[] arrCheckDays, ArrayList<int[]> arrIndexDeadDays, int numberOfDeadDays){
            MyCalendar[] arrMyCalendar = new MyCalendar[arrCheckDays.length + numberOfDeadDays];
        int countCkeckDays =0;
        int countIndexDeadDays =0;
        Date dateDead;
        for (int j = 0; j <arrMyCalendar.length;) {
            if(countIndexDeadDays<arrIndexDeadDays.size() && arrIndexDeadDays.get(countIndexDeadDays)[0]==countCkeckDays){
                for (int k = 0; k < arrIndexDeadDays.get(countIndexDeadDays)[1]; k++) {
                    dateDead = DateUtils.incrementDays( arrMyCalendar[j-1].getFecha(),1);
                    arrMyCalendar[j] = new MyCalendar(dateDead,true,idTarea);
                    j++;
                }
                countIndexDeadDays++;
            }else{
                arrMyCalendar[j] = new MyCalendar(arrCheckDays[countCkeckDays],false,idTarea);
                j++;
                if(countCkeckDays<arrCheckDays.length-1)
                    countCkeckDays++;
            }
        }
        return arrMyCalendar;
    }


}
