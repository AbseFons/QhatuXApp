package controller;

import dao.PersonaDAO;
import dao.UsuarioDAO;
import dao_impl.PersonaDAOImpl;
import dao_impl.UsuarioDAOImpl;
import model.Persona;
import model.Usuario;
import util.PasswordHasher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(urlPatterns = {"/login", "/registro", "/logout"})
public class AuthController extends HttpServlet {

    private final PersonaDAO personaDAO = new PersonaDAOImpl();
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String path = request.getServletPath();

        switch (path) {
            case "/login":
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
                break;

            case "/registro":
                request.getRequestDispatcher("/WEB-INF/views/auth/registro.jsp").forward(request, response);
                break;

            case "/logout":
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                response.sendRedirect(request.getContextPath() + "/login");
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        try {
            switch (path) {
                case "/login":
                    login(request, response);
                    break;
                case "/registro":
                    registro(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error interno: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/auth/error.jsp").forward(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Usuario u = usuarioDAO.findByEmail(email);

        if (u == null) {
            request.setAttribute("error", "Usuario no encontrado.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            return;
        }

        boolean valido = PasswordHasher.verify(password, u.getPasswordHash());

        if (!valido) {
            request.setAttribute("error", "Contraseña incorrecta.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            return;
        }

        // Recuperar Persona vinculada al usuario
        Persona p = personaDAO.findById(u.getIdPersona());
        if (p == null) {
            request.setAttribute("error", "No se encontró información personal del usuario.");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            return;
        }

        // Actualizar último login
        usuarioDAO.updateUltimoLogin(u.getIdUsuario());

        // Crear DTO de sesión
        String nombreMostrar = (p.getNombres() != null ? p.getNombres() : "");
        if (p.getApellidos() != null && !p.getApellidos().isBlank()) {
            nombreMostrar = (nombreMostrar + " " + p.getApellidos()).trim();
        }
        model.SessionUser su = new model.SessionUser(
                        u.getIdUsuario(),
                        u.getIdPersona(),
                        nombreMostrar.isBlank() ? p.getEmail() : nombreMostrar,
                        u.getRol()
                );

        // Crear sesión
        HttpSession session = request.getSession(true);
        session.setAttribute("user", su);
        // Puedes quitar la 'persona' si ya no la usas en vistas:
        session.setAttribute("persona", p);

        response.sendRedirect(request.getContextPath() + "/home");

    }

    private void registro(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // Datos del formulario
        String tipoDoc = request.getParameter("tipoDocumento");
        String nroDoc = request.getParameter("nroDocumento");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String pais = request.getParameter("pais");
        String ciudad = request.getParameter("ciudad");
        String password = request.getParameter("password");

        // Validaciones básicas
        if (personaDAO.existsByEmail(email)) {
            request.setAttribute("error", "El correo ya está registrado.");
            request.getRequestDispatcher("/WEB-INF/views/auth/registro.jsp").forward(request, response);
            return;
        }

        // Crear persona
        Persona p = new Persona();
        p.setTipoDocumento(tipoDoc);
        p.setNroDocumento(nroDoc);
        p.setNombres(nombres);
        p.setApellidos(apellidos);
        p.setEmail(email);
        p.setTelefono(telefono);
        p.setPais(pais);
        p.setCiudad(ciudad);
        p.setEstado("A");
        p.setFechaRegistro(LocalDate.now());

        long idPersona = personaDAO.create(p);

        // Crear usuario vinculado
        Usuario u = new Usuario();
        u.setIdPersona((int) idPersona);
        u.setRol("USUARIO");
        u.setPasswordHash(PasswordHasher.hash(password));

        usuarioDAO.create(u);

        // Redirige al login
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
