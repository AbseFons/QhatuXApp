<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <title>Registro</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    </head>
    <body class="bg-light">
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-12 col-md-7 col-lg-6">
                    <div class="card shadow-sm">
                        <div class="card-body p-4">
                            <h1 class="h4 mb-3 text-center">Crear cuenta</h1>

                            <%-- Mensaje de error --%>
                            <%
                                Object err = request.getAttribute("error");
                                if (err != null) {
                            %>
                            <div class="alert alert-danger" role="alert">
                                <%= err%>
                            </div>
                            <% }%>

                            <form method="post" action="${pageContext.request.contextPath}/registro" novalidate>
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="nombres" class="form-label">Nombres</label>
                                        <input type="text" class="form-control" id="nombres" name="nombres"
                                               value="${param.nombres}" required/>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="apellidos" class="form-label">Apellidos</label>
                                        <input type="text" class="form-control" id="apellidos" name="apellidos"
                                               value="${param.apellidos}" required/>
                                    </div>

                                    <div class="col-md-4">
                                        <label for="tipoDocumento" class="form-label">Tipo de documento</label>
                                        <select class="form-select" id="tipoDocumento" name="tipoDocumento" required>
                                            <option value="" ${empty param.tipoDocumento ? "selected" : ""} disabled>Selecciona…</option>
                                            <option value="DNI" ${param.tipoDocumento == 'DNI' ? "selected" : ""}>DNI</option>
                                            <option value="CE"  ${param.tipoDocumento == 'CE'  ? "selected" : ""}>CE</option>
                                            <option value="PASAPORTE" ${param.tipoDocumento == 'PASAPORTE' ? "selected" : ""}>Pasaporte</option>
                                            <option value="OTRO" ${param.tipoDocumento == 'OTRO' ? "selected" : ""}>Otro</option>
                                        </select>
                                    </div>
                                    <div class="col-md-8">
                                        <label for="nroDocumento" class="form-label">N° documento</label>
                                        <input type="text" class="form-control" id="nroDocumento" name="nroDocumento"
                                               value="${param.nroDocumento}" required/>
                                    </div>

                                    <div class="col-12">
                                        <label for="email" class="form-label">Correo electrónico</label>
                                        <input type="email" class="form-control" id="email" name="email"
                                               value="${param.email}" required/>
                                    </div>

                                    <div class="col-12">
                                        <label for="password" class="form-label">Contraseña</label>
                                        <input type="password" class="form-control" id="password" name="password" required/>
                                    </div>
                                </div>

                                <div class="d-grid mt-4">
                                    <button type="submit" class="btn btn-success">Crear cuenta</button>
                                </div>
                            </form>

                            <hr class="my-4"/>
                            <p class="text-center mb-0">
                                ¿Ya tienes cuenta?
                                <a href="${pageContext.request.contextPath}/login">Inicia sesión</a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
