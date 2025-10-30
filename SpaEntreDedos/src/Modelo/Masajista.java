package Modelo;

/**
 *
 * @author Heber Gomez
 */
public class Masajista {

    private int matricula;
    private String nombreYapellido;
    private String telefono;
    private String especialidad;
    private String estado;

    public Masajista () {
    }

    public Masajista (int matricula) {
        this.matricula = matricula;
    }

    public Masajista (String nombreYapellido, String telefono, String especialidad, String estado) {
        this.nombreYapellido = nombreYapellido;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public Masajista (int matricula, String nombreYapellido, String telefono, String especialidad, String estado) {
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

    public String getTelefono () {
        return telefono;
    }

    public void setTelefono (String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad () {
        return especialidad;
    }

    public void setEspecialidad (String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEstado () {
        return estado;
    }

    public void setEstado (String estado) {
        this.estado = estado;
    }

    @Override
    public String toString () {
        return "Masajista{" + "matricula=" + matricula + ", nombreYapellido="
            + nombreYapellido + ", telefono=" + telefono + ", especialidad="
            + especialidad + ", estado=" + estado + '}';
    }
}
