package Modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan
 */
public class Dia_De_Spa {

    //Atributos (ENCAPSULADOS)
    private int codPack;
    private LocalDateTime fechaHora;
    private String preferencias;
    private Cliente cliente;
    private double monto;
    private String estado; // Ej: "Reservado", "Activo", "Anulado"
    private List<Sesion> sesiones;

    // constructores
    public Dia_De_Spa() {
        this.sesiones = new ArrayList<>();
        this.estado = "Reservado";
    }

    public Dia_De_Spa(int codPack, LocalDateTime fechaHora, String preferencias,
            Cliente cliente, double monto, String estado) {
        this.codPack = codPack;
        this.fechaHora = fechaHora;
        this.preferencias = preferencias;
        this.cliente = cliente;
        this.monto = monto;
        this.estado = estado;
        this.sesiones = new ArrayList<>();
    }

    // get y set
    public int getCodPack() {
        return codPack;
    }

    public void setCodPack(int codPack) {
        this.codPack = codPack;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<Sesion> sesiones) {
        this.sesiones = sesiones;
    }

    // metodos funcionales
    // Agrega una sesion al Dia de Spa 
    public void agregarSesion(Sesion sesion) {
        if (sesiones == null) sesiones = new ArrayList<>();
        sesiones.add(sesion);
        recalcularMonto();
    }

    // Elimina una sesion especifica del Dia de Spa
    public void eliminarSesion(Sesion sesion) {
        if (sesiones != null) {
            sesiones.remove(sesion);
            recalcularMonto();
        }
    }

    // Calcula el monto total sumando los costos de las sesiones 
    public void recalcularMonto() {}
//        double total = 0;
//        if (sesiones != null) {
//            for (Sesion s : sesiones) {
//                total += s.getCosto();   //GetCosto LO SACO DE  SESION UNA VEZ CREADA LA CLASE
//            }
//        }
//        this.monto = total;
//    }
    
     //Devuelve un resumen del Dia de Spa con todas sus sesiones
    public String listarItinerario() {
        StringBuilder sb = new StringBuilder();
        sb.append("Día de Spa N° ").append(codPack)
          .append(" | Cliente: ").append(cliente.getNombre_completo()) 
          .append("\nFecha: ").append(fechaHora)
          .append("\nPreferencias: ").append(preferencias)
          .append("\nEstado: ").append(estado)
          .append("\n--- Sesiones ---\n");

        if (sesiones.isEmpty()) {
            sb.append("Sin sesiones registradas.\n");
        } else {
            for (Sesion s : sesiones) {
                sb.append(s.toString()).append("\n");
            }
        }

        sb.append("\nMonto Total: $").append(monto);
        return sb.toString();
    }

//    /** Cambia el estado del Día de Spa */
    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    /** Verifica si el Día de Spa contiene una sesión en una fecha/hora específica */
//    public boolean contieneSesion(LocalDateTime fechaInicio) {
//        if (sesiones == null) return false;
//        for (Sesion s : sesiones) {
//            if (s.getFechaHoraInicio().equals(fechaInicio)) return true;  //getFechaHoraInicio LO SACO DE  SESION UNA VEZ CREADA LA CLASE
//        }
//        return false;
//    }

    @Override
    public String toString() {
        return "Día de Spa #" + codPack + " - " + cliente.getNombre_completo()+   //NOMBRE COMPLETO LO SACO DE CLIENTE UNA VEZ CREADA LA CLASE
                " - Estado: " + estado + " - Total: $" + monto;
    }
}


