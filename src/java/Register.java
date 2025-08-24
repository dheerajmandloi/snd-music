import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


@WebServlet(urlPatterns = {"/Register"})
public class Register extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       PrintWriter out = response.getWriter();

        try
        {
            String name=request.getParameter("name");
            String mobile=request.getParameter("mob");
            String email=request.getParameter("email");
            String password=request.getParameter("pwd");
            
            Class.forName("com.mysql.cj.jdbc.Driver");
              Connection cn=DriverManager.getConnection("jdbc:mysql://localhost:3306/Web", "root", "sneha");
       
            PreparedStatement  ps=cn.prepareStatement("insert into music(name,email,mobile,password) values(?,?,?,?)");
             
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);
            ps.setString(4, password);
            
            boolean b=ps.execute();
            if(b==false)
            {
        out.println("<html>");
        out.println("<head><title>Message Box</title></head>");
        out.println("<body>");
        out.println("<script type='text/javascript'>");
        out.println("alert('Registraion completed');");
        out.println("</script>");
        out.println("</body></html>");
        
        RequestDispatcher rd=request.getRequestDispatcher("user.html");
        rd.include(request, response);
            }
            cn.close();
}
catch (Exception ex)
{
    out.println(ex.toString());
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
