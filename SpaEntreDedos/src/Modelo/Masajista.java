package Modelo;

/**
 *
 * @author Heber Gomez
 */
public class Masajista {

    private int matricula;
    private String nombreYapellido;
    private int telefono;
    private String especialidad;
    private boolean activo;

    public Masajista () {
    }

    public Masajista (String nombreYapellido, int telefono, String especialidad, boolean activo) {
        this.nombreYapellido = nombreYapellido;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.activo = activo;
    }

    public Masajista (int matricula, String nombreYapellido, int telefono, String especialidad, boolean activo) {
        this.matricula = matricula;
        this.nombreYapellido = nombreYapellido;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.activo = activo;
    }

    public int getMatricula () {
        return matricula;
    }

    public void setMatricula (int matricula) {
        this.matricula = matricula;
    }

    public String getNombreYapellido () {
        return nombreYapellido;
    }

    public void setNombreYapellido (String nombreYapellido) {
        this.nombreYapellido = nombreYapellido;
    }

    public int getTelefono () {
        return telefono;
    }

    public void setTelefono (int telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad () {
        return especialidad;
    }

    public void setEspecialidad (String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isActivo () {
        return activo;
    }

    public void setActivo (boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString () {
        return "Masajista{" + "matricula=" + matricula + ", nombreYapellido="
            + nombreYapellido + ", telefono=" + telefono + ", especialidad="
            + especialidad + ", activo=" + activo + '}';
    }
}
