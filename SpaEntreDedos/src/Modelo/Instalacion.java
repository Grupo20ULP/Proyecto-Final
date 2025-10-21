package Modelo;

/**
 * Clase que representa una instalación del spa. para sacarle la jubilacion a las abuelas y que puedan disfrutar de un buen masaje.
 * @author Federico Galan
 */
public class Instalacion {
    private int codInstal;
    private String nombre;
    private String detalleUso;
    private double precio30m;
    private String estado;


    public Instalacion() {
    }

    public Instalacion(String nombre, String detalleUso, double precio30m, String estado) {
        this.nombre = nombre;
        this.detalleUso = detalleUso;
        this.precio30m = precio30m;
        this.estado = estado;
    }

    public Instalacion(int codInstal, String nombre, String detalleUso, double precio30m, String estado) {
        this.codInstal = codInstal;
        this.nombre = nombre;
        this.detalleUso = detalleUso;
        this.precio30m = precio30m;
        this.estado = estado;
    }

    public int getCodInstal() {
        return codInstal;
    }

    public void setCodInstal(int codInstal) {
        this.codInstal = codInstal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalleUso() {
        return detalleUso;
    }

    public void setDetalleUso(String detalleUso) {
        this.detalleUso = detalleUso;
    }

    public double getPrecio30m() {
        return precio30m;
    }

    public void setPrecio30m(double precio30m) {
        this.precio30m = precio30m;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return nombre + " - $" + precio30m + " (30 min)";
    }

    public boolean estaActiva() {
        return "activa".equalsIgnoreCase(estado);
    }
    
    public double calcularPrecio(int minutos) {
        if (minutos <= 0) return 0;
        return (precio30m / 30) * minutos;
    }




}
