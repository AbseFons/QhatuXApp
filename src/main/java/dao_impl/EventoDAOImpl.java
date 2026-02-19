package dao_impl;

import dao.EventoDAO;
import model.Evento;
import util.MySQLConexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAOImpl implements EventoDAO {

    @Override
    public List<Evento> list(int page, int size, String q) throws Exception {
        List<Evento> out = new ArrayList<>();
        String sql = "SELECT e.IdEvento, e.IdOrganizador, e.Nombre, e.IdCategoria, e.Descripcion, "
                + "e.FechaInicio, e.FechaFin, e.Pais, e.Ciudad, e.Direccion, "
                + "e.EstadoPublicacion, e.Precio, e.Aforo, e.UrlFoto "
                + "FROM Evento e "
                + "WHERE ( ? IS NULL OR ? = '' OR e.Nombre LIKE CONCAT('%', ?, '%') "
                + "       OR e.Ciudad LIKE CONCAT('%', ?, '%') OR e.Pais LIKE CONCAT('%', ?, '%') ) "
                + "AND e.EstadoPublicacion <> 'oculto' "
                + "ORDER BY e.FechaInicio ASC LIMIT ? OFFSET ?";

        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            int i = 1;
            ps.setString(i++, q);
            ps.setString(i++, q);
            ps.setString(i++, q);
            ps.setString(i++, q);
            ps.setString(i++, q);
            ps.setInt(i++, size);
            ps.setInt(i, (page - 1) * size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(mapRow(rs));
                }
            }
        }
        return out;
    }

    @Override
    public List<Evento> list(int offset, int size, String q, Long idCategoria) throws Exception {

        StringBuilder sql = new StringBuilder(
                "SELECT IdEvento, IdOrganizador, Nombre, IdCategoria, Descripcion, "
                + "       FechaInicio, FechaFin, Pais, Ciudad, Direccion, "
                + "       EstadoPublicacion, Precio, Aforo, UrlFoto, EsDestacado "
                + "FROM Evento "
                + "WHERE EstadoPublicacion = 'publicado' "
        );

        List<Object> params = new ArrayList<>();

        if (q != null && !q.isBlank()) {
            sql.append(" AND (Nombre LIKE ? OR Ciudad LIKE ?) ");
            String like = "%" + q.trim() + "%";
            params.add(like);
            params.add(like);
        }

        if (idCategoria != null) {
            sql.append(" AND IdCategoria = ? ");
            params.add(idCategoria);
        }

        sql.append(" ORDER BY FechaInicio ASC LIMIT ? OFFSET ? ");
        params.add(size);
        params.add(offset);

        List<Evento> out = new ArrayList<>();
        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            int idx = 1;
            for (Object param : params) {
                if (param instanceof String) {
                    ps.setString(idx++, (String) param);
                } else if (param instanceof Long) {
                    ps.setLong(idx++, (Long) param);
                } else if (param instanceof Integer) {
                    ps.setInt(idx++, (Integer) param);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Evento e = new Evento();
                    e.setIdEvento(rs.getLong("IdEvento"));
                    e.setIdOrganizador(rs.getLong("IdOrganizador"));
                    e.setNombre(rs.getString("Nombre"));
                    e.setIdCategoria(rs.getLong("IdCategoria"));
                    e.setDescripcion(rs.getString("Descripcion"));
                    Timestamp fi = rs.getTimestamp("FechaInicio");
                    e.setFechaInicio(fi != null ? fi.toLocalDateTime() : null);
                    Timestamp ff = rs.getTimestamp("FechaFin");
                    e.setFechaFin(ff != null ? ff.toLocalDateTime() : null);
                    e.setPais(rs.getString("Pais"));
                    e.setCiudad(rs.getString("Ciudad"));
                    e.setDireccion(rs.getString("Direccion"));
                    e.setEstadoPublicacion(rs.getString("EstadoPublicacion"));
                    e.setPrecio(rs.getDouble("Precio"));
                    int af = rs.getInt("Aforo");
                    e.setAforo(rs.wasNull() ? null : af);
                    e.setUrlFoto(rs.getString("UrlFoto"));
                    e.setEsDestacado(rs.getBoolean("EsDestacado"));
                    out.add(e);
                }
            }
        }

        return out;
    }

    @Override
    public int count(String q) throws Exception {
        String sql = "SELECT COUNT(1) FROM Evento e "
                + "WHERE ( ? IS NULL OR ? = '' OR e.Nombre LIKE CONCAT('%', ?, '%') "
                + "       OR e.Ciudad LIKE CONCAT('%', ?, '%') OR e.Pais LIKE CONCAT('%', ?, '%') ) "
                + "AND e.EstadoPublicacion <> 'oculto'";

        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            int i = 1;
            ps.setString(i++, q);
            ps.setString(i++, q);
            ps.setString(i++, q);
            ps.setString(i++, q);
            ps.setString(i++, q);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    @Override
    public int count(String q, Long idCategoria) throws Exception {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) AS total "
                + "FROM Evento "
                + "WHERE EstadoPublicacion = 'publicado' "
        );

        List<Object> params = new ArrayList<>();

        if (q != null && !q.isBlank()) {
            sql.append(" AND (Nombre LIKE ? OR Ciudad LIKE ?) ");
            String like = "%" + q.trim() + "%";
            params.add(like);
            params.add(like);
        }

        if (idCategoria != null) {
            sql.append(" AND IdCategoria = ? ");
            params.add(idCategoria);
        }

        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            int idx = 1;
            for (Object param : params) {
                if (param instanceof String) {
                    ps.setString(idx++, (String) param);
                } else if (param instanceof Long) {
                    ps.setLong(idx++, (Long) param);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    @Override
    public Evento findById(long id) throws Exception {
        String sql = "SELECT e.IdEvento, e.IdOrganizador, e.Nombre, e.IdCategoria, e.Descripcion, "
                + "e.FechaInicio, e.FechaFin, e.Pais, e.Ciudad, e.Direccion, "
                + "e.EstadoPublicacion, e.Precio, e.Aforo, e.UrlFoto, e.EsDestacado "
                + "FROM Evento e WHERE e.IdEvento=?";
        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public long create(Evento e) throws Exception {
        String sql = "INSERT INTO Evento (IdOrganizador,Nombre,IdCategoria,Descripcion,FechaInicio,FechaFin,"
                + "Pais,Ciudad,Direccion,EstadoPublicacion,Precio,Aforo,UrlFoto,EsDestacado) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setLong(i++, e.getIdOrganizador());
            if (e.getNombre() == null || e.getNombre().isBlank()) {
                throw new IllegalArgumentException("Nombre requerido");
            }
            ps.setString(i++, e.getNombre());

            if (e.getIdCategoria() == null) {
                ps.setNull(i++, Types.BIGINT);
            } else {
                setLongOrNull(ps, i++, e.getIdCategoria());
            }

            ps.setString(i++, e.getDescripcion());
            ps.setTimestamp(i++, Timestamp.valueOf(e.getFechaInicio()));
            ps.setTimestamp(i++, Timestamp.valueOf(e.getFechaFin()));
            ps.setString(i++, e.getPais());
            ps.setString(i++, e.getCiudad());
            ps.setString(i++, e.getDireccion());
            ps.setString(i++, e.getEstadoPublicacion());
            ps.setDouble(i++, e.getPrecio());
            if (e.getAforo() == 0) {
                ps.setNull(i++, Types.INTEGER);
            } else {
                ps.setInt(i++, e.getAforo());
            }
            ps.setString(i++, e.getUrlFoto());
            ps.setBoolean(i++, e.getEsDestacado());

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
    public void update(Evento e) throws Exception {
        String sql = "UPDATE Evento SET Nombre=?, IdCategoria=?, Descripcion=?, FechaInicio=?, FechaFin=?, "
                + "Pais=?, Ciudad=?, Direccion=?, EstadoPublicacion=?, Precio=?, Aforo=?, UrlFoto=?, EsDestacado=? "
                + "WHERE IdEvento=?";
        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            int i = 1;
            ps.setString(i++, e.getNombre());
            if (e.getIdCategoria() == null) {
                ps.setNull(i++, Types.BIGINT);
            } else {
                ps.setLong(i++, e.getIdCategoria());
            }
            ps.setString(i++, e.getDescripcion());
            ps.setTimestamp(i++, Timestamp.valueOf(e.getFechaInicio()));
            ps.setTimestamp(i++, Timestamp.valueOf(e.getFechaFin()));
            ps.setString(i++, e.getPais());
            ps.setString(i++, e.getCiudad());
            ps.setString(i++, e.getDireccion());
            ps.setString(i++, e.getEstadoPublicacion());
            ps.setDouble(i++, e.getPrecio());
            if (e.getAforo() == 0) {
                ps.setNull(i++, Types.INTEGER);
            } else {
                ps.setInt(i++, e.getAforo());
            }
            ps.setString(i++, e.getUrlFoto());
            ps.setBoolean(i++, e.getEsDestacado());
            ps.setLong(i, e.getIdEvento());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(long id) throws Exception {
        String sql = "DELETE FROM Evento WHERE IdEvento=?";
        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Evento> listDestacados(int limit) throws Exception {
        String sql = "SELECT IdEvento, IdOrganizador, Nombre, IdCategoria, Descripcion, "
                + "       FechaInicio, FechaFin, Pais, Ciudad, Direccion, "
                + "       EstadoPublicacion, Precio, Aforo, UrlFoto, EsDestacado "
                + "FROM Evento "
                + "WHERE EstadoPublicacion = 'publicado' "
                + "  AND FechaInicio >= NOW() "
                + "ORDER BY FechaInicio ASC "
                + "LIMIT ?";

        List<Evento> out = new ArrayList<>();

        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Evento e = new Evento();
                    e.setIdEvento(rs.getLong("IdEvento"));
                    e.setIdOrganizador(rs.getLong("IdOrganizador"));
                    e.setNombre(rs.getString("Nombre"));
                    e.setIdCategoria(rs.getLong("IdCategoria"));
                    e.setDescripcion(rs.getString("Descripcion"));

                    Timestamp fi = rs.getTimestamp("FechaInicio");
                    e.setFechaInicio(fi != null ? fi.toLocalDateTime() : null);

                    Timestamp ff = rs.getTimestamp("FechaFin");
                    e.setFechaFin(ff != null ? ff.toLocalDateTime() : null);

                    e.setPais(rs.getString("Pais"));
                    e.setCiudad(rs.getString("Ciudad"));
                    e.setDireccion(rs.getString("Direccion"));
                    e.setEstadoPublicacion(rs.getString("EstadoPublicacion"));
                    e.setPrecio(rs.getDouble("Precio"));
                    int af = rs.getInt("Aforo");
                    e.setAforo(rs.wasNull() ? null : af);
                    e.setUrlFoto(rs.getString("UrlFoto"));
                    e.setEsDestacado(rs.getBoolean("EsDestacado"));

                    out.add(e);
                }
            }
        }
        return out;
    }

    private Evento mapRow(ResultSet rs) throws Exception {
        Evento e = new Evento();
        e.setIdEvento(rs.getLong("IdEvento"));
        e.setIdOrganizador(rs.getLong("IdOrganizador"));
        e.setNombre(rs.getString("Nombre"));
        long idCat = rs.getLong("IdCategoria");
        e.setIdCategoria(rs.wasNull() ? null : idCat);
        e.setDescripcion(rs.getString("Descripcion"));
        Timestamp ini = rs.getTimestamp("FechaInicio");
        Timestamp fin = rs.getTimestamp("FechaFin");
        e.setFechaInicio(ini != null ? ini.toLocalDateTime() : null);
        e.setFechaFin(fin != null ? fin.toLocalDateTime() : null);
        e.setPais(rs.getString("Pais"));
        e.setCiudad(rs.getString("Ciudad"));
        e.setDireccion(rs.getString("Direccion"));
        e.setEstadoPublicacion(rs.getString("EstadoPublicacion"));
        e.setPrecio(rs.getDouble("Precio"));
        int aforo = rs.getInt("Aforo");
        e.setAforo(rs.wasNull() ? null : aforo);
        e.setUrlFoto(rs.getString("UrlFoto"));
        e.setEsDestacado(rs.getBoolean("EsDestacado"));
        return e;
    }

    @Override
    public List<Evento> listByCategoria(Long idCategoria) throws Exception {
        String baseSql
                = "SELECT IdEvento, IdOrganizador, Nombre, IdCategoria, Descripcion, FechaInicio, FechaFin, "
                + "       Precio, Aforo, EstadoPublicacion "
                + "FROM Evento "
                + "WHERE EstadoPublicacion = 'publicado' ";

        // si idCategoria es null → sin filtro extra
        boolean filtrar = (idCategoria != null);

        String sql = baseSql + (filtrar ? " AND IdCategoria = ? " : "")
                + " ORDER BY FechaInicio ASC";

        List<Evento> out = new ArrayList<>();

        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {

            if (filtrar) {
                ps.setLong(1, idCategoria);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Evento e = new Evento();
                    e.setIdEvento(rs.getLong("IdEvento"));
                    e.setIdOrganizador(rs.getLong("IdOrganizador"));
                    e.setNombre(rs.getString("Nombre"));
                    e.setIdCategoria((Long) rs.getLong("IdCategoria"));
                    e.setDescripcion(rs.getString("Descripcion"));

                    Timestamp fi = rs.getTimestamp("FechaInicio");
                    e.setFechaInicio(fi != null ? fi.toLocalDateTime() : null);

                    Timestamp ff = rs.getTimestamp("FechaFin");
                    e.setFechaFin(ff != null ? ff.toLocalDateTime() : null);

                    e.setPrecio(rs.getDouble("Precio"));
                    e.setAforo(rs.getInt("Aforo"));
                    e.setEstadoPublicacion(rs.getString("EstadoPublicacion"));

                    out.add(e);
                }
            }
        }
        return out;
    }

    private static void setLongOrNull(PreparedStatement ps, int idx, Long val) throws SQLException {
        if (val == null) {
            ps.setNull(idx, java.sql.Types.BIGINT);
        } else {
            ps.setLong(idx, val);
        }
    }

    private static void setIntOrNull(PreparedStatement ps, int idx, Integer val) throws SQLException {
        if (val == null) {
            ps.setNull(idx, java.sql.Types.INTEGER);
        } else {
            ps.setInt(idx, val);
        }
    }

}
