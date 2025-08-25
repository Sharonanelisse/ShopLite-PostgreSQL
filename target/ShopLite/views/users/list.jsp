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
  <title>ShopLite • Productos</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg bg-white border-bottom">
  <div class="container">
    <a class="navbar-brand text-primary" href="${pageContext.request.contextPath}/home">ShopLite</a>
    <div class="ms-auto d-flex gap-2">
      <c:if test="${sessionScope.user != null && sessionScope.user.role == 'ADMIN'}">
        <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/admin">Nuevo producto</a>

        <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/app/users">Usuarios</a>

      </c:if>
      <form method="post" action="${pageContext.request.contextPath}/logout">
        <button class="btn btn-sm btn-outline-danger">Cerrar sesión</button>
      </form>
    </div>
  </div>
</nav>

<section class="container my-5">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h4>Usuarios</h4>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/app/users/new">Nuevo</a>
  </div>

  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>

  <table class="table table-striped">
    <thead>
    <tr>
      <th>ID</th>
      <th>Usuario</th>
      <th>Rol</th>
      <th>Activo</th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="u" items="${users}">
      <tr>
        <td>${u.id}</td>
        <td>${u.username}</td>
        <td><span class="badge bg-secondary">${u.role}</span></td>
        <td>
          <c:choose>
            <c:when test="${u.active}"><span class="badge bg-success">Sí</span></c:when>
            <c:otherwise><span class="badge bg-danger">No</span></c:otherwise>
          </c:choose>
        </td>
        <td class="text-end">
          <a class="btn btn-sm btn-outline-secondary"
             href="${pageContext.request.contextPath}/app/users/edit?id=${u.id}">Editar</a>
          <form method="post" action="${pageContext.request.contextPath}/app/users/delete" class="d-inline"
                onsubmit="return confirm('¿Eliminar usuario?');">
            <input type="hidden" name="id" value="${u.id}"/>
            <button class="btn btn-sm btn-outline-danger">Eliminar</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <nav>
    <ul class="pagination">
      <c:forEach var="i" begin="1" end="${totalPages}">
        <li class="page-item ${i == page ? 'active' : ''}">
          <a class="page-link" href="${pageContext.request.contextPath}/app/users?page=${i}">${i}</a>
        </li>
      </c:forEach>
    </ul>
  </nav>
</section>
</body>
</html>
