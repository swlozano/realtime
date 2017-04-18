package app.real.time;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.real.time.Persistence.db.DBGestor;
import app.real.time.entity.Tarea;
import app.real.time.utils.DateUtils;
import app.real.time.view.TareaAdapter;

public class MainActivity extends AppCompatActivity {

    private static  final String TAG = MainActivity.class.getCanonicalName();
    DBGestor dbGestor;
    Button  btnAddTask;
    EditText txtTask;
    ArrayList<Tarea> arrTareas;
    ListView listView;
    public final static String EXTRA_MESSAGE = "extra.id.tarea";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ly_main);
        hiddenInput();
        dbGestor = new DBGestor(getApplicationContext());
        btnAddTask = (Button) findViewById(R.id.btnAddTask);
        txtTask = (EditText) findViewById(R.id.txtTask);
        btnAddTask.setOnClickListener(onClickAddTask);
        //insertSampleData();
        attachListViewAdapter();
    }

    private void hiddenInput(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private View.OnClickListener onClickAddTask= new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            dbGestor.insertTarea(txtTask.getText().toString(),"");
            txtTask.setText("");
            attachListViewAdapter();
        }
    };

    private void attachListViewAdapter(){
        arrTareas = dbGestor.getTasks();
        if(arrTareas!=null || !arrTareas.isEmpty()) {
            TareaAdapter adapter = new TareaAdapter(this, arrTareas,R.color.colorTxt);
            listView = (ListView) findViewById(R.id.listViewTareas);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(onItemClickListener);
            listView.setOnItemLongClickListener(onItemLongClickListener);
        }
    }

    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {
            toggleCheckTare(index);
            return false;
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
            if(arrTareas!=null && !arrTareas.isEmpty())
                lauchCalendarActivity(arrTareas.get(index).getId());
        }
    };

    private void toggleCheckTare(int index){
        Tarea tarea = arrTareas.get(index);
        checkTareaOnDB(tarea.getId());
        tarea.setDayIsCheck(!tarea.isDayIsCheck());
        arrTareas.set(index,tarea);
        updateView(listView, index, tarea);
    }

    private void lauchCalendarActivity(int idTarea){
        Intent intent = new Intent(this, CalendarActivity.class);
        intent.putExtra(EXTRA_MESSAGE, idTarea);
        startActivity(intent);
    }

    private void checkTareaOnDB(int idTarea){
        dbGestor.insertCheckTarea(idTarea, new Date()) ;
    }

    private void updateView(ListView yourListView, int index, Tarea tarea){
        View v = yourListView.getChildAt(index -
                yourListView.getFirstVisiblePosition());
        if(v == null)
            return;
        TareaAdapter.toggleCheck(v,tarea);
    }

    private void insertSampleData(String task){
        long id  = dbGestor.insertTarea(task, "");
        Date date = DateUtils.createDate(2016,11,14,0,0,0,0,Calendar.AM);
        Date date2 = DateUtils.createDate(2016,11,15,0,0,0,0,Calendar.AM);
        Date date3 = DateUtils.createDate(2016,11,16,0,0,0,0,Calendar.AM);
        Date date4 = DateUtils.createDate(2016,11,18,0,0,0,0,Calendar.AM);
        Date date5 = DateUtils.createDate(2016,11,19,0,0,0,0,Calendar.AM);
        Date date6 = DateUtils.createDate(2016,11,22,0,0,0,0,Calendar.AM);
        Date date7 = DateUtils.createDate(2017,0,5,0,0,0,0,Calendar.AM);
        Date[] arrDates = {date, date2, date3, date4, date5, date6,date7};
        for (int i = 0; i < arrDates.length; i++) {
            dbGestor.insertCheckTarea((int) id,arrDates[i]);
        }
    }

}
