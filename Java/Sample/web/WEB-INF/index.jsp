<%@page import="model.DtoInfo"%>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lista de Productos</title>
    </head>
    <body>
        <table border="1">
            <thead>
                <tr>
                    <th>Bodega</th>
                    <th>Producto</th>
                    <th>Cantidad</th>
                    <th>Cantidad Total</th> <!-- Nueva columna para la cantidad total -->
                </tr>
            </thead>
            <tbody>
                <%
                    List<DtoInfo> listaDatos = (List<DtoInfo>) request.getAttribute("listado");
                    String productoActual = ""; // Producto actual en el bucle
                    int contadorFilas = 0;      // Para contar las filas del producto actual
                    int index = 0;             // Índice para iterar los datos

                    if (listaDatos != null) {
                        while (index < listaDatos.size()) {
                            DtoInfo dato = listaDatos.get(index);
                            productoActual = dato.getProducto();
                            contadorFilas = 0;
                            int cantidadTotal = 0; // Variable para almacenar la cantidad total del producto

                            // Calcular cuántas filas corresponden al producto actual y la cantidad total
                            for (DtoInfo temp : listaDatos) {
                                if (temp.getProducto().equals(productoActual)) {
                                    contadorFilas++;
                                    cantidadTotal += temp.getCantidad(); // Sumar la cantidad de cada bodega
                                }
                            }

                            // Imprimir la primera fila para este producto
                %>
                <tr>
                    <td><%= dato.getBodega() %></td>
                    <td rowspan="<%= contadorFilas %>"><%= dato.getProducto() %></td>
                    <td><%= dato.getCantidad() %></td>
                    <td rowspan="<%= contadorFilas %>"><%= cantidadTotal %></td> <!-- Mostrar cantidad total -->
                </tr>
                <%
                            // Iterar sobre las filas restantes del mismo producto
                            for (int i = 1; i < contadorFilas; i++) {
                                index++;
                                dato = listaDatos.get(index);
                %>
                <tr>
                    <td><%= dato.getBodega() %></td>
                    <td><%= dato.getCantidad() %></td>
                    <!-- La cantidad total ya está en la primera fila, no la repetimos -->
                </tr>
                <%
                            }
                            index++;
                        }
                    } else {
                %>
                <tr>
                    <td colspan="4">No hay datos disponibles</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
