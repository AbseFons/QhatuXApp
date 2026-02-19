package dao_impl;

import dao.ComentarioDAO;
import model.Comentario;
import util.MySQLConexion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAOImpl implements ComentarioDAO {

    private Comentario map(ResultSet rs) throws Exception {
        Comentario c = new Comentario();
        c.setIdComentario(rs.getLong("IdComentario"));
        c.setIdEvento(rs.getLong("IdEvento"));
        c.setIdUsuario(rs.getLong("IdUsuario"));
        c.setTexto(rs.getString("Texto"));
        Timestamp f = rs.getTimestamp("Fecha");
        c.setFecha(f != null ? f.toLocalDateTime() : null);
        c.setAutorNombre(rs.getString("Autor"));
        return c;
    }

    @Override
    public List<Comentario> listByEvento(long idEvento, int page, int size) throws Exception {
        String sql = "SELECT c.IdComentario,c.IdEvento,c.IdUsuario,c.Texto,c.Fecha, "
                + "CONCAT(p.Nombres,' ',IFNULL(p.Apellidos,'')) AS Autor "
                + "FROM Comentario c "
                + "JOIN Usuario u ON c.IdUsuario=u.IdUsuario "
                + "JOIN Persona p ON u.IdPersona=p.IdPersona "
                + "WHERE c.IdEvento=? "
                + "ORDER BY c.Fecha DESC LIMIT ? OFFSET ?";
        List<Comentario> list = new ArrayList<>();
        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, idEvento);
            ps.setInt(2, size);
            ps.setInt(3, (page - 1) * size);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }
        return list;
    }

    @Override
    public long create(Comentario c) throws Exception {
        String sql = "INSERT INTO Comentario (IdEvento,IdUsuario,Texto) VALUES (?,?,?)";
        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, c.getIdEvento());
            ps.setLong(2, c.getIdUsuario());
            ps.setString(3, c.getTexto());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        return 0;
    }

    @Override
    public void delete(long idComentario, long solicitante, boolean esAdmin) throws Exception {
        String sqlSelf = "DELETE FROM Comentario WHERE IdComentario=? AND IdUsuario=?";
        String sqlAdmin = "DELETE FROM Comentario WHERE IdComentario=?";
        try (Connection cn = MySQLConexion.getConexion()) {
            try (PreparedStatement ps = cn.prepareStatement(esAdmin ? sqlAdmin : sqlSelf)) {
                ps.setLong(1, idComentario);
                if (!esAdmin) {
                    ps.setLong(2, solicitante);
                }
                ps.executeUpdate();
            }
        }
    }

    @Override
    public int countByUsuario(long idUsuario) throws Exception {
        String sql = "SELECT COUNT(*) AS total "
                + "FROM Comentario "
                + "WHERE IdUsuario = ?";
        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }
}
