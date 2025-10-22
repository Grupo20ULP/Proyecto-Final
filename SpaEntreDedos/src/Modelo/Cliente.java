package Modelo;

/**
 *
 * @author Carreño Lucas (Alias, Kick-boxers)
 */


public class Cliente {

///////////////// Tributos ;) ////////////////

    private int codCli;
    private int dni;
    private String nombre_completo;
    private String telefono;
    private int edad;
    private String afecciones;
    private String estado;
    
//////////// Construyendo-endo /////////////////

    public Cliente() {
    }
    
    public Cliente(int codCli, int dni, String nombre_completo, String telefono, int edad, String afecciones, String estado) {
        this.codCli = codCli;
        this.dni = dni;
        this.nombre_completo = nombre_completo;
        this.telefono = telefono;
        this.edad = edad;
        this.afecciones = afecciones;
        this.estado = estado;
    }

///////////////////////---GETERS---///////////////////////
    
    public int getCodCli() {
        return codCli;
    }

    public int getDni() {
        return dni;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public String getTelefono() {
        return telefono;
    }
    
    public int getEdad() {
        return edad;
    }

    public String getAfecciones() {
        return afecciones;
    }
    
    public String getEstado() {
        return estado;
    }
    
////////////////////---SETTERS---////////////////////////
    
    public void setCodCli(int codCli) {
        this.codCli = codCli;
    }
    
    public void setDni(int dni) {
        this.dni = dni;
    }
    
    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setAfecciones(String afecciones) {
        this.afecciones = afecciones;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

/////////////////// TO-STREEMER /////// ;) ///////////
    @Override
    public String toString() {
        return "Cliente{" + "codCli= " + codCli + ", dni= " + dni + ", nombre_completo= " + nombre_completo + '}';
    }
}