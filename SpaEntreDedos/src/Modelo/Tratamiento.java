package Modelo;

/**
 *
 * @author Heber Gomez
 */
public class Tratamiento {

    private int codTratam;
    private String nombre;
    private TipoDeTratamiento tipo;
    private String detalle;
    private int duracion;
    private double costo;
    private String activo;

    public Tratamiento () {
    }

    public Tratamiento (String nombre, TipoDeTratamiento tipo, String detalle, int duracion, double costo, String activo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.detalle = detalle;
        this.duracion = duracion;
        this.costo = costo;
        this.activo = activo;
    }

    public Tratamiento (int codTratam, String nombre, TipoDeTratamiento tipo, String detalle, int duracion, double costo, String activo) {
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

    public TipoDeTratamiento getTipo () {
        return tipo;
    }

    public void setTipo (TipoDeTratamiento tipo) {
        this.tipo = tipo;
    }

    public String getDetalle () {
        return detalle;
    }

    public void setDetalle (String detalle) {
        this.detalle = detalle;
    }

    public int getDuracion () {
        return duracion;
    }

    public void setDuracion (int duracion) {
        this.duracion = duracion;
    }

    public double getCosto () {
        return costo;
    }

    public void setCosto (double costo) {
        this.costo = costo;
    }

    public String getActivo () {
        return activo;
    }

    public void setActivo (String activo) {
        this.activo = activo;
    }

    @Override
    public String toString () {
        return "Tratamiento{" + "codTratam=" + codTratam + ", nombre=" + nombre
            + ", tipo=" + tipo + ", detalle=" + detalle + ", duracion="
            + duracion + ", costo=" + costo + ", activo=" + activo + '}';
    }
}
