package Vista;

import Controlador.ApiRest;
import Modelo.Estudiante;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "EstudianteServlet", urlPatterns = {"/EstudianteServlet"})
public class EstudianteServlet extends HttpServlet {

    private final ApiRest apiClient = new ApiRest();

    private Estudiante parseStudent(String jsonResponse) {
        try {
            // Convierte la respuesta en un JSONArray
            JSONArray jsonArray = new JSONArray(jsonResponse);

            // Verifica si el array no está vacío
            if (jsonArray.length() > 0) {
                JSONObject jsonStudent = jsonArray.getJSONObject(0);

                // Extrae los datos del estudiante
                String cedula = jsonStudent.getString("cedula");
                String nombre = jsonStudent.getString("nombre");
                String apellido = jsonStudent.getString("apellido");
                String direccion = jsonStudent.getString("direccion");
                String telefono = jsonStudent.getString("telefono");
                int id_materia = jsonStudent.getInt("id_materia");
                String materia = jsonStudent.getString("materia");
                int creditos = jsonStudent.getInt("creditos");

                return new Estudiante(cedula, nombre, apellido, direccion, telefono, id_materia, materia, creditos);
            } else {
                System.out.println("No se encontró el estudiante en la respuesta.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se puede parsear
    }

    private List<Estudiante> parseStudents(String jsonResponse) {

        List<Estudiante> estudiantes = new ArrayList<>();
        try {
            JSONArray listaEstudiantes = new JSONArray(jsonResponse);
            for (int i = 0; i < listaEstudiantes.length(); i++) {
                JSONObject estudiante = listaEstudiantes.getJSONObject(i);
                estudiantes.add(new Estudiante(
                        estudiante.getString("cedula"),
                        estudiante.getString("nombre"),
                        estudiante.getString("apellido"),
                        estudiante.getString("direccion"),
                        estudiante.getString("telefono"),
                        estudiante.getInt("id_materia"),
                        estudiante.getString("materia"),
                        estudiante.getInt("creditos")
                ));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return estudiantes;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("search".equals(action)) {
            String nombre = request.getParameter("nombre");
            if (nombre == null || nombre.trim().isEmpty()) {
                listarEstudiantes(request, response);
            } else {
                String jsonResponse = apiClient.getStudentbyName(nombre);
                List<Estudiante> estudiantes = parseStudents(jsonResponse);
                if (estudiantes != null && !estudiantes.isEmpty()) {
                    request.setAttribute("students", estudiantes);
                } else {
                    request.setAttribute("students", null);
                    request.setAttribute("error", "No se encontró el estudiante con la cédula proporcionada.");
                }
            }
            request.getRequestDispatcher("/WEB-INF/estudiantes.jsp").forward(request, response);
        } else if ("add".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/agregarEstudiante.jsp").forward(request, response);
        } else {
            listarEstudiantes(request, response);
        }
    }

    private void listarEstudiantes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonResponse = apiClient.getStudents();
        List<Estudiante> students = parseStudents(jsonResponse);
        request.setAttribute("students", students);
        request.getRequestDispatcher("/WEB-INF/estudiantes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("_method");
        // Maneja la creación del estudiante
        Estudiante e = new Estudiante();
        e.setCedula(request.getParameter("cedula"));
        e.setApellido(request.getParameter("apellido"));
        e.setNombre(request.getParameter("nombre"));
        e.setDireccion(request.getParameter("direccion"));
        e.setTelefono(request.getParameter("telefono"));
        e.setId_materia(Integer.parseInt(request.getParameter("id_materia")));
        
        String result = apiClient.saveStudent(e);
        if (result.equals("Error al procesar la solicitud")) {
            // Mostrar mensaje de error o realizar alguna acción
            request.setAttribute("errorMessage", "Hubo un problema al guardar el estudiante.");
            request.getRequestDispatcher("formulario.jsp").forward(request, response);
        } else {
            // Redirigir después de un guardado exitoso
            response.sendRedirect("EstudianteServlet");
        }
    }

}
