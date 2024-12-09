/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Vista;

import Controlador.ApiBodega;
import Controlador.ApiProducto;
import Modelo.Bodega;
import Modelo.Producto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author PC
 */
@WebServlet(name = "productos", urlPatterns = {"/productos"})
public class SoloProductosServlet extends HttpServlet {

    private final ApiProducto apiClient = new ApiProducto();
    private final ApiBodega apiBodega = new ApiBodega();

    private List<Producto> parseSoloProducts(String jsonResponse) {

        List<Producto> productos = new ArrayList<>();
        try {
            JSONArray listaProductos = new JSONArray(jsonResponse);
            for (int i = 0; i < listaProductos.length(); i++) {
                JSONObject producto = listaProductos.getJSONObject(i);
                productos.add(new Producto(
                        producto.getInt("id_producto"),
                        producto.getString("producto"),
                        producto.getInt("cantidad")
                ));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return productos;
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonResponse = apiClient.getSoloProductos();
        List<Producto> productos = parseSoloProducts(jsonResponse);
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/WEB-INF/ProductosSinBodega.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id_producto"));
            String jsonResponse = apiClient.getProductById(id);
            List<Producto> p = parseSoloProducts(jsonResponse);
            if (p == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Estudiante no encontrado");
                return;
            }
            String jsonBodegaResponse = apiBodega.getBodegas();
            List<Bodega> bodegas = parseBodegas(jsonBodegaResponse);

            request.setAttribute("bodegas", bodegas);
            request.setAttribute("producto", p.get(0));
            request.getRequestDispatcher("/WEB-INF/asignarBodega.jsp").forward(request, response);
        } else if ("add".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/crearProducto.jsp").forward(request, response);
        } else {
            listarProductos(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("_method");

        if ("put".equalsIgnoreCase(method)) {
            int id_producto = Integer.parseInt(request.getParameter("id_producto"));
            int id_bodega = Integer.parseInt(request.getParameter("id_bodega"));
            String result= apiBodega.saveRealation(id_bodega, id_producto);
            response.sendRedirect("productos");
        } else {
            // Maneja la creación del estudiante
            Producto p = new Producto();
            p.setProducto(request.getParameter("producto"));
            p.setCantidad(Integer.parseInt(request.getParameter("cantidad")));

            String result = apiClient.saveProduct(p);
            response.sendRedirect("productos");
        }

    }

    ////////////////// Bodegas ///////////////////////
    private List<Bodega> parseBodegas(String jsonResponse) {

        List<Bodega> bodegas = new ArrayList<>();
        try {
            JSONArray listaBodegas = new JSONArray(jsonResponse);
            for (int i = 0; i < listaBodegas.length(); i++) {
                JSONObject bodega = listaBodegas.getJSONObject(i);
                bodegas.add(new Bodega(
                        bodega.getInt("id_bodega"),
                        bodega.getString("bodega"),
                        bodega.getString("ubicacion"),
                        bodega.getString("ciudad")
                ));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return bodegas;
    }

    private void listarBodegas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonResponse = apiBodega.getBodegas();
        List<Bodega> bodegas = parseBodegas(jsonResponse);
        request.setAttribute("bodegas", bodegas);
        request.getRequestDispatcher("/WEB-INF/ProductosSinBodega.jsp").forward(request, response);
    }

}
