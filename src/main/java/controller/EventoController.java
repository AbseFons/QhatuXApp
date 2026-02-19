package controller;

import dao.CategoriaDAO;
import dao_impl.CategoriaDAOImpl;
import dao_impl.ComentarioDAOImpl;
import dao_impl.EventoDAOImpl;
import model.Evento;
import service.EventoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import service.ComentarioService;

@WebServlet(urlPatterns = {"/eventos", "/eventos/nuevo", "/eventos/guardar", "/eventos/detalle", "/eventos/editar", "/eventos/eliminar"})
public class EventoController extends HttpServlet {

    private final EventoService service = new EventoService(new EventoDAOImpl());
    private final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"); // para <input type="datetime-local">
    private final CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        try {
            switch (path) {
                case "/eventos":
                    listar(req, resp);
                    break;
                case "/eventos/nuevo":
                    cargarCategorias(req);
                    req.getRequestDispatcher("/WEB-INF/views/eventos/eventos-form.jsp").forward(req, resp);
                    break;
                case "/eventos/detalle":
                    verDetalle(req, resp);
                    break;
                case "/eventos/editar":
                    editar(req, resp);
                    break;
                default:
                    resp.sendError(404);
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        try {
            switch (path) {
                case "/eventos/guardar":
                    guardar(req, resp);
                    break;
                case "/eventos/eliminar":
                    eliminar(req, resp);
                    break;
                default:
                    resp.sendError(404);
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int page = parseInt(req.getParameter("page"), 1);
        int size = parseInt(req.getParameter("size"), 12);
        String q = req.getParameter("q");

        String idCatStr = req.getParameter("idCategoria");
        Long idCategoria = null;
        if (idCatStr != null && !idCatStr.isBlank()) {
            try {
                idCategoria = Long.parseLong(idCatStr);
            } catch (NumberFormatException e) {
                idCategoria = null;
            }
        }

        req.setAttribute("eventos", service.list(page, size, q, idCategoria));
        req.setAttribute("page", page);
        req.setAttribute("size", size);
        req.setAttribute("q", q);
        req.setAttribute("total", service.count(q, idCategoria));

        // 👉 para el combo de filtro
        req.setAttribute("categorias", categoriaDAO.listAll());
        req.setAttribute("idCategoriaSeleccionada", idCategoria);

        req.getRequestDispatcher("/WEB-INF/views/eventos/eventos-list.jsp").forward(req, resp);
    }

    private void verDetalle(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        long id = Long.parseLong(req.getParameter("id"));
        Evento e = service.get(id);
        if (e == null) {
            resp.sendError(404);
            return;
        }
        req.setAttribute("evento", e);

        ComentarioService cService = new ComentarioService(new ComentarioDAOImpl());
        req.setAttribute("comentarios", cService.listByEvento(id, 1, 20));

        req.getRequestDispatcher("/WEB-INF/views/eventos/eventos-detalle.jsp").forward(req, resp);
    }

    private void editar(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        long id = Long.parseLong(req.getParameter("id"));
        Evento e = service.get(id);
        if (e == null) {
            resp.sendError(404);
            return;
        }
        req.setAttribute("evento", e);

        cargarCategorias(req);

        req.getRequestDispatcher("/WEB-INF/views/eventos/eventos-form.jsp").forward(req, resp);
    }

    private void cargarCategorias(HttpServletRequest req) throws Exception {
        req.setAttribute("categorias", categoriaDAO.listAll());
    }

    private void guardar(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setCharacterEncoding("UTF-8");

        model.SessionUser su = (model.SessionUser) req.getSession().getAttribute("user");
        if (su == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String idStr = req.getParameter("idEvento");

        Evento e = new Evento();
        e.setIdOrganizador(su.getIdUsuario()); // long seguro
        e.setNombre(req.getParameter("nombre"));
        e.setDescripcion(req.getParameter("descripcion"));

        String idCatStr = req.getParameter("idCategoria");
        if (idCatStr == null || idCatStr.isBlank()) {
            e.setIdCategoria(null);
        } else {
            try {
                e.setIdCategoria((idCatStr == null || idCatStr.isBlank()) ? null : Long.parseLong(idCatStr)); // usa Long en tu POJO para permitir null
            } catch (NumberFormatException ex) {
                e.setIdCategoria(null);
            }
        }

        e.setFechaInicio(LocalDateTime.parse(req.getParameter("fechaInicio"), FMT));
        e.setFechaFin(LocalDateTime.parse(req.getParameter("fechaFin"), FMT));
        e.setPais(req.getParameter("pais"));
        e.setCiudad(req.getParameter("ciudad"));
        e.setDireccion(req.getParameter("direccion"));
        e.setEstadoPublicacion(req.getParameter("estadoPublicacion")); // 'borrador','publicado','oculto'

        String precioStr = req.getParameter("precio");
        e.setPrecio((precioStr == null || precioStr.isBlank()) ? 0.0 : Double.parseDouble(precioStr));

        String aforoStr = req.getParameter("aforo");
        e.setAforo((aforoStr == null || aforoStr.isBlank()) ? null : Integer.parseInt(aforoStr));
        
        String esDestParam = req.getParameter("esDestacado");
        e.setEsDestacado(esDestParam != null);

        e.setUrlFoto(req.getParameter("urlFoto"));

        if (idStr == null || idStr.isBlank()) {
            long idNuevo = service.create(e);
            resp.sendRedirect(req.getContextPath() + "/eventos/detalle?id=" + idNuevo);
        } else {
            e.setIdEvento(Long.parseLong(idStr)); // mantén long en el POJO
            service.update(e);
            resp.sendRedirect(req.getContextPath() + "/eventos/detalle?id=" + e.getIdEvento());
        }
    }

    private void eliminar(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        long id = Long.parseLong(req.getParameter("id"));
        service.delete(id);
        resp.sendRedirect(req.getContextPath() + "/eventos");
    }

    private int parseInt(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }
}
