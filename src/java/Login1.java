import jakarta.servlet.RequestDispatcher;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/Login1")
public class Login1 extends HttpServlet { 


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
             
     String email=request.getParameter("email");
     String password=request.getParameter("pwd");       
            
            //Load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        //make the connection object
       Connection cn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Web", "root", "sneha");
        //make the PreparedStatement object
PreparedStatement ps=cn.prepareStatement("select * from music where email=? and password=?");
ps.setString(1, email);
ps.setString(2, password);
//execute query                  
        

     ResultSet rs=ps.executeQuery();

     if(rs.next())
     {
         String name=rs.getString("name");
         HttpSession hs=request.getSession(true);
         hs.setAttribute("naam", name);
         RequestDispatcher rd=request.getRequestDispatcher("home.html");
     rd.forward(request, response);
     }
     else
     {
        out.println("<html>");
        out.println("<head> <title>Message Box</title>   </head>");
        out.println("<body>");
        out.println("<script type='text/javascript'>");
        out.println("alert('Invalid email or password');");
        out.println("</script>");
        out.println("</body></html>");
     RequestDispatcher rd=request.getRequestDispatcher("user.html");
     rd.include(request, response);
     
     }
     
        //close the connection
        cn.close();
        }
        catch (Exception ex) {
            System.out.println(ex);
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
