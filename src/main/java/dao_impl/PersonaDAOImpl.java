package dao_impl;

import dao.PersonaDAO;
import model.Persona;
import util.MySQLConexion;

import java.sql.*;
import java.time.LocalDate;

public class PersonaDAOImpl implements PersonaDAO {

    private static final String SQL_INSERT =
        "INSERT INTO Persona (TipoDocumento,NroDocumento,Nombres,Apellidos,Email,Telefono,Pais,Ciudad,Estado) " +
        "VALUES (?,?,?,?,?,?,?,?,?)";

    private static final String SQL_EXISTS_DOC =
        "SELECT 1 FROM Persona WHERE TipoDocumento=? AND NroDocumento=? LIMIT 1";

    private static final String SQL_EXISTS_EMAIL =
        "SELECT 1 FROM Persona WHERE Email=? LIMIT 1";

    private static final String SQL_FIND_BY_ID =
        "SELECT IdPersona,TipoDocumento,NroDocumento,Nombres,Apellidos,Email,Telefono,Pais,Ciudad,Estado,FechaRegistro " +
        "FROM Persona WHERE IdPersona=?";

    @Override
    public long create(Persona p) throws Exception {
        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getTipoDocumento());
            ps.setString(2, p.getNroDocumento());
            ps.setString(3, p.getNombres());
            ps.setString(4, p.getApellidos());
            ps.setString(5, p.getEmail());
            ps.setString(6, p.getTelefono());
            ps.setString(7, p.getPais());
            ps.setString(8, p.getCiudad());
            ps.setString(9, p.getEstado());

            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("No se insertó Persona (0 filas).");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    return id;
                } else {
                    throw new SQLException("No se obtuvo el ID generado de Persona.");
                }
            }
        }
    }

    @Override
    public boolean existsByDocumento(String tipo, String nro) throws Exception {
        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_EXISTS_DOC)) {

            ps.setString(1, tipo);
            ps.setString(2, nro);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // existe si hay al menos 1 fila
            }
        }
    }

    @Override
    public boolean existsByEmail(String email) throws Exception {
        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_EXISTS_EMAIL)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public Persona findById(long id) throws Exception {
        try (Connection con = MySQLConexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_FIND_BY_ID)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Persona p = new Persona();
                p.setIdPersona(rs.getInt("IdPersona"));
                p.setTipoDocumento(rs.getString("TipoDocumento"));
                p.setNroDocumento(rs.getString("NroDocumento"));
                p.setNombres(rs.getString("Nombres"));
                p.setApellidos(rs.getString("Apellidos"));
                p.setEmail(rs.getString("Email"));
                p.setTelefono(rs.getString("Telefono"));
                p.setPais(rs.getString("Pais"));
                p.setCiudad(rs.getString("Ciudad"));
                p.setEstado(rs.getString("Estado"));

                Date fr = rs.getDate("FechaRegistro"); // java.sql.Date
                p.setFechaRegistro(fr != null ? fr.toLocalDate() : null); // LocalDate

                return p;
            }
        }
    }
}
