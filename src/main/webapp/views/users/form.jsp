<%--
  Created by IntelliJ IDEA.
  User: sharonmarroquin
  Date: 24/08/25
  Time: 10:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>ShopLite • Admin</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg bg-white border-bottom">
  <div class="container">
    <a class="navbar-brand text-danger" href="${pageContext.request.contextPath}/home">ShopLite • Admin</a>
  </div>
</nav>

<section class="container my-5" style="max-width:720px;">
  <h4>${user.id == null ? 'Nuevo usuario' : 'Editar usuario'}</h4>

  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>

  <form method="post" action="${pageContext.request.contextPath}/app/users/save" class="row g-3">
    <input type="hidden" name="id" value="${user.id}"/>
    <div class="col-md-6">
      <label class="form-label">Usuario</label>
      <input name="username" value="${user.username}" class="form-control" required/>
    </div>
    <div class="col-md-6">
      <label class="form-label">Contraseña</label>
      <input name="password" value="${user.password}" class="form-control" required/>
    </div>
    <div class="col-md-6">
      <label class="form-label">Rol</label>
      <select name="role" class="form-select" required>
        <option value="USER" ${user.role == 'USER' ? 'selected' : ''}>USER</option>
        <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
      </select>
    </div>
    <div class="col-md-6 d-flex align-items-end">
      <div class="form-check">
        <input class="form-check-input" type="checkbox" name="active" ${user.active ? 'checked' : ''}/>
        <label class="form-check-label">Activo</label>
      </div>
    </div>
    <div class="col-12">
      <button class="btn btn-primary">Guardar</button>
      <a class="btn btn-secondary" href="${pageContext.request.contextPath}/app/users">Cancelar</a>
    </div>
  </form>
</section>
</body>
</html>
