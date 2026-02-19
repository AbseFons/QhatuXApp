package service;

import dao.EventoDAO;
import java.time.LocalDateTime;
import java.util.List;
import model.Evento;

public class EventoService {

    private final EventoDAO eventoDAO;

    public EventoService(EventoDAO dao) {
        this.eventoDAO = dao;
    }

    public List<Evento> list(int page, int size, String q, Long idCategoria) throws Exception {
        return eventoDAO.list(page, size, q, idCategoria);
    }

    public int count(String q, Long idCategoria) throws Exception {
        return eventoDAO.count(q, idCategoria);
    }

    public Evento get(long id) throws Exception {
        return eventoDAO.findById(id);
    }

    public long create(Evento e) throws Exception {
        validar(e);
        return eventoDAO.create(e);
    }

    public void update(Evento e) throws Exception {
        validar(e);
        eventoDAO.update(e);
    }

    public void delete(long id) throws Exception {
        eventoDAO.delete(id);
    }

    public List<Evento> listarEventos(Long idCategoria) throws Exception {
        return eventoDAO.listByCategoria(idCategoria);
    }
    
    public List<Evento> listDestacados(int limit) throws Exception {
        return eventoDAO.listDestacados(limit);
    }

    private void validar(Evento e) {
        if (e.getNombre() == null || e.getNombre().isBlank()) {
            throw new IllegalArgumentException("Nombre requerido");
        }
        if (e.getFechaInicio() == null || e.getFechaFin() == null) {
            throw new IllegalArgumentException("Fechas requeridas");
        }
        if (e.getFechaFin().isBefore(e.getFechaInicio())) {
            throw new IllegalArgumentException("Fecha fin no puede ser anterior al inicio");
        }
        if (e.getPrecio() < 0) {
            throw new IllegalArgumentException("Precio inválido");
        }
        if (e.getAforo() != 0 && e.getAforo() < 0) {
            throw new IllegalArgumentException("Aforo inválido");
        }
        if (e.getEstadoPublicacion() == null) {
            e.setEstadoPublicacion("borrador");
        }
    }
}
