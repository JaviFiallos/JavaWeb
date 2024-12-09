<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="Modelo.Producto"%>
<%@page import="Modelo.Bodega"%>
<%@page import="java.util.List"%>

<%
    Producto producto = (Producto) request.getAttribute("producto");
    List<Bodega> bodegasList = (List<Bodega>) request.getAttribute("bodegas"); // Obtener la lista de bodegas
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Producto</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    </head>
    <body>
        <div class="container my-5">
            <h2 class="text-center mb-4">Asignar Bodega</h2>
            <form action="productos" method="post" class="border p-4 rounded bg-light">
                <input type="hidden" name="_method" value="put">
                <input type="hidden" name="id_producto" value="<%= producto.getId_producto()%>">
                
                <div class="form-group mb-4">
                    <label for="producto">Producto</label>
                    <input type="text" name="producto" id="producto" class="form-control" value="<%= producto.getProducto()%>" required>
                </div>
                
                <div class="form-group mb-4">
                    <label for="cantidad">Cantidad</label>
                    <input type="text" name="cantidad" id="cantidad" class="form-control" value="<%= producto.getCantidad()%>" required>
                </div>
                
                <!-- Combo para seleccionar la bodega -->
                <div class="form-group mb-4">
                    <label for="bodega">Seleccionar Bodega</label><br>
                    <select name="id_bodega" id="bodega" class="form-control" required>
                        <% 
                            for (Bodega bodega : bodegasList) {
                        %>
                            <option value="<%= bodega.getId_bodega() %>">
                                <%= bodega.getBodega() %> - <%= bodega.getUbicacion() %> - <%= bodega.getCiudad() %>
                            </option>
                        <% } %>
                    </select>
                </div>
                
                <br>
                <button type="submit" class="btn btn-primary btn-block">Guardar Relación</button>
            </form>
        </div>
    </body>
</html>
