package dao_impl;

import dao.UsuarioDAO;
import model.Usuario;
import util.MySQLConexion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    private static final String SQL_INSERT
            = "INSERT INTO Usuario (IdPersona,Rol,PasswordHash) VALUES (?,?,?)";

    private static final String SQL_FIND_BY_EMAIL
            = "SELECT IdUsuario,IdPersona,Rol,Puntos,PasswordHash,UltimoLogin FROM Usuario WHERE IdUsuario IN "
            + "(SELECT u.IdUsuario FROM Usuario u JOIN Persona p ON u.IdPersona=p.IdPersona WHERE p.Email=?) "
            + "LIMIT 1";

    private static final String SQL_FIND_BY_ID
            = "SELECT IdUsuario,IdPersona,Rol,Puntos,PasswordHash,UltimoLogin FROM Usuario WHERE IdUsuario=?";

    private static final String SQL_UPDATE_LAST_LOGIN
            = "UPDATE Usuario SET UltimoLogin=NOW() WHERE IdUsuario=?";

    private static final String SQL_TOP_BY_PUNTOS
            = "SELECT IdUsuario, IdPersona, Rol, Puntos, PasswordHash, UltimoLogin "
            + "FROM Usuario "
            + "ORDER BY Puntos DESC "
            + "LIMIT ?";

    @Override
    public long create(Usuario u) throws Exception {
        try (Connection con = MySQLConexion.getConexion(); PreparedStatement ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, u.getIdPersona());
            ps.setString(2, u.getRol());
            ps.setString(3, u.getPasswordHash());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("No se insertó Usuario (0 filas).");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    throw new SQLException("No se obtuvo el ID generado de Usuario.");
                }
            }
        }
    }

    @Override
    public Usuario findByEmail(String email) throws Exception {
        try (Connection con = MySQLConexion.getConexion(); PreparedStatement ps = con.prepareStatement(SQL_FIND_BY_EMAIL)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("IdUsuario"));
                u.setIdPersona(rs.getInt("IdPersona"));
                u.setRol(rs.getString("Rol"));
                u.setPasswordHash(rs.getString("PasswordHash"));
                u.setPuntos(rs.getInt("Puntos"));

                Timestamp ts = rs.getTimestamp("UltimoLogin");
                u.setUltimoLogin(ts != null ? ts.toLocalDateTime() : null);

                return u;
            }
        }
    }

    @Override
    public Usuario findById(long id) throws Exception {
        try (Connection con = MySQLConexion.getConexion(); PreparedStatement ps = con.prepareStatement(SQL_FIND_BY_ID)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("IdUsuario"));
                u.setIdPersona(rs.getInt("IdPersona"));
                u.setRol(rs.getString("Rol"));
                u.setPasswordHash(rs.getString("PasswordHash"));
                u.setPuntos(rs.getInt("Puntos"));

                Timestamp ts = rs.getTimestamp("UltimoLogin");
                u.setUltimoLogin(ts != null ? ts.toLocalDateTime() : null);

                return u;
            }
        }
    }

    @Override
    public List<Usuario> topByPuntos(int limit) throws Exception {
        List<Usuario> out = new ArrayList<>();

        try (Connection con = MySQLConexion.getConexion(); PreparedStatement ps = con.prepareStatement(SQL_TOP_BY_PUNTOS)) {

            ps.setInt(1, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("IdUsuario"));
                    u.setIdPersona(rs.getInt("IdPersona"));
                    u.setRol(rs.getString("Rol"));
                    u.setPasswordHash(rs.getString("PasswordHash"));
                    u.setPuntos(rs.getInt("Puntos"));

                    Timestamp ts = rs.getTimestamp("UltimoLogin");
                    u.setUltimoLogin(ts != null ? ts.toLocalDateTime() : null);

                    out.add(u);
                }
            }
        }
        return out;
    }

    @Override
    public void updateUltimoLogin(long id) throws Exception {
        try (Connection con = MySQLConexion.getConexion(); PreparedStatement ps = con.prepareStatement(SQL_UPDATE_LAST_LOGIN)) {

            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("No se actualizó UltimoLogin: IdUsuario no encontrado.");
            }
        }
    }

    @Override
    public void incrementarPuntos(long idUsuario, int delta) throws Exception {
        String sql = "UPDATE Usuario SET Puntos = Puntos + ? WHERE IdUsuario = ?";
        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, delta);
            ps.setLong(2, idUsuario);
            ps.executeUpdate();
        }
    }
}
