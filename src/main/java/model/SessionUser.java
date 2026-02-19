package model;

public class SessionUser {

    private final long idUsuario;
    private final long idPersona;
    private final String nombre;  // muestra en header
    private final String rol;     // 'admin' | 'organizador' | 'asistente'

    public SessionUser(long idUsuario, long idPersona, String nombre, String rol) {
        this.idUsuario = idUsuario;
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.rol = rol;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public long getIdPersona() {
        return idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }
}
