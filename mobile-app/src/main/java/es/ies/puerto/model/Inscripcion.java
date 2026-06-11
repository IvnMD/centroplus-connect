package es.ies.puerto.model;

public class Inscripcion {
    private Long id;
    private Long idUsuario;
    private Long idActividad;
    private String fecha;
    private String estado;

    public Inscripcion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdActividad() { return idActividad; }
    public void setIdActividad(Long idActividad) { this.idActividad = idActividad; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() { return id + " - Usuario:" + idUsuario + " Actividad:" + idActividad; }
}
