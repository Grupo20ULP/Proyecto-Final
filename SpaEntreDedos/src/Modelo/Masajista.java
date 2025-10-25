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
    private boolean estado;

    public Masajista () {
    }

    public Masajista (String nombreYapellido, int telefono, String especialidad, boolean estado) {
        this.nombreYapellido = nombreYapellido;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public Masajista (int matricula, String nombreYapellido, int telefono, String especialidad, boolean estado) {
        this.matricula = matricula;
        this.nombreYapellido = nombreYapellido;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.estado = estado;
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

    public boolean isEstado () {
        return estado;
    }

    public void setEstado (boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString () {
        return "Masajista{" + "matricula=" + matricula + ", nombreYapellido="
            + nombreYapellido + ", telefono=" + telefono + ", especialidad="
            + especialidad + ", estado=" + estado + '}';
    }
}
