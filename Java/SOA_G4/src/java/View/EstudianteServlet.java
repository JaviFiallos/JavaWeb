package View;

import Controller.ApiRest;
import Model.Estudiante;
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

@WebServlet(name = "EstudianteServlet", urlPatterns = {"/EstudianteServlet"})
public class EstudianteServlet extends HttpServlet {

    private final ApiRest apiClient = new ApiRest();
    
    private Estudiante parseStudent(String jsonResponse){
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            if(jsonArray.length()>0){
                JSONObject jsonStudent = jsonArray.getJSONObject(0);
                String cedula = jsonStudent.getString("cedula");
                String nombre = jsonStudent.getString("nombre");
                String apellido = jsonStudent.getString("apellido");
                String direccion = jsonStudent.getString("direccion");
                String telefono = jsonStudent.getString("telefono");
                
                return new Estudiante(cedula, nombre, apellido, direccion, telefono);
            }else{
                System.out.println("No se encontro estudiantes");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    private List<Estudiante> parseStudents(String jsonResponse){
    
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
                        estudiante.getString("telefono")
                ));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return estudiantes;
    }
    
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            String cedula = request.getParameter("cedula");
            String jsonResponse = apiClient.getStudentByCedula(cedula);
            Estudiante estudiante = parseStudent(jsonResponse);
            if (estudiante == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Estudiante no encontrado");
                return;
            }
            request.setAttribute("estudiante", estudiante);
            request.getRequestDispatcher("/WEB-INF/editarEstudiante.jsp").forward(request, response);
        } else if ("add".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/agregarEstudiante.jsp").forward(request, response);
        } else if ("search".equals(action)) {
            String cedula = request.getParameter("cedula");
            if (cedula == null || cedula.trim().isEmpty()) {
                // Si la cédula no se proporciona, recargar toda la lista
                String jsonResponse = apiClient.getStudents();
                List<Estudiante> students = parseStudents(jsonResponse);
                request.setAttribute("students", students);
            } else {
                // Buscar estudiante por cédula
                String jsonResponse = apiClient.getStudentByCedula(cedula);
                Estudiante estudiante = parseStudent(jsonResponse);
                if (estudiante != null) {
                    List<Estudiante> students = new ArrayList<>();
                    students.add(estudiante);
                    request.setAttribute("students", students);
                } else {
                    request.setAttribute("students", null);
                    request.setAttribute("error", "No se encontró el estudiante con la cédula proporcionada.");
                }
            }
            request.getRequestDispatcher("/WEB-INF/estudiantes.jsp").forward(request, response);
        } else {
            String jsonResponse = apiClient.getStudents();
            List<Estudiante> students = parseStudents(jsonResponse);
            request.setAttribute("students", students);
            request.getRequestDispatcher("/WEB-INF/estudiantes.jsp").forward(request, response);
        }
    }
    
        @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cedula = request.getParameter("cedula");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");

        String result = apiClient.updateStudent(cedula, nombre, apellido, direccion, telefono);
        response.getWriter().write(result);
    }
    
        @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cedula = request.getParameter("cedula");
        String result = apiClient.deleteStudent(cedula);
        response.sendRedirect("EstudianteServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("_method");
        if ("put".equalsIgnoreCase(method)) {
            doPut(request, response);
        }if("delete".equalsIgnoreCase(method)){
        doDelete(request, response);
        }
        else {
            
            // Maneja la creación del estudiante
            String cedula = request.getParameter("cedula");
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String direccion = request.getParameter("direccion");
            String telefono = request.getParameter("telefono");

            String result = apiClient.saveStudent(cedula, nombre, apellido, direccion, telefono);
            response.sendRedirect("EstudianteServlet");
        }
    }

}
