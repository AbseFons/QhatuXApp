package service;

import dao.UsuarioDAO;

public class GamificationService {

    private final UsuarioDAO usuarioDAO;

    public GamificationService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void sumarPuntosPorCompra(long idUsuario, int cantidadEntradas) throws Exception {
        int puntos = 20 * cantidadEntradas;
        usuarioDAO.incrementarPuntos(idUsuario, puntos);
    }

    public void sumarPuntosPorComentario(long idUsuario) throws Exception {
        int puntos = 1;
        usuarioDAO.incrementarPuntos(idUsuario, puntos);
    }
}
