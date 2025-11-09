package Modelo;

/**
 *
 * @author Nehuen Zerdá
 */
public class Consultorio {
    
    // --- Atributos ---
    private int nroConsultorio;
    private String usos;          // facial, corporal, relajacion o estetico
    private String equipamiento;  // descripcion del equipamiento disponible
    private String apto;          // "si" o "no"
    
    
    // --- Constructores ---
    
    // Constructor vacío (necesario para instancias sin datos iniciales)
    public Consultorio() {
    }

    // Constructor con todos los campos (para crear objetos directamente)
    public Consultorio(int nroConsultorio, String usos, String equipamiento, String apto) {
        this.nroConsultorio = nroConsultorio;
        this.usos = usos;
        this.equipamiento = equipamiento;
        this.apto = apto;
    }
    
    
    public int getNroConsultorio() {
        return nroConsultorio;
    }

    public void setNroConsultorio(int nroConsultorio) {
        this.nroConsultorio = nroConsultorio;
    }

    public String getUsos() {
        return usos;
    }

    public void setUsos(String usos) {
        this.usos = usos;
    }

    public String getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(String equipamiento) {
        this.equipamiento = equipamiento;
    }

    public String getApto() {
        return apto;
    }

    public void setApto(String apto) {
        this.apto = apto;
    }
   
    // --- Metodos Utiles ---
    @Override
    public String toString() {
        return "Consultorio Nº " + nroConsultorio + " (" + usos + ")";
    }

}
