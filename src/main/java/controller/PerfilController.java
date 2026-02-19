package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Usuario;
import dao.UsuarioDAO;
import dao_impl.UsuarioDAOImpl;
import dao.CompraDAO;
import dao_impl.CompraDAOImpl;
import dao.ComentarioDAO;
import dao.PersonaDAO;
import dao_impl.ComentarioDAOImpl;
import dao_impl.PersonaDAOImpl;
import model.Persona;
import model.SessionUser;

@WebServlet("/mi-perfil")
public class PerfilController extends HttpServlet {

    private final UsuarioDAO usuarioDAO;
    private final CompraDAO compraDAO;
    private final ComentarioDAO comentarioDAO;
    private final PersonaDAO personaDAO;

    public PerfilController() {
        this.usuarioDAO = new UsuarioDAOImpl();
        this.compraDAO = new CompraDAOImpl();
        this.comentarioDAO = new ComentarioDAOImpl();
        this.personaDAO = new PersonaDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            SessionUser sessionUser = (SessionUser) req.getSession().getAttribute("user");
            if (sessionUser == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            long idUsuario = sessionUser.getIdUsuario();

            Usuario usuario = usuarioDAO.findById(idUsuario);
            if (usuario == null) {
                resp.sendError(404);
                return;
            }

            // 👇 Traemos la persona asociada
            long idPersona = usuario.getIdPersona();
            Persona persona = personaDAO.findById(idPersona);

            int totalCompras = compraDAO.countByUsuario(idUsuario);
            int totalComentarios = comentarioDAO.countByUsuario(idUsuario);

            req.setAttribute("usuarioPerfil", usuario);
            req.setAttribute("personaPerfil", persona);
            req.setAttribute("totalCompras", totalCompras);
            req.setAttribute("totalComentarios", totalComentarios);

            req.getRequestDispatcher("/WEB-INF/views/usuario/mi-perfil.jsp")
               .forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
