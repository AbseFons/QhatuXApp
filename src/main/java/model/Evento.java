package model;

import java.time.LocalDateTime;
import java.util.Date;
import java.sql.Timestamp;

public class Evento {
    // GARANTIZADOS (no null)

    private Long idEvento;           // PK
    private Long idOrganizador;      // FK a Usuario
    private String nombre;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private double precio;
    private Boolean esDestacado;

    // OPCIONALES (sí pueden ser null)
    private Long idCategoria;        // puede ser null
    private Integer aforo;           // puede ser null
    private String descripcion;
    private String pais;
    private String ciudad;
    private String direccion;
    private String estadoPublicacion; // 'borrador','publicado','oculto'
    private String urlFoto;

    // Constructor vacío
    public Evento() {
    }

    // Constructor completo
    public Evento(Long idEvento, Long idOrganizador, String nombre, Long idCategoria, String descripcion,
            LocalDateTime fechaInicio, LocalDateTime fechaFin, String pais, String ciudad,
            String direccion, String estadoPublicacion, double precio, int aforo, String urlFoto) {
        this.idEvento = idEvento;
        this.idOrganizador = idOrganizador;
        this.nombre = nombre;
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.pais = pais;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.estadoPublicacion = estadoPublicacion;
        this.precio = precio;
        this.aforo = aforo;
        this.urlFoto = urlFoto;
    }

    public Date getFechaInicioDate() {
        return fechaInicio != null ? Timestamp.valueOf(fechaInicio) : null;
    }

    public Date getFechaFinDate() {
        return (fechaFin == null) ? null : Timestamp.valueOf(fechaFin);
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public Long getIdOrganizador() {
        return idOrganizador;
    }

    public void setIdOrganizador(Long idOrganizador) {
        this.idOrganizador = idOrganizador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // IMPORTANTES: opcionales devuelven WRAPPER
    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getAforo() {
        return aforo;
    }

    public void setAforo(Integer aforo) {
        this.aforo = aforo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstadoPublicacion() {
        return estadoPublicacion;
    }

    public void setEstadoPublicacion(String estadoPublicacion) {
        this.estadoPublicacion = estadoPublicacion;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Boolean getEsDestacado() {
        return esDestacado;
    }

    public void setEsDestacado(Boolean esDestacado) {
        this.esDestacado = esDestacado;
    }
}
