package dao;

import java.util.List;
import model.Usuario;

public interface UsuarioDAO {

    long create(Usuario u) throws Exception;

    Usuario findByEmail(String email) throws Exception; // retorna null si no existe

    Usuario findById(long id) throws Exception;

    void updateUltimoLogin(long id) throws Exception;
    
    void incrementarPuntos(long idUsuario, int delta) throws Exception;
    
    List<Usuario> topByPuntos(int limit) throws Exception;
}
