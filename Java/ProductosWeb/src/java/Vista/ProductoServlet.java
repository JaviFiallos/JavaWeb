package Vista;

import Controlador.ApiProducto;
import Modelo.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
public class ProductoServlet extends HttpServlet {

    private final ApiProducto apiClient = new ApiProducto();

    private List<Producto> parseAllProducts(String jsonResponse) {

        List<Producto> productos = new ArrayList<>();
        try {
            JSONArray listaProductos = new JSONArray(jsonResponse);
            for (int i = 0; i < listaProductos.length(); i++) {
                JSONObject producto = listaProductos.getJSONObject(i);
                productos.add(new Producto(
                        producto.getInt("id_producto"),
                        producto.getString("producto"),
                        producto.getInt("cantidad"),
                        producto.getInt("id_bodega"),
                        producto.getString("bodega"),
                        producto.getString("ubicacion"),
                        producto.getString("ciudad")
                ));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return productos;
    }

    private void listarTodosProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonResponse = apiClient.getAllProducts();
        List<Producto> productos = parseAllProducts(jsonResponse);
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/WEB-INF/allProducts.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("search".equals(action)) {
            String nombre = request.getParameter("nombre");
            if (nombre == null || nombre.trim().isEmpty()) {
                listarTodosProductos(request, response);
            } else {
                String jsonResponse = apiClient.getProductbyName(nombre);
                List<Producto> productos = parseAllProducts(jsonResponse);
                if (productos != null && !productos.isEmpty()) {
                    request.setAttribute("productos", productos);
                } else {
                    request.setAttribute("productos", null);
                    request.setAttribute("error", "No se encontró el producto con el nombre proporcionada.");
                }
                
            }
            request.getRequestDispatcher("/WEB-INF/allProducts.jsp").forward(request, response);
        }else {
            listarTodosProductos(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
