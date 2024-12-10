
package control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import model.DtoInfo;
import org.json.JSONArray;
import org.json.JSONObject;


public class Controlador {
    
        private final String apiUrl = "http://localhost/PracticaJavaWeb/PracticaProductos/apiPractica.php";

    public List<DtoInfo> obtener() {
        List<DtoInfo> lista = new ArrayList<>();
        try {

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONArray json = new JSONArray(response.toString());
                for (int i = 0; i < json.length(); i++) {

                    JSONObject objeto = json.getJSONObject(i);

                    DtoInfo dato = new DtoInfo(
                        objeto.getString("producto"),
                        objeto.getString("bodega"),
                        Integer.parseInt(objeto.getString("total"))
                    );

                    lista.add(dato);
                }

            } else {
                System.out.println("Error: Código de respuesta HTTP " + conn.getResponseCode());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
