package controller;

import dao.UsuarioDAO;
import dao_impl.ComentarioDAOImpl;
import dao_impl.UsuarioDAOImpl;
import model.Usuario;
import service.ComentarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import model.SessionUser;
import service.GamificationService;

@WebServlet(urlPatterns = {"/eventos/comentar", "/eventos/comentario/eliminar"})
public class ComentarioController extends HttpServlet {

    private final ComentarioService comentarioService;

    public ComentarioController() {
        UsuarioDAO uDao = new UsuarioDAOImpl();
        GamificationService gService = new GamificationService(uDao);

        this.comentarioService = new ComentarioService(
                new ComentarioDAOImpl(),
                gService
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        try {
            SessionUser u = (SessionUser) req.getSession().getAttribute("user");
            if (u == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            long idUsuario = u.getIdUsuario();

            if ("/eventos/comentar".equals(path)) {
                long idEvento = Long.parseLong(req.getParameter("idEvento"));
                String texto = req.getParameter("texto");
                comentarioService.crear(idEvento, u.getIdUsuario(), texto);
                resp.sendRedirect(req.getContextPath() + "/eventos/detalle?id=" + idEvento);
            } else if ("/eventos/comentario/eliminar".equals(path)) {
                long idComentario = Long.parseLong(req.getParameter("idComentario"));
                long idEvento = Long.parseLong(req.getParameter("idEvento"));
                boolean esAdmin = "ADMIN".equalsIgnoreCase(u.getRol());
                comentarioService.eliminar(idComentario, u.getIdUsuario(), esAdmin);
                resp.sendRedirect(req.getContextPath() + "/eventos/detalle?id=" + idEvento);
            } else {
                resp.sendError(404);
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
