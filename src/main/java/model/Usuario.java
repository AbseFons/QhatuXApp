package model;

import java.time.LocalDateTime;

public class Usuario {

    private int idUsuario;
    private int idPersona;
    private int puntos;
    private String rol;
    private String passwordHash;
    private LocalDateTime ultimoLogin;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor completo
    public Usuario(int idUsuario, int idPersona, String rol, String passwordHash, LocalDateTime ultimoLogin) {
        this.idUsuario = idUsuario;
        this.idPersona = idPersona;
        this.rol = rol;
        this.passwordHash = passwordHash;
        this.ultimoLogin = ultimoLogin;
    }

    // Método para tener nivel por puntos
    public String getNivel() {
        if (puntos < 50) {
            return "Nuevo";
        } else if (puntos < 150) {
            return "Explorador";
        } else if (puntos < 300) {
            return "Fanatico";
        } else {
            return "Ultra Fanatico";
        }
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }
}
