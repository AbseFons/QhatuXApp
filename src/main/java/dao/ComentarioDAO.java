package dao;

import model.Comentario;
import java.util.List;

public interface ComentarioDAO {
    List<Comentario> listByEvento(long idEvento, int page, int size) throws Exception;

    long create(Comentario c) throws Exception;

    void delete(long idComentario, long idUsuarioSolicitante, boolean esAdmin) throws Exception;
    
    int countByUsuario(long idUsuario) throws Exception;
}
