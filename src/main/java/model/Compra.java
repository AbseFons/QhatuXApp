package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Compra {

    private long idCompra;
    private long idUsuario;
    private long idEvento;
    private int cantidad;
    private Double precioUnitario;
    private LocalDateTime fechaCompra;
    private String estado;

    /*--------Evento---------*/
    
    private LocalDateTime fechaEvento;
    private String nombreEvento;

    /*---------Métodos---------*/
    
    public String getCodigoTicket() {
        if (idCompra == 0) {
            return null;
        }
        int year;
        if (fechaCompra != null) {
            year = fechaCompra.getYear();
        } else {
            year = LocalDate.now().getYear();
        }
        // Formato: QX-2025-123
        return String.format("QX-%d-%d", year, idCompra);
    }

    public Double getPrecioTotal() {
        return (precioUnitario != null ? precioUnitario : 0.0) * cantidad;
    }

    public Date getFechaCompraDate() {
        return fechaCompra != null ? Timestamp.valueOf(fechaCompra) : null;
    }

    public Date getFechaEventoDate() {
        return fechaEvento != null ? Timestamp.valueOf(fechaEvento) : null;
    }

    
    /*--------Setters---------*/
    
    public long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(long idCompra) {
        this.idCompra = idCompra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(long idEvento) {
        this.idEvento = idEvento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public LocalDateTime getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(LocalDateTime fechaEvento) {
        this.fechaEvento = fechaEvento;
    }
}
