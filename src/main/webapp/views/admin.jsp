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
    <c:if test="${param.err=='1'}">
        <div class="alert alert-danger">Datos inválidos</div>
    </c:if>
    <div class="card shadow-sm">
        <div class="card-body p-4">

            <h4>${product.id == null ? 'Nuevo producto' : 'Editar producto'}</h4>

            <form method="post" action="${pageContext.request.contextPath}/app/save" class="row g-3">
                <input type="hidden" name="id" value="${product.id}"/>
                <div class="col-md-6">
                    <label class="form-label">Nombre</label>
                    <input name="name" value="${product.name}" class="form-control" required/>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Precio</label>
                    <input name="price" type="number" step="0.01" value="${product.price}" class="form-control" required/>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Stock</label>
                    <input name="stock" type="number" value="${product.stock}" class="form-control" required/>
                </div>
                <div class="col-12">
                    <button class="btn btn-primary">Guardar</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/app">Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</section>
</body>
</html>
