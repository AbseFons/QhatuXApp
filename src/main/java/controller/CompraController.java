package controller;

import dao.EventoDAO;
import dao.UsuarioDAO;
import dao_impl.CompraDAOImpl;
import dao_impl.EventoDAOImpl;
import dao_impl.UsuarioDAOImpl;
import model.Evento;
import model.Usuario;
import service.CompraService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import model.SessionUser;
import service.GamificationService;

@WebServlet(urlPatterns = {"/eventos/*", "/mis-compras"})
public class CompraController extends HttpServlet {

    private final EventoDAO eventoDAO = new EventoDAOImpl();
    private final CompraService service;

    public CompraController() {
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        GamificationService gamificationService = new GamificationService(usuarioDAO);

        this.service = new CompraService(
                new CompraDAOImpl(),
                new EventoDAOImpl(),
                gamificationService
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath(); // "/eventos" ó "/mis-compras"
        String pathInfo = req.getPathInfo();       // "/{id}/comprar" o null

        try {
            SessionUser u = (SessionUser) req.getSession().getAttribute("user");
            if (u == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            long idUsuario = u.getIdUsuario();

            // /eventos/{id}/comprar
            if ("/eventos".equals(servletPath) && pathInfo != null && pathInfo.endsWith("/comprar")) {
                String[] parts = pathInfo.split("/"); // ["", "{id}", "comprar"]
                long idEvento = Long.parseLong(parts[1]);
                int cantidad = Integer.parseInt(req.getParameter("cantidad"));

                try {
                    // Aquí puede lanzar IllegalArgumentException o IllegalStateException
                    service.comprar(idUsuario, idEvento, cantidad);

                    // Si todo va bien, rediriges a mis compras
                    resp.sendRedirect(req.getContextPath() + "/mis-compras");
                    return;

                } catch (IllegalArgumentException | IllegalStateException ex) {
                    // Errores "funcionales" (sin stock, cantidad inválida, etc.)
                    // 1. mensaje para el JSP
                    req.setAttribute("errorCompra", ex.getMessage());

                    // 2. recargas el evento para volver al detalle
                    Evento evento = eventoDAO.findById(idEvento);
                    req.setAttribute("evento", evento);

                    // 3. reenvías al mismo JSP de detalle que usas en el GET
                    req.getRequestDispatcher("/WEB-INF/views/eventos/eventos-detalle.jsp")
                            .forward(req, resp);
                    return;
                }
            }

            // Si no matchea ninguna ruta conocida
            resp.sendError(404);

        } catch (Exception e) {
            // Cualquier otro error no previsto (BD caída, NullPointer raro, etc.)
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        try {
            if ("/mis-compras".equals(path)) {
                SessionUser u = (SessionUser) req.getSession().getAttribute("user");
                if (u == null) {
                    resp.sendRedirect(req.getContextPath() + "/login");
                    return;
                }
                long idUsuario = u.getIdUsuario();
                req.setAttribute("compras", service.misCompras(u.getIdUsuario()));
                req.getRequestDispatcher("/WEB-INF/views/compras/mis-compras.jsp").forward(req, resp);
            } else {
                resp.sendError(404);
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
