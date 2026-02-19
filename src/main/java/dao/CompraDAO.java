package dao;

import model.Compra;
import java.util.List;

public interface CompraDAO {

    long create(Compra c) throws Exception;

    List<Compra> listByUsuario(long idUsuario) throws Exception;
    
    int totalEntradasPagadasPorEvento(long idEvento) throws Exception;
    
    int countByUsuario(long idUsuario) throws Exception;

}
