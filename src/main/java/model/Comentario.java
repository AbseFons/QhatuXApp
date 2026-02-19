package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class Comentario {

    private long idComentario;
    private long idEvento;
    private long idUsuario;
    private String texto;
    private LocalDateTime fecha;
    private String autorNombre; // opcional, para mostrar en vista

    public Date getFechaDate() {
        return (fecha == null) ? null : Timestamp.valueOf(fecha);
    }
    
    public long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(long idComentario) {
        this.idComentario = idComentario;
    }

    public long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(long idEvento) {
        this.idEvento = idEvento;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getAutorNombre() {
        return autorNombre;
    }

    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }
}
