package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import dao.CompraDAO;
import dao_impl.CompraDAOImpl;
import dao.EventoDAO;
import dao_impl.EventoDAOImpl;
import model.Compra;
import service.CompraService;
import service.GamificationService; // si lo tienes en este paquete
import dao.UsuarioDAO;
import dao_impl.UsuarioDAOImpl;
import model.SessionUser; // adapta al paquete correcto

@WebServlet("/mi-calendario")
public class CalendarioController extends HttpServlet {

    private final CompraService compraService;

    public CalendarioController() {
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        GamificationService gService = new GamificationService(usuarioDAO);

        this.compraService = new CompraService(
                new CompraDAOImpl(),
                new EventoDAOImpl(),
                gService
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            SessionUser u = (SessionUser) req.getSession().getAttribute("user");
            if (u == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            long idUsuario = u.getIdUsuario();

            List<Compra> futuras = compraService.comprasFuturas(idUsuario);
            List<Compra> pasadas = compraService.comprasPasadas(idUsuario);

            req.setAttribute("comprasFuturas", futuras);
            req.setAttribute("comprasPasadas", pasadas);

            req.getRequestDispatcher("/WEB-INF/views/compras/mi-calendario.jsp")
               .forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
