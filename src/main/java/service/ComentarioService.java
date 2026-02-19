package service;

import dao.ComentarioDAO;
import model.Comentario;
import java.util.List;

public class ComentarioService {

    private final ComentarioDAO comentarioDAO;
    private final GamificationService gamificationService;

    public ComentarioService(ComentarioDAO cDao, GamificationService gService) {
        this.comentarioDAO = cDao;
        this.gamificationService = gService;
    }

    public ComentarioService(ComentarioDAO cDao) {
        this.comentarioDAO = cDao;
        this.gamificationService = null;
    }
    public List<Comentario> listByEvento(long idEvento, int page, int size) throws Exception {
        return comentarioDAO.listByEvento(idEvento, page, size);
    }

    public long crear(long idEvento, long idUsuario, String texto) throws Exception {
        if (texto == null || texto.isBlank() || texto.length() > 800) {
            throw new IllegalArgumentException("Comentario inválido");
        }
        Comentario c = new Comentario();
        c.setIdEvento(idEvento);
        c.setIdUsuario(idUsuario);
        c.setTexto(texto.trim());
        
        if (gamificationService != null) {
            gamificationService.sumarPuntosPorComentario(idUsuario);
        }
        
        return comentarioDAO.create(c);
    }

    public void eliminar(long idComentario, long solicitante, boolean esAdmin) throws Exception {
        comentarioDAO.delete(idComentario, solicitante, esAdmin);
    }
}
