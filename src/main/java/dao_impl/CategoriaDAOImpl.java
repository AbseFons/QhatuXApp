
package dao_impl;

import dao.CategoriaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Categoria;
import util.MySQLConexion;

public class CategoriaDAOImpl implements CategoriaDAO {

    private static final String SQL_LIST_ALL =
        "SELECT IdCategoria, Nombre, Descripcion FROM Categoria ORDER BY Nombre";

    @Override
    public List<Categoria> listAll() throws Exception {
        List<Categoria> out = new ArrayList<>();
        try (Connection cn = MySQLConexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(SQL_LIST_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCategoria((int) rs.getLong("IdCategoria"));
                c.setNombre(rs.getString("Nombre"));
                c.setDescripcion(rs.getString("Descripcion"));
                out.add(c);
            }
        }
        return out;
    }
}
