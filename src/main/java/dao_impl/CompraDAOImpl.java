package dao_impl;

import dao.CompraDAO;
import model.Compra;
import util.MySQLConexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraDAOImpl implements CompraDAO {

    @Override
    public long create(Compra c) throws Exception {
        String sql = "INSERT INTO Compra (IdUsuario,IdEvento,Cantidad,PrecioUnitario,MetodoPago) "
                + "VALUES (?,?,?,?, 'simulado')";
        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, c.getIdUsuario());
            ps.setLong(2, c.getIdEvento());
            ps.setInt(3, c.getCantidad());
            ps.setDouble(4, c.getPrecioUnitario());
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
    public List<Compra> listByUsuario(long idUsuario) throws Exception {
        String sql = "SELECT c.IdCompra, c.IdUsuario, c.IdEvento, c.Cantidad, c.PrecioUnitario,"
                + "c.Estado, c.FechaCompra, "
                + "e.Nombre, e.FechaInicio AS FechaEvento "
                + "FROM Compra c "
                + "JOIN Evento e ON c.IdEvento = e.IdEvento "
                + "WHERE c.IdUsuario = ? "
                + "ORDER BY c.FechaCompra DESC";
        List<Compra> out = new ArrayList<>();
        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Compra c = new Compra();
                    c.setIdCompra(rs.getLong("IdCompra"));
                    c.setIdUsuario(rs.getLong("IdUsuario"));
                    c.setIdEvento(rs.getLong("IdEvento"));
                    c.setCantidad(rs.getInt("Cantidad"));
                    c.setPrecioUnitario(rs.getDouble("PrecioUnitario"));
                    c.setEstado(rs.getString("Estado"));
                    c.setNombreEvento(rs.getString("Nombre"));

                    Timestamp fc = rs.getTimestamp("FechaCompra");
                    c.setFechaCompra(fc != null ? fc.toLocalDateTime() : null);

                    Timestamp fe = rs.getTimestamp("FechaEvento");
                    c.setFechaEvento(fe != null ? fe.toLocalDateTime() : null);

                    out.add(c);
                }
            }
        }
        return out;
    }

    @Override
    public int totalEntradasPagadasPorEvento(long idEvento) throws Exception {
        String sql = "SELECT COALESCE(SUM(Cantidad), 0) AS total "
                + "FROM Compra "
                + "WHERE IdEvento = ? AND Estado = 'PAGADO'";
        try (Connection cn = MySQLConexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setLong(1, idEvento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    @Override
    public int countByUsuario(long idUsuario) throws Exception {
        String sql = "SELECT COUNT(*) AS total "
                + "FROM Compra "
                + "WHERE IdUsuario = ? AND Estado = 'PAGADO'";
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
