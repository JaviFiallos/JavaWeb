/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Estudiante;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author PC
 */
public class ApiRest {

    private final String apiURL = "http://localhost/PracticaJavaWeb/Estudiantes/api.php";

    public String getStudents() {
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

    public String getStudentbyName(String name) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(apiURL + "?nombre=" + name);
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

    public String saveStudent(Estudiante e) {
        return sendRequest("POST", e);
    }
    
    private String sendRequest(String method, Estudiante e) {
        try {
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            String params = "cedula=" + e.getCedula() + "&nombre=" + e.getNombre() + "&apellido=" 
                    + e.getApellido() + "&direccion=" + e.getDireccion() + "&telefono=" + e.getTelefono()
                    + "&id_materia=" +e.getId_materia();
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
