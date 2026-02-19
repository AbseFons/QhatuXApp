package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import service.EventoService;
import service.CompraService;
import service.GamificationService;

import dao_impl.EventoDAOImpl;
import dao.CategoriaDAO;
import dao_impl.CategoriaDAOImpl;
import dao.UsuarioDAO;
import dao_impl.UsuarioDAOImpl;
import dao.PersonaDAO;
import dao_impl.PersonaDAOImpl;
import dao.CompraDAO;
import dao_impl.CompraDAOImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Evento;
import model.Usuario;
import model.Persona;
import model.Compra;
import model.SessionUser;

@WebServlet("/home")
public class HomeController extends HttpServlet {

    private final EventoService eventoService = new EventoService(new EventoDAOImpl());
    private final CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    private final PersonaDAO personaDAO = new PersonaDAOImpl();
    private final CompraService compraService;

    public HomeController() {
        // GamificationService no se usa aquí directamente,
        // pero CompraService lo pide en el constructor.
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
            // 1) Eventos destacados
            req.setAttribute("destacados", eventoService.listDestacados(6));

            // 2) Categorías
            req.setAttribute("categorias", categoriaDAO.listAll());

            // 3) Si hay usuario logueado → cargar resumen personalizado
            SessionUser su = (SessionUser) req.getSession().getAttribute("user");
            if (su != null) {
                long idUsuario = su.getIdUsuario();

                Usuario u = usuarioDAO.findById(idUsuario);
                if (u != null) {
                    Persona p = personaDAO.findById(u.getIdPersona());

                    List<Compra> futuras = compraService.comprasFuturas(idUsuario);
                    // Limitar a máximo 3 para el home
                    int max = Math.min(3, futuras.size());
                    List<Compra> proximosHome = futuras.subList(0, max);

                    req.setAttribute("usuarioHome", u);
                    req.setAttribute("personaHome", p);
                    req.setAttribute("proximosEventosHome", proximosHome);
                }
            }

            // 4) Ranking de fans (siempre, esté o no logueado)
            List<Usuario> topFans = usuarioDAO.topByPuntos(5);

            Map<Integer, Persona> personasRanking = new HashMap<>();
            for (Usuario uFan : topFans) {
                int idPersona = uFan.getIdPersona();
                Persona pFan = personaDAO.findById(idPersona);
                personasRanking.put(idPersona, pFan);
            }

            req.setAttribute("topFans", topFans);
            req.setAttribute("personasRanking", personasRanking);

            req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
