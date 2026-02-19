package dao;

import java.util.List;
import model.Categoria;

public interface CategoriaDAO {
    List<Categoria> listAll() throws Exception;
}
