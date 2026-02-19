package service;

import dao.CompraDAO;
import dao.EventoDAO;
import model.Compra;
import model.Evento;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;

public class CompraService {

    private final CompraDAO compraDAO;
    private final EventoDAO eventoDAO;
    private final GamificationService gamificationService;

    public CompraService(CompraDAO cDao, EventoDAO eDao, GamificationService gService) {
        this.compraDAO = cDao;
        this.eventoDAO = eDao;
        this.gamificationService = gService;
    }

    public long comprar(long idUsuario, long idEvento, int cantidad) throws Exception {

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }

        // 1) Obtener el evento
        Evento e = eventoDAO.findById(idEvento);
        if (e == null) {
            throw new IllegalArgumentException("El evento no existe.");
        }

        // 2) Validar aforo (solo si el evento tiene aforo configurado)
        Integer aforo = e.getAforo(); // adapta el tipo si en tu modelo es int
        if (aforo != null) {
            int vendidos = compraDAO.totalEntradasPagadasPorEvento(idEvento);
            int disponibles = aforo - vendidos;

            if (disponibles <= 0) {
                throw new IllegalStateException("Este evento ya no tiene entradas disponibles.");
            }

            if (cantidad > disponibles) {
                throw new IllegalStateException(
                        "Solo hay " + disponibles + " entradas disponibles para este evento."
                );
            }
        }

        // 3) Precio actual del evento
        double precio = e.getPrecio();

        // 4) Crear la compra
        Compra c = new Compra();
        c.setIdUsuario(idUsuario);
        c.setIdEvento(idEvento);
        c.setCantidad(cantidad);
        c.setPrecioUnitario(precio);

        long idCompra = compraDAO.create(c);

        // Gamificación: sumar puntos
        gamificationService.sumarPuntosPorCompra(idUsuario, cantidad);

        return idCompra;
    }

    public List<Compra> comprasFuturas(long idUsuario) throws Exception {
        List<Compra> todas = compraDAO.listByUsuario(idUsuario);
        LocalDateTime ahora = LocalDateTime.now();

        return todas.stream()
                .filter(c -> c.getFechaEvento() != null && c.getFechaEvento().isAfter(ahora))
                .collect(Collectors.toList());
    }

    public List<Compra> comprasPasadas(long idUsuario) throws Exception {
        List<Compra> todas = compraDAO.listByUsuario(idUsuario);
        LocalDateTime ahora = LocalDateTime.now();

        return todas.stream()
                .filter(c -> c.getFechaEvento() != null && !c.getFechaEvento().isAfter(ahora))
                .collect(Collectors.toList());
    }

    public List<Compra> misCompras(long idUsuario) throws Exception {
        return compraDAO.listByUsuario(idUsuario);
    }
}
