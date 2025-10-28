package Modelo;

import java.time.LocalDateTime;

/**
 *
 * @author Federico Galan
 */
public class Sesion {
    private int codSesion;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Tratamiento tratamiento;
    private Consultorio consultorio;
    private Masajista masajista;
    private Dia_De_Spa diaSpa;
    private Instalacion instalacion;
    private String estado; // "pendiente", "confirmada", "realizada", "cancelada" !important CaseSensitive
    
    
    public Sesion() {
    }
    
    
    public Sesion(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, 
                  Tratamiento tratamiento, Masajista masajista, Dia_De_Spa diaSpa, String estado) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.tratamiento = tratamiento;
        this.masajista = masajista;
        this.diaSpa = diaSpa;
        this.estado = estado;
    }
    
    
    public Sesion(int codSesion, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin,
                  Tratamiento tratamiento, Consultorio consultorio, Masajista masajista,
                  Dia_De_Spa diaSpa, Instalacion instalacion, String estado) {
        this.codSesion = codSesion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.tratamiento = tratamiento;
        this.consultorio = consultorio;
        this.masajista = masajista;
        this.diaSpa = diaSpa;
        this.instalacion = instalacion;
        this.estado = estado;
    }

    public int getCodSesion() {
        return codSesion;
    }

    public void setCodSesion(int codSesion) {
        this.codSesion = codSesion;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public Masajista getMasajista() {
        return masajista;
    }

    public void setMasajista(Masajista masajista) {
        this.masajista = masajista;
    }

    public Dia_De_Spa getDiaSpa() {
        return diaSpa;
    }

    public void setDiaSpa(Dia_De_Spa diaSpa) {
        this.diaSpa = diaSpa;
    }

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /*METODOS PARA USAR */

    public long calcularDuracionMinutos() {
        if (fechaHoraInicio != null && fechaHoraFin != null) {
            java.time.Duration duracion = java.time.Duration.between(fechaHoraInicio, fechaHoraFin);
            return duracion.toMinutes();
        }
        return 0;
    }

    public boolean estaActiva() {
        return "pendiente".equalsIgnoreCase(estado) || "confirmada".equalsIgnoreCase(estado);
    }

    public boolean esModificable() {
        return "pendiente".equalsIgnoreCase(estado) || "confirmada".equalsIgnoreCase(estado);
    }

    public boolean seSuperponeCon(LocalDateTime inicio, LocalDateTime fin) {
        return (fechaHoraInicio.isBefore(fin) && fechaHoraFin.isAfter(inicio));
    }

    public double calcularCostoTotal() {
        double costoTotal = 0;
        
        if (tratamiento != null) {
            costoTotal += tratamiento.getCosto();
        }
        
        if (instalacion != null) {
            // Calcular costo de instalación basado en la duración
            long duracionMinutos = calcularDuracionMinutos();
            if (duracionMinutos > 0) {
                costoTotal += instalacion.calcularPrecio((int) duracionMinutos);
            }
        }
        
        return costoTotal;
    }  

    @Override
    public String toString() {
        return "Sesion{" + 
               "codSesion= " + codSesion + 
               ", fechaHoraInicio= " + fechaHoraInicio + 
               ", fechaHoraFin= " + fechaHoraFin + 
               ", tratamiento= " + (tratamiento != null ? tratamiento.getNombre() : "No Encontrada") +
               ", consultorio= " + (consultorio != null ? consultorio.getNroConsultorio() : "No Encontrada") +
               ", masajista= " + (masajista != null ? masajista.getNombreYapellido() : "No Encontrada") +
               ", instalacion= " + (instalacion != null ? instalacion.getNombre() : "No Encontrada") +
               ", estado= " + estado + 
               '}';
    }

    public boolean masajistaEsEspecialista() {
        if (masajista != null && tratamiento != null) {
            return masajista.getEspecialidad().equalsIgnoreCase(tratamiento.getTipo().name());
        }
        return false;
    }
}
