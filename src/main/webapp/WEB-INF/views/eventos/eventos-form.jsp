<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
    <head>
        <c:set var="pageTitle" value="QhatuXApp — Evento" />
        <%@ include file="/WEB-INF/views/jspf/head.jspf" %>

        <link rel="stylesheet"
              href="${pageContext.request.contextPath}/RESOURCES/css/eventos-form.css"
              type="text/css"/>
    </head>
    <body>
        <%@ include file="/WEB-INF/views/jspf/header.jspf" %>

        <c:set var="e" value="${evento}" />

        <div class="container event-form-page">
            <div class="event-form-card event-form">

                <!-- Header -->
                <div class="event-form-header">
                    <div>
                        <h1 class="event-form-title">
                            <c:out value="${e == null ? 'Nuevo evento' : 'Editar evento'}" />
                        </h1>
                        <p class="event-form-subtitle mb-0">
                            Completa la información del evento. Podrás marcarlo como destacado y controlar su estado de publicación.
                        </p>
                    </div>
                    <a href="${pageContext.request.contextPath}/eventos"
                       class="btn btn-outline-secondary btn-sm">
                        Volver a la lista
                    </a>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/eventos/guardar" novalidate>
                    <input type="hidden" name="idEvento" value="${e.idEvento}" />

                    <!-- Sección: Datos generales -->
                    <div class="event-form-section">
                        <p class="event-form-section-title">
                            <i class="bi bi-card-text me-1"></i> Datos generales
                        </p>

                        <div class="mb-3">
                            <label class="form-label">Nombre del evento</label>
                            <input class="form-control"
                                   name="nombre"
                                   value="${e.nombre}"
                                   required
                                   maxlength="150">
                        </div>

                        <div class="mb-0">
                            <label class="form-label">Descripción</label>
                            <textarea class="form-control"
                                      name="descripcion"
                                      rows="4"
                                      maxlength="2000">${e.descripcion}</textarea>
                        </div>
                    </div>

                    <!-- Sección: Fecha y ubicación -->
                    <div class="event-form-section">
                        <p class="event-form-section-title">
                            <i class="bi bi-calendar-event me-1"></i> Fecha y ubicación
                        </p>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Inicio</label>
                                <input type="datetime-local"
                                       class="form-control"
                                       name="fechaInicio"
                                       value="${e != null ? e.fechaInicio.toString().replace(' ', 'T') : ''}"
                                       required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Fin</label>
                                <input type="datetime-local"
                                       class="form-control"
                                       name="fechaFin"
                                       value="${e != null ? e.fechaFin.toString().replace(' ', 'T') : ''}"
                                       required>
                            </div>
                        </div>

                        <div class="row mb-0">
                            <div class="col-md-4 mb-3">
                                <label class="form-label">País</label>
                                <input class="form-control"
                                       name="pais"
                                       value="${e.pais}">
                            </div>
                            <div class="col-md-4 mb-3">
                                <label class="form-label">Ciudad</label>
                                <input class="form-control"
                                       name="ciudad"
                                       value="${e.ciudad}">
                            </div>
                            <div class="col-md-4 mb-0">
                                <label class="form-label">Dirección</label>
                                <input class="form-control"
                                       name="direccion"
                                       value="${e.direccion}">
                            </div>
                        </div>
                    </div>

                    <!-- Sección: Configuración y categoría -->
                    <div class="event-form-section">
                        <p class="event-form-section-title">
                            <i class="bi bi-sliders me-1"></i> Configuración y categoría
                        </p>

                        <div class="row">
                            <div class="col-6 col-md-3 mb-3">
                                <label class="form-label">Estado publicación</label>
                                <select class="form-select" name="estadoPublicacion">
                                    <c:set var="estado" value="${e.estadoPublicacion != null ? e.estadoPublicacion : 'borrador'}" />
                                    <option value="borrador"  ${estado=='borrador'?'selected':''}>Borrador</option>
                                    <option value="publicado" ${estado=='publicado'?'selected':''}>Publicado</option>
                                    <option value="oculto"    ${estado=='oculto'?'selected':''}>Oculto</option>
                                </select>
                            </div>

                            <div class="col-6 col-md-3 mb-3">
                                <label class="form-label">Precio (S/)</label>
                                <input class="form-control"
                                       name="precio"
                                       type="number"
                                       step="0.01"
                                       min="0"
                                       value="${e.precio}">
                            </div>

                            <div class="col-6 col-md-3 mb-3">
                                <label class="form-label">Aforo</label>
                                <input class="form-control"
                                       name="aforo"
                                       type="number"
                                       min="0"
                                       value="${e.aforo}">
                            </div>

                            <div class="col-6 col-md-3 mb-0">
                                <label for="idCategoria" class="form-label">Categoría</label>
                                <select class="form-select" id="idCategoria" name="idCategoria">
                                    <option value="">-- Selecciona una categoría --</option>
                                    <c:forEach var="cat" items="${categorias}">
                                        <option value="${cat.idCategoria}"
                                                <c:if test="${not empty e && e.idCategoria == cat.idCategoria}">
                                                    selected
                                                </c:if>>
                                            ${cat.nombre}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <!-- Sección: Imagen y destacado -->
                    <div class="event-form-section mb-0">
                        <p class="event-form-section-title">
                            <i class="bi bi-image me-1"></i> Imagen y destacado
                        </p>

                        <div class="mb-3">
                            <label class="form-label">URL de la imagen del evento</label>
                            <input class="form-control"
                                   name="urlFoto"
                                   value="${e.urlFoto}">
                            <small class="form-text text-muted">
                                Puedes usar imágenes alojadas en internet. Se recomienda un formato horizontal.
                            </small>
                        </div>

                        <div class="mb-0 form-check">
                            <input class="form-check-input"
                                   type="checkbox"
                                   id="esDestacado"
                                   name="esDestacado"
                                   value="1"
                                   <c:if test="${not empty e && e.esDestacado}">
                                       checked
                                   </c:if>>
                            <label class="form-check-label" for="esDestacado">
                                Marcar como evento destacado (aparecerá en la sección de destacados del inicio).
                            </label>
                        </div>
                    </div>

                    <!-- Botones -->
                    <div class="event-form-footer">
                        <button class="btn btn-primary" type="submit">
                            <c:out value="${e == null ? 'Crear evento' : 'Guardar cambios'}" />
                        </button>
                        <a class="btn btn-outline-secondary"
                           href="${pageContext.request.contextPath}/eventos">
                            Cancelar
                        </a>
                    </div>

                </form>
            </div>
        </div>

        <%@ include file="/WEB-INF/views/jspf/footer.jspf" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
