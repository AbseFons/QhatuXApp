package dao;

import java.util.List;
import model.Evento;

public interface EventoDAO {

    List<Evento> list(int page, int size, String q) throws Exception;
    int count(String q) throws Exception;
    
    List<Evento> list(int page, int size, String q, Long idCategoria) throws Exception;
    int count(String q, Long idCategoria) throws Exception;

    Evento findById(long id) throws Exception;

    long create(Evento e) throws Exception;

    void update(Evento e) throws Exception;

    void delete(long id) throws Exception;
    
    List<Evento> listByCategoria(Long idCategoria) throws Exception;
    
    List<Evento> listDestacados(int limit) throws Exception;
}
