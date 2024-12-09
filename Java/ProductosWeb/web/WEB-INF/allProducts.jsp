<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lista de Productos</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    </head>
    <body>
        <div class="container my-5">
            <h1 class="text-center">Lista de Productos</h1>

            <!-- Formulario de búsqueda -->
            <form action="ProductoServlet" method="get" class="mb-3 d-flex">
                <input type="hidden" name="action" value="search">
                <input type="text" name="nombre" class="form-control me-2" placeholder="Buscar por nombre">
                <button type="submit" class="btn btn-primary">Buscar</button>
            </form>
           
            
            
            <table class="table table-bordered table-striped mt-4">
                <thead class="thead-dark">
                    <tr>
                        <th>Id Producto</th>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Id Bodega</th>
                        <th>Bodega</th>
                        <th>Ubicacion</th>
                        <th>Ciudad</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Modelo.Producto> productos = (List<Modelo.Producto>) request.getAttribute("productos");
                        if (productos != null) {
                            for (Modelo.Producto producto : productos) {
                    %>
                    <tr>
                        <td><%= producto.getId_producto()%></td>
                        <td><%= producto.getProducto()%></td>
                        <td><%= producto.getCantidad()%></td>
                        <td><%= producto.getId_bodega()%></td>
                        <td><%= producto.getBodega()%></td>
                        <td><%= producto.getUbicacion()%></td>
                        <td><%= producto.getCiudad()%></td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="6" class="text-center">No hay estudiantes disponibles.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <a href="index.html" class="btn btn-primary mb-1">Pagina Principal</a>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </body>
</html>
