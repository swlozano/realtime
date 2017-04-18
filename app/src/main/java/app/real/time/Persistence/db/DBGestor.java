package app.real.time.Persistence.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import app.real.time.entity.Tarea;
import app.real.time.utils.DateUtils;

/**
 * Created by alejandrolozanomedina on 6/12/16.
 */
public class DBGestor extends SQLiteOpenHelper {

    private final String LOG_TAG = this.getClass().getName();

    public static final String DATABASE_NAME = "realTime.db";
    public static final String TABLE_TAREA = "tarea";//Es la tabla
    public static final String TABLE_CHECKING_DIA = "checkDia";//Es la tabla
    public static final String COLUMN_TAREA = "tarea";//Es el nombre del campo.
    public static final String COLUMN_DESCRIP = "descripcion";
    public static final String COLUMN_FECHA_TAREA = "fechaTarea";
    public static final String COLUMN_ID_TAREA = "idTarea";
    private HashMap hp;

    public DBGestor(Context context) {
        super(context, DATABASE_NAME , null, 3);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_TAREA +
                        "(id integer primary key, " + COLUMN_TAREA + " text," +COLUMN_DESCRIP + " text)"
        );
        db.execSQL(
                "create table " + TABLE_CHECKING_DIA +
                        "(id integer primary key, idTarea integer, fechaTarea text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAREA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKING_DIA);
        onCreate(db);
    }

    public boolean isDayIsCheck(int idTarea, long fecha){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_CHECKING_DIA +" where " + COLUMN_ID_TAREA + " = "+ idTarea + " AND " + COLUMN_FECHA_TAREA + " = " + fecha
                , null );
        res.moveToFirst();
        return res.getCount()!=0?true:false;
    }

    public boolean deleteCheckDia(int idTarea, long fecha){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean resp = db.delete(TABLE_CHECKING_DIA,COLUMN_ID_TAREA +" =? and " + COLUMN_FECHA_TAREA+" =? ",new String[]{idTarea+"",fecha+""})>0?true:false;
        //return db.delete(TABLE_CHECKING_DIA, COLUMN_ID_TAREA + "=" + idTarea, null) > 0;
        return resp;
    }

    private int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_TAREA);
        return numRows;
    }

    public long insertTarea(String tarea, String descripcion)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TAREA, tarea);
        contentValues.put(COLUMN_DESCRIP, descripcion);
        return db.insert(TABLE_TAREA, null, contentValues);
    }

    public boolean insertCheckTarea(int idTarea, Date fecha){
        long lgFecha = DateUtils.normalizeDate(fecha);
        if(!isDayIsCheck(idTarea, lgFecha)){
            return insertCheckDia(idTarea,lgFecha);
        }else{
            return deleteCheckDia(idTarea,lgFecha);
        }
    }

    public boolean insertCheckDia(int idTarea, long fecha)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID_TAREA, idTarea);
        contentValues.put(COLUMN_FECHA_TAREA, fecha + "");
        db.insert(TABLE_CHECKING_DIA, null, contentValues);
        return true;
    }

   public Date[] getCheckDiasById(int idTarea)
    {
        Date arrDaysCheck[]=null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_CHECKING_DIA +" where " + COLUMN_ID_TAREA + "="+ idTarea
                , null );

        if(res.getCount()>0) {
            int count  =0;
            arrDaysCheck = new Date[res.getCount()];
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                long lgFecha = Long.parseLong(res.getString(res.getColumnIndex(COLUMN_FECHA_TAREA)));
                arrDaysCheck[count] = new Date(lgFecha);
                count++;
                res.moveToNext();
            }
        }
        DateUtils.orderArrays(arrDaysCheck);
        return arrDaysCheck;
    }

    public ArrayList<Tarea> getTasks(){
        ArrayList<Tarea> array_list = new ArrayList<Tarea>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_TAREA , null );
        res.moveToFirst();
        Tarea tarea=null;
        long  currentDate = DateUtils.normalizeDate(new Date());

        while(res.isAfterLast() == false){
            tarea = new Tarea();
            tarea.setDescripcion(res.getString(res.getColumnIndex(COLUMN_DESCRIP)));
            tarea.setTarea(res.getString(res.getColumnIndex(COLUMN_TAREA)));
            tarea.setId(res.getInt(res.getColumnIndex("id")));
            tarea.setDayIsCheck(isDayIsCheck(tarea.getId(), currentDate));
            array_list.add(tarea);
            res.moveToNext();
        }
        return array_list;
    }


}


