package Controlador;

import Modelo.Producto;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiProducto {

    private final String apiURL = "http://localhost/PracticaJavaWeb/Productos/api.php";

    public String getAllProducts() {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(apiURL + "?detalles=true");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
            } else {
                System.out.println("Error en la conexión: " + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public String getSoloProductos() {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
            } else {
                System.out.println("Error en la conexión: " + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
    
    
        public String getProductById(int id) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(apiURL + "?id_producto=" + id);
            System.out.println("URL: " + url); // Verifica la URL generada
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
            } else {
                System.out.println("Error en la conexión: " + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Respuesta de la API: " + result.toString()); // Verifica la respuesta
        return result.toString();
    }

    public String getProductbyName(String name) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(apiURL + "?producto=" + name);
            System.out.println("URL: " + url); // Verifica la URL generada
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
            } else {
                System.out.println("Error en la conexión: " + conn.getResponseCode());
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Respuesta de la API: " + result.toString()); // Verifica la respuesta
        return result.toString();
    }

    public String saveProduct(Producto p) {
        return sendRequest("POST", p);
    }

    private String sendRequest(String method, Producto p) {
        try {
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            String params = "producto=" + p.getProducto() + "&cantidad=" + p.getCantidad();
            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
            writer.writeBytes(params);
            writer.flush();
            writer.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error al procesar la solicitud";
        }
    }

}
