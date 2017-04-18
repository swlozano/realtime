package app.real.time.entity;

import java.util.Date;

/**
 * Created by alejandrolozanomedina on 16/12/16.
 */
public class MyCalendar {

    private int idTarea;
    private Date fecha;
    private boolean isDeadDay;
    

    public MyCalendar(Date fecha, boolean isDeadDay, int idTarea) {
        this.fecha = fecha;
        this.isDeadDay = isDeadDay;
        this.idTarea = idTarea;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isDeadDay() {
        return isDeadDay;
    }

    public void setDeadDay(boolean deadDay) {
        isDeadDay = deadDay;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }
}
