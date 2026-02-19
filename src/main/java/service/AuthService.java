package service;

import dao.PersonaDAO;
import dao.UsuarioDAO;
import model.Persona;
import model.Usuario;
import util.PasswordHasher;

public class AuthService {

    private final PersonaDAO personaDAO;
    private final UsuarioDAO usuarioDAO;

    public AuthService(PersonaDAO pDao, UsuarioDAO uDao) {
        this.personaDAO = pDao;
        this.usuarioDAO = uDao;
    }

    public long register(Persona p, String rol, String plainPassword) throws Exception {
        // Validaciones mínimas
        if (p.getEmail() == null || !p.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        if (plainPassword == null || plainPassword.length() < 8) {
            throw new IllegalArgumentException("Contraseña insegura");
        }
        if (personaDAO.existsByEmail(p.getEmail())) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        long idPersona = personaDAO.create(p);

        Usuario u = new Usuario();
        u.setIdPersona(Math.toIntExact(idPersona));
        u.setRol(rol == null ? "asistente" : rol); // por defecto asistente
        u.setPasswordHash(PasswordHasher.hash(plainPassword));

        long idUsuario = usuarioDAO.create(u);
        return idUsuario;
    }

    public Usuario login(String email, String plainPassword) throws Exception {
        Usuario u = usuarioDAO.findByEmail(email);
        if (u == null) {
            return null;
        }
        if (!PasswordHasher.verify(plainPassword, u.getPasswordHash())) {
            return null;
        }
        usuarioDAO.updateUltimoLogin(u.getIdUsuario());
        return u;
    }
}
