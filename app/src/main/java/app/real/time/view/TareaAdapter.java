package app.real.time.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.real.time.R;
import app.real.time.entity.Tarea;

/**
 * Created by alejandrolozanomedina on 6/12/16.
 */
public class TareaAdapter extends ArrayAdapter<Tarea> {

    int color;

    public TareaAdapter(Context context, ArrayList<Tarea> arrTarea, int color) {
        super(context, 0, arrTarea);
        this.color = color;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tarea tarea = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ly_relative_tarea, parent, false);
        }
        toggleCheck(convertView, tarea);
        return convertView;
    }

    public static void toggleCheck(View convertView, Tarea tarea){
        ImageView imgView = (ImageView) convertView.findViewById(R.id.imgViewCheck);
        TextView txtViewTarea = (TextView) convertView.findViewById(R.id.textVwTask);
        txtViewTarea.setText(tarea.getTarea());
        if(tarea.isDayIsCheck()){
            imgView.setVisibility(View.VISIBLE);
        }else{
            imgView.setVisibility(View.INVISIBLE);
        }
    }
}
