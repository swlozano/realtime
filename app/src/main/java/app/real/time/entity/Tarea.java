package app.real.time.entity;

/**
 * Created by alejandrolozanomedina on 6/12/16.
 */
public class Tarea {

    private int id;
    private String tarea;
    private String descripcion;
    private long[] checkDias;
    private boolean dayIsCheck;

    public Tarea(){

    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long[] getCheckDias() {
        return checkDias;
    }

    public void setCheckDias(long[] checkDias) {
        this.checkDias = checkDias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDayIsCheck() {
        return dayIsCheck;
    }

    public void setDayIsCheck(boolean dayIsCheck) {
        this.dayIsCheck = dayIsCheck;
    }
}
