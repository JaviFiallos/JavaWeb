/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package serv;

import control.Controlador;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DtoInfo;

/**
 *
 * @author PC
 */
@WebServlet(name = "info", urlPatterns = {"/info"})
public class info extends HttpServlet {

    private Controlador con = new Controlador();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<DtoInfo> listado = con.obtener();
        request.setAttribute("listado", listado);
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

}
