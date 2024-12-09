/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Producto;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author PC
 */
public class ApiBodega {

    private final String apiURL = "http://localhost/PracticaJavaWeb/Productos/api.php";

    public String getBodegas() {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(apiURL + "?bodegas=true");
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
    
    
    public String saveRealation(int id_bodega, int id_producto) {
        return sendRelationRequest("POST", id_bodega, id_producto);
    }

    private String sendRelationRequest(String method, int id_bodega, int id_producto) {
        try {
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            String params = "id_bodega=" + id_bodega + "&id_producto=" + id_producto + "&accion=relacion";
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
