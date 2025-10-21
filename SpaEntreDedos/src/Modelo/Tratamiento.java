package Modelo;

/**
 *
 * @author Heber Gomez
 */
public class Tratamiento {

    private int codTratam;
    private String nombre;
    private String tipo;
    private String detalle;
    private double duracion;
    private double costo;
    private boolean activo;

    public Tratamiento () {
    }

    public Tratamiento (String nombre, String tipo, String detalle, double duracion, double costo, boolean activo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.detalle = detalle;
        this.duracion = duracion;
        this.costo = costo;
        this.activo = activo;
    }

    public Tratamiento (int codTratam, String nombre, String tipo, String detalle, double duracion, double costo, boolean activo) {
        this.codTratam = codTratam;
        this.nombre = nombre;
        this.tipo = tipo;
        this.detalle = detalle;
        this.duracion = duracion;
        this.costo = costo;
        this.activo = activo;
    }

    public int getCodTratam () {
        return codTratam;
    }

    public void setCodTratam (int codTratam) {
        this.codTratam = codTratam;
    }

    public String getNombre () {
        return nombre;
    }

    public void setNombre (String nombre) {
        this.nombre = nombre;
    }

    public String getTipo () {
        return tipo;
    }

    public void setTipo (String tipo) {
        this.tipo = tipo;
    }

    public String getDetalle () {
        return detalle;
    }

    public void setDetalle (String detalle) {
        this.detalle = detalle;
    }

    public double getDuracion () {
        return duracion;
    }

    public void setDuracion (double duracion) {
        this.duracion = duracion;
    }

    public double getCosto () {
        return costo;
    }

    public void setCosto (double costo) {
        this.costo = costo;
    }

    public boolean isActivo () {
        return activo;
    }

    public void setActivo (boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString () {
        return "Tratamiento{" + "codTratam=" + codTratam + ", nombre=" + nombre
            + ", tipo=" + tipo + ", detalle=" + detalle + ", duracion="
            + duracion + ", costo=" + costo + ", activo=" + activo + '}';
    }
}
