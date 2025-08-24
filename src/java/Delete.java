
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(urlPatterns = {"/Delete"})
public class Delete extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

           
            String name = request.getParameter("name");
       
            // Load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Make the connection object
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Web", "root", "sneha");

            // Correct SQL query
            PreparedStatement ps = cn.prepareStatement("DELETE FROM song WHERE name=?");
       
            ps.setString(1, name);
          

            // Execute update
            int count = ps.executeUpdate();

            if (count > 0) {
              
             out.println("Deleted Succesfully");
                RequestDispatcher rd = request.getRequestDispatcher("delete.html");
                rd.forward(request, response);
            } else {
                // Record not found
                out.println("NOT DELETED");
                RequestDispatcher rd = request.getRequestDispatcher("delete.html");
                rd.include(request, response);
            }

            cn.close();

        } catch (Exception ex) {
            ex.printStackTrace(); // Better than System.out.println for debugging
        }
    }

 
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
