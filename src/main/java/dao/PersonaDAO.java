package dao;

import model.Persona;

public interface PersonaDAO {

    long create(Persona p) throws Exception;

    boolean existsByDocumento(String tipo, String nro) throws Exception;

    boolean existsByEmail(String email) throws Exception;

    Persona findById(long id) throws Exception;
}
